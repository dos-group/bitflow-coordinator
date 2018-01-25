package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.ForkStorage;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineParameterDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.StepTypeEnum;

public class ScriptGenerator {

	private static final Logger log = Logger.getLogger(ScriptGenerator.class);
	
	public static String generateScriptForPipelineStep(PipelineStepDTO pipelineStep){
		StringBuilder sb = new StringBuilder();
		
		if(pipelineStep.getType() == StepTypeEnum.SOURCE || pipelineStep.getType() == StepTypeEnum.SINK){
			sb.append(pipelineStep.getContent());
		}else{
			sb.append(pipelineStep.getContent());
			sb.append("(");
			for(PipelineParameterDTO param : pipelineStep.getParams()){
				sb.append(param.toString());
				sb.append(", ");
			}
			if(pipelineStep.getParams().size() > 0){
				sb.delete(sb.length() - 2, sb.length());
			}
			sb.append(")");
		}
		
		return sb.toString();
	}
	
	public static String generateScriptForPipeline(PipelineDTO pipeline) {
		StringBuilder sb = new StringBuilder();
		
		try {
			PipelineSort.sortPipeline(pipeline);
			List<Integer> visited = new ArrayList<>();
			List<PipelineStepDTO> steps = pipeline.getPipelineSteps();
			generateScriptRecursive(steps, 0, sb, new ForkStorage(), visited);
		} catch (IllegalStateException e) {
			log.error("Error while generating the pipeline script!",e);
			return null;
		}
		return sb.toString();
	}

	static void generateScriptRecursive(List<PipelineStepDTO> steps, int index, StringBuilder sb,
			ForkStorage forkStorage, List<Integer> visitedIndexes) throws IllegalStateException {
		if(visitedIndexes.contains(index)){
			generateScriptRecursive(steps, index + 1, sb, forkStorage, visitedIndexes);
			return;
		}else{
			visitedIndexes.add(index);
		}
		if(index >= steps.size()){
			return;
		}
		
		sb.append(generateScriptForPipelineStep(steps.get(index)));
		
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		
		if(succIndexes.size() > 1){
			forkStorage.addNewFork(succIndexes.size(), ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes));
			sb.append(" -> { ");
		}else if(succIndexes.size() == 0){
			if(forkStorage.isForkBranchClosedAtSink()){
				forkStorage.closeForkBranch();
				sb.append(" ; ");
			}else if(forkStorage.isForkClosedAtSink()){
				forkStorage.closeFork();
				if(index == steps.size() - 1){
					sb.append(" } ");
				}else{
					forkStorage.closeForkBranch();
					sb.append(" }; ");
				}
			}
			return;
		}else {
			if(forkStorage.isForkBranchClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeForkBranch();
				sb.append(" ; ");
				return;
			}else if(forkStorage.isForkClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeFork();
				sb.append(" } -> ");
			}else{
				sb.append(" -> ");
			}
		}
		
		if(succIndexes.size() == 1){
			generateScriptRecursive(steps, index + 1, sb, forkStorage, visitedIndexes);
		}else{
			for (int i = 0; i < succIndexes.size(); i++) {//i, succIndexes.size() - 1, forkJoinStepNumber
				generateScriptRecursive(steps, index + 1 + i, sb, forkStorage, visitedIndexes);
			}
		}
	}
}
