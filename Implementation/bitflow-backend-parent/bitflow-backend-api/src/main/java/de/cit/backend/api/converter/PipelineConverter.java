package de.cit.backend.api.converter;

import de.cit.backend.api.model.Pipeline;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PipelineConverter implements Converter<PipelineDTO, Pipeline>{
	
	public PipelineDTO convertToBackend(Pipeline in) {
		if(in == null){
			return null;
		}
		
		PipelineDTO out = new PipelineDTO();
		out.setId(in.getID());
		out.setStatus("unknown"); // representation in Frontend missing
		out.setProject(new ProjectConverter().convertToBackend(in.getProject()));
		out.setScript(in.getSript());
		out.setLastChanged(in.getLastChanged());
		out.setPipelineSteps(new PipelineStepConverter().convertToBackend(in.getPipelineSteps()));
		
		return out;
	}

	public Pipeline convertToFrontend(PipelineDTO in) {
		if(in == null){
			return null;
		}
		//FIXME move the conerters into an EJB, so we can LazyLoad some properties
		Pipeline out = new Pipeline();
		out.setID(in.getId());
		out.setName(in.getProject().getName());
		out.setProject(new ProjectConverter().convertToFrontend(in.getProject())); 
		out.setSript(in.getScript());
		out.setLastChanged(in.getLastChanged());
		out.setPipelineSteps(new PipelineStepConverter().convertToFrontend(in.getPipelineSteps()));
		return out;
	}
}
