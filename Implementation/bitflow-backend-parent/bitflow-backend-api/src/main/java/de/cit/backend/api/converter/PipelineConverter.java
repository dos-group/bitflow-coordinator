package de.cit.backend.api.converter;

import de.cit.backend.api.model.Pipeline;
import de.cit.backend.mgmt.helper.ScriptGenerator;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PipelineConverter implements Converter<PipelineDTO, Pipeline>{
	
	public PipelineDTO convertToBackend(Pipeline in) {
		if(in == null){
			return null;
		}
		
		PipelineDTO out = new PipelineDTO();
		out.setId(in.getID());
		out.setStatus("unknown"); // representation in Frontend missing
		//FIXME
//		out.setProject(new ProjectConverter().convertToBackend(in.getProject()));
		out.setLastChanged(in.getLastChanged());
		out.setPipelineSteps(new PipelineStepConverter().convertToBackend(in.getPipelineSteps()));
		out.setName(in.getName());
		
		return out;
	}

	public Pipeline convertToFrontend(PipelineDTO in) {
		if(in == null){
			return null;
		}
		//FIXME move the converters into an EJB, so we can LazyLoad some properties
		
		Pipeline out = new Pipeline();
		out.setID(in.getId());
		out.setName(in.getName());
		out.setLastChanged(in.getLastChanged());
		out.setSript(ScriptGenerator.generateScriptForPipeline(in));
		out.setPipelineSteps(new PipelineStepConverter().convertToFrontend(in.getPipelineSteps()));
		return out;
	}
}
