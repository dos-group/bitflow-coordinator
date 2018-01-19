package de.cit.backend.mgmt.helper;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineParameterDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.StepTypeEnum;

public class ScriptGenerator {

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
	
	public static String generateScriptForPipeline(PipelineDTO pipeline){
		StringBuilder sb = new StringBuilder();
		boolean parallelMode = false;
		
		//TODO Sortierung ist hier wichtig
		for(PipelineStepDTO step : pipeline.getPipelineSteps()){
			sb.append(generateScriptForPipelineStep(step));
			sb.append(" -> ");
			
			if(step.getSuccessors().size() > 1){
				//handle parallel steps
			}
		}
		sb.delete(sb.length() - 4, sb.length());
		
		return sb.toString();
	}
}
