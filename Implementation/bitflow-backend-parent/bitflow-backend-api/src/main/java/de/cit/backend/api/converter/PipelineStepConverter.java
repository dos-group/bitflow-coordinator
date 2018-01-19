package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.api.model.PipelineStep;
import de.cit.backend.api.model.PipelineStep.TypEnum;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.StepTypeEnum;

public class PipelineStepConverter implements Converter <PipelineStepDTO, PipelineStep>{

	public PipelineStepDTO convertToBackend(PipelineStep in) {
		
		// incomplete
		PipelineStepDTO out = new PipelineStepDTO();
		out.setId(in.getID());
		//out.setPipeline(pipeline);
		return out;
	}

	public List<PipelineStepDTO> convertToBackend(List<PipelineStep> in) {
		List<PipelineStepDTO> out = new ArrayList<PipelineStepDTO>();
		for (PipelineStep pipelineStep : in) {
			out.add(this.convertToBackend(pipelineStep));
		}
		return out;
	}
	
	public PipelineStep convertToFrontend(PipelineStepDTO in) {
		PipelineStep out = new PipelineStep();
		out.setID(in.getId());
		out.setNumber(in.getStepNumber());
		out.setAgentId(0);
		out.setContent(in.getContent());
		if(in.getType() == StepTypeEnum.SOURCE){
			out.setTyp(TypEnum.SOURCE);
		}else if(in.getType() == StepTypeEnum.SINK){
			out.setTyp(TypEnum.SINK);
		}else{
			out.setTyp(TypEnum.OPERATION);
		}
		
		out.setParams(null);
		List<Integer> succ = new ArrayList<Integer>();
		for(PipelineStepDTO step : in.getSuccessors()){
			succ.add(step.getStepNumber());
		}
		out.setSuccessors(succ);
		return out;
	}

	public List<PipelineStep> convertToFrontend(List<PipelineStepDTO> in) {
		List<PipelineStep> out = new ArrayList<PipelineStep>();
		for (PipelineStepDTO pipelineStepDTO : in) {
			out.add(this.convertToFrontend(pipelineStepDTO));
		}
		return out;
	}
	
}
