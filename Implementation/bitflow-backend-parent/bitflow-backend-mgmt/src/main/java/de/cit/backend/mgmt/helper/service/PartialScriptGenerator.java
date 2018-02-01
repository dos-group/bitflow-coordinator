package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.ForkStorage;
import de.cit.backend.mgmt.helper.model.PartialScriptBuilder;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PartialScriptGenerator {

	private static final Logger log = Logger.getLogger(PartialScriptGenerator.class);
	
	public static DeploymentInformation[] generateParallelScripts(PipelineDTO pipeline){//, int numberOfAgents
//		PipelineDistributer2.distributePipeline(pipeline, numberOfAgents);//do that independently
		
		try {
			PipelineSort.sortPipeline(pipeline);
			List<Integer> visited = new ArrayList<>();
			List<PipelineStepDTO> steps = pipeline.getPipelineSteps();
			PartialScriptBuilder scriptBuilder = new PartialScriptBuilder();
			
			generateScriptRecursive(steps, 0, new ForkStorage(), visited, scriptBuilder);
			return scriptBuilder.buildScripts();
		} catch (IllegalStateException e) {
			log.error("Error while generating the distributed pipeline scripts!",e);
			return null;
		}
	}
	
	private static void generateScriptRecursive(List<PipelineStepDTO> steps, int index, ForkStorage forkStorage, 
			List<Integer> visitedIndexes, PartialScriptBuilder scriptBuilder) throws IllegalStateException {
		if(visitedIndexes.contains(index)){
			generateScriptRecursive(steps, index + 1, forkStorage, visitedIndexes, scriptBuilder);
			return;
		}else{
			visitedIndexes.add(index);
		}
		if(index >= steps.size()){
			return;
		}
		
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		List<Integer> succAgents = SuccessorTracker.findSuccessorAgents(steps, succIndexes);
		
		int agent = steps.get(index).getAgentAdvice();
		scriptBuilder.appendToScript(agent, succAgents, ScriptGenerator.generateScriptForPipelineStep(steps.get(index)));
		
		if(succIndexes.size() > 1){
			forkStorage.addNewFork(succIndexes.size(), ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes));
			int forkOnDifferentAgents = analyseFork(steps, succIndexes, agent);
			scriptBuilder.forkDetected(agent, succIndexes.size(), forkOnDifferentAgents);
		}else if(succIndexes.size() == 0){
			if(forkStorage.isForkBranchClosedAtSink()){
				forkStorage.closeForkBranch();
				scriptBuilder.appendToScript(agent, null, " ; ");
//				sb.append(" ; ");
			}else if(forkStorage.isForkClosedAtSink()){
				forkStorage.closeFork();
				if(index == steps.size() - 1){
					scriptBuilder.appendToScript(agent, null, " } ");
//					sb.append(" } ");
				}else{
					forkStorage.closeForkBranch();
					scriptBuilder.appendToScript(agent, null, " }; ");
//					sb.append(" }; ");
				}
			}
			return;
		}else {
			if(forkStorage.isForkBranchClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeForkBranch();
				scriptBuilder.appendToScript(agent, null, " ; ");
//				sb.append(" ; ");
				return;
			}else if(forkStorage.isForkClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeFork();
				scriptBuilder.appendToScript(agent, null," } -> ");
//				sb.append(" } -> ");
			}else{
				scriptBuilder.appendToScript(agent, null," -> ");
//				sb.append(" -> ");
			}
		}
		
		if(succIndexes.size() == 1){
			generateScriptRecursive(steps, index + 1, forkStorage, visitedIndexes, scriptBuilder);
		}else{
			for (int i = 0; i < succIndexes.size(); i++) {
				generateScriptRecursive(steps, index + 1 + i, forkStorage, visitedIndexes, scriptBuilder);
			}
		}
	}

	private static int analyseFork(List<PipelineStepDTO> steps, List<Integer> succIndexes, int currentAgent) {
		int count = 0;
		for(int succIndex : succIndexes){
			if(steps.get(succIndex).getAgentAdvice() != currentAgent){
				count++;
			}
		}
		return count;
	}
	
}
