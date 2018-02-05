package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.ForkStorage;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PipelineDistributer2 {

	private static final Logger log = Logger.getLogger(PipelineDistributer2.class);
	
	public static void distributePipeline(PipelineDTO pipeline, int numberOfAgents){
		if(pipeline.isSequence() || pipeline.countSinks() == 1){
			return;//a sequence will be deployed at exactly one agent, it will not be split up
			//same goes for pipelines with only one sink, they cannot be split up
		}
		if(numberOfAgents == 0){
			throw new IllegalArgumentException("You can not limit the number of Agents to zero!");
		}
		
		try {
			PipelineSort.sortPipeline(pipeline);
			List<Integer> visited = new ArrayList<>();
			List<PipelineStepDTO> steps = pipeline.getPipelineSteps();
			List<Integer> neverJoinedForks = new ArrayList<>();
			
			distributePipelineRecursive(steps, 0, new ForkStorage(), visited, neverJoinedForks);
			
			assignAgents(steps, neverJoinedForks, numberOfAgents);
		} catch (IllegalStateException e) {
			log.error("Error while distributing the pipeline!",e);
			return;
		}
	}

	public static void distributePipeline(PipelineDTO pipeline){
		distributePipeline(pipeline, -1);
	}

	private static void distributePipelineRecursive(List<PipelineStepDTO> steps, int index, ForkStorage forkStorage,
			List<Integer> visitedIndexes, List<Integer> neverJoinedForks) {
		if(visitedIndexes.contains(index)){
			distributePipelineRecursive(steps, index + 1, forkStorage, visitedIndexes, neverJoinedForks);
			return;
		}else{
			visitedIndexes.add(index);
		}
		if(index >= steps.size()){
			return;
		}
		
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		
		if(succIndexes.size() > 1){
			int forkJoin = ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes);
			forkStorage.addNewFork(succIndexes.size(), forkJoin);
			if(forkJoin == -1){
				neverJoinedForks.add(steps.get(index).getStepNumber());
			}
		}
		
		if(succIndexes.size() == 1){
			distributePipelineRecursive(steps, index + 1, forkStorage, visitedIndexes, neverJoinedForks);
		}else{
			for (int i = 0; i < succIndexes.size(); i++) {
				distributePipelineRecursive(steps, index + 1 + i, forkStorage, visitedIndexes, neverJoinedForks);
			}
		}
	}
	
	private static void assignAgents(List<PipelineStepDTO> steps, List<Integer> neverJoinedForks, int numberOfAgents) {
		int agent = 0;
		List<Integer> successors = new ArrayList<>();
		for(PipelineStepDTO step : steps){
			if(canAssignNewAgent(step, successors, numberOfAgents, agent)){
				agent++;
			}
			if(neverJoinedForks.contains(step.getStepNumber())){
				successors.addAll(step.getSuccessorsFlatUpdated());
			}
			step.setAgentAdvice(agent);
		}
	}
	
	private static boolean canAssignNewAgent(PipelineStepDTO step, List<Integer> successors ,int numberOfAgents , int currentAgent){
		return successors.contains(step.getStepNumber()) 
				&& (numberOfAgents < 0 || currentAgent < numberOfAgents - 1);
	}
}
