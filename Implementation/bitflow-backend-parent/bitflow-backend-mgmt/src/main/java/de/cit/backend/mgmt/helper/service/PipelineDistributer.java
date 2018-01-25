package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.helper.model.AgentInfo;
import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.ForkStorage;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PipelineDistributer {

	private static final Logger log = Logger.getLogger(PipelineDistributer.class);
	
	public static void distributePipeline(PipelineDTO pipeline, int numberOfAgents){
		if(pipeline.isSequence()){
			return;//a sequence will be deployed at exactly one agent, it will not be split up
		}
		if(numberOfAgents == 0){
			throw new IllegalArgumentException("You can not limit the number of Agents to zero!");
		}
		
		try {
			PipelineSort.sortPipeline(pipeline);
			List<Integer> visited = new ArrayList<>();
			List<PipelineStepDTO> steps = pipeline.getPipelineSteps();
			distributePipelineRecursive(steps, 0, new ForkStorage(), new AgentInfo(numberOfAgents), visited);
		} catch (IllegalStateException e) {
			log.error("Error while distributing the pipeline!",e);
			return;
		}
	}
	
	public static void distributePipeline(PipelineDTO pipeline){
		distributePipeline(pipeline, -1);
	}

	private static void distributePipelineRecursive(List<PipelineStepDTO> steps, int index, ForkStorage forkStorage,
			AgentInfo agentInfo, List<Integer> visitedIndexes) {
		if(visitedIndexes.contains(index)){
			distributePipelineRecursive(steps, index + 1, forkStorage, agentInfo, visitedIndexes);
			return;
		}else{
			visitedIndexes.add(index);
		}
		if(index >= steps.size()){
			return;
		}
		steps.get(index).setAgentAdvice(agentInfo.getCurrentAgent());
		
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		
		if(succIndexes.size() > 1){
			forkStorage.addNewFork(succIndexes.size(), ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes));
			agentInfo.incrementAgentByFork();
		}else if(succIndexes.size() == 0){
			return;
		}else {
			if(forkStorage.isForkBranchClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeForkBranch();
				agentInfo.closeForkBranch();
				return;
			}else if(forkStorage.isForkClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeFork();
				agentInfo.closeFork();
			}
		}
		
		if(succIndexes.size() == 1){
			distributePipelineRecursive(steps, index + 1, forkStorage, agentInfo, visitedIndexes);
		}else{
			for (int i = 0; i < succIndexes.size(); i++) {
				distributePipelineRecursive(steps, index + 1 + i, forkStorage, agentInfo, visitedIndexes);
			}
		}
	}
}
