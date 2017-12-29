package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;
import de.cit.backend.api.model.PipelineStep;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class PipelineStepConverter implements Converter <PipelineStepDTO, PipelineStep>{

	public PipelineStepDTO convertToBackend(PipelineStep in) {
		
		// incomplete
		PipelineStepDTO out = new PipelineStepDTO();
		out.setId(in.getID());
		//out.setPipeline(pipeline);
		out.setScript(in.getAlgorithm());
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
		out.setPipelineId(in.getPipeline().getId());
		out.setAlgorithm(in.getScript());
		//out.setSuccessors(successors);
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
