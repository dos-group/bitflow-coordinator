package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.PartialScriptBuilder;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PartialScriptGeneratorSimple {

	private static final Logger log = Logger.getLogger(PartialScriptGeneratorSimple.class);
	
	private List<Integer> forkJoinNumbers;
	private List<Integer> currentForkSize;
	private List<Integer> forkAgents;
	private List<Integer> firstStepsAfterFork;
	
	public PartialScriptGeneratorSimple(){
		forkJoinNumbers = new ArrayList<>();
		forkAgents = new ArrayList<>();
		firstStepsAfterFork = new ArrayList<>();
		currentForkSize = new ArrayList<>();
	}
	
	public DeploymentInformation[] generateParallelScripts(PipelineDTO pipeline) throws BitflowException{//, int numberOfAgents
		
		try {
			Map<Integer, List<Integer>> agentStepMapping = getAgentStepMapping(pipeline);
			
			PartialScriptBuilder scriptBuilder = buildPartialScripts(agentStepMapping, pipeline);
			
			return scriptBuilder.buildScripts();
		} catch (IllegalStateException e) {
			log.error("Error while generating the distributed pipeline scripts!",e);
			throw new BitflowException(e);
		}
	}

	private Map<Integer, List<Integer>> getAgentStepMapping(PipelineDTO pipeline) {
		Map<Integer, List<Integer>> agentStepMapping = new HashMap<>();
		for(int index = 0; index < pipeline.getPipelineSteps().size(); index++){
			PipelineStepDTO step = pipeline.getPipelineSteps().get(index);
			
			if(!agentStepMapping.containsKey(step.getAgentAdvice())){
				agentStepMapping.put(step.getAgentAdvice(), new ArrayList<>());
			}
			agentStepMapping.get(step.getAgentAdvice()).add(index);
		}
		
		return agentStepMapping;
	}	
	
	private PartialScriptBuilder buildPartialScripts(Map<Integer, List<Integer>> agentStepMapping, PipelineDTO pipeline) {
		PartialScriptBuilder scriptBuilder = new PartialScriptBuilder();
		for(Integer agent : agentStepMapping.keySet()){
			buildScriptForAgent(agent, agentStepMapping.get(agent), pipeline.getPipelineSteps(), scriptBuilder);
		}
			
		return scriptBuilder;	
	}

	private void buildScriptForAgent(Integer agent, List<Integer> indexList, List<PipelineStepDTO> steps,
			PartialScriptBuilder scriptBuilder) {
		
		for(int i = 0; i < indexList.size(); i++){
			int index = indexList.get(i);
			List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
			List<Integer> succAgents = SuccessorTracker.findSuccessorAgents(steps, succIndexes);
			
			if(isJoinOnSameAgent(agent, index, steps)){
				scriptBuilder.appendToScript(agent, null, " } -> ");
			}else if(i != 0 && !firstStepsAfterFork.contains(index)){
				scriptBuilder.appendToScript(agent, null, " -> ");
			}
			
			scriptBuilder.appendToScript(agent, succAgents, ScriptGenerator.generateScriptForPipelineStep(steps.get(index)));
		
			if(succIndexes.size() > 1){
				int join = ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes);
				if(join >= 0){
					forkJoinNumbers.add(join);
					forkAgents.add(agent);
				}
				firstStepsAfterFork.addAll(succIndexes);
				currentForkSize.add(succIndexes.size());
				int forkOnDifferentAgents = countBranchesOnDifferentAgents(steps, succIndexes, agent);
				scriptBuilder.forkDetected(agent, succIndexes.size(), forkOnDifferentAgents);
			}else if(succIndexes.size() == 0){
				
			}else{
				handleOneSuccessor(agent, index, indexList, succIndexes.get(0), steps, scriptBuilder);
			}
		}
		
		scriptBuilder.closeParatheses(agent);
	}

	private void handleOneSuccessor(int agent, int index, List<Integer> indexList, int successorIndex, List<PipelineStepDTO> steps,
			PartialScriptBuilder scriptBuilder) {
		if(!isSuccessorOnSameAgent(indexList, successorIndex)){
			scriptBuilder.appendToScript(agent, null, " -> " + DeploymentInformation.PLACEHOLDER_SINK);
		}else if(isSuccessorJoinerOnSameAgent(indexList, successorIndex, steps, agent)){
			int sizeRemaining = decrementForkSize();
			if(sizeRemaining > 0){
				scriptBuilder.appendToScript(agent, null, " ; ");
			}
		}
		
		/*
		 * scriptBuilder.appendToScript(agent, null, " -> ");
		}else{
		 */
	}
	
	private int decrementForkSize() {
		int size = currentForkSize.remove(currentForkSize.size() - 1);
		if(size > 1){
			currentForkSize.add(size - 1);
		}
		return size - 1;
	}

	private boolean isJoinOnSameAgent(int agent, int index, List<PipelineStepDTO> steps) {
		return !forkJoinNumbers.isEmpty() && forkJoinNumbers.get(forkJoinNumbers.size() - 1) == steps.get(index).getStepNumber()
				&& forkAgents.get(forkAgents.size() - 1) == agent;
	}

	private boolean isSuccessorJoinerOnSameAgent(List<Integer> indexList, int successorIndex, List<PipelineStepDTO> steps, int agent) {
		return !forkJoinNumbers.isEmpty() && forkJoinNumbers.contains(steps.get(successorIndex).getStepNumber())
				&& indexList.contains(successorIndex);
	}

	private boolean isSuccessorOnSameAgent(List<Integer> indexList, int succIndex) {
		return indexList.contains(succIndex);
	}

	private int countBranchesOnDifferentAgents(List<PipelineStepDTO> steps, List<Integer> succIndexes, int currentAgent) {
		int count = 0;
		for(int succIndex : succIndexes){
			if(steps.get(succIndex).getAgentAdvice() != currentAgent){
				count++;
			}
		}
		return count;
	}
}
