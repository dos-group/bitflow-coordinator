package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import de.cit.backend.api.model.Pipeline;
import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.helper.service.ScriptGenerator;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public class PipelineConverter implements Converter<PipelineDTO, Pipeline>{
	
	private static final Logger log = Logger.getLogger(PipelineConverter.class);
	
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
		try{
			out.setSript(ScriptGenerator.generateScriptForPipeline(in));
		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
			out.setSript("Script could not be generated: Please check your Pipeline for errors.");
		}
		out.setPipelineSteps(new PipelineStepConverter().convertToFrontend(in.getPipelineSteps()));
		
		Project project = new Project();
		out.setProject(project);
		return out;
	}
	
	public List<Pipeline> convertToFrontend(List<PipelineDTO> in, Integer projectId) {
		List<Pipeline> out = new ArrayList<Pipeline>();
		for (PipelineDTO pipelineDTO : in) {
			out.add(this.convertToFrontend(pipelineDTO, projectId));
		}
		return out;
	}

	public Pipeline convertToFrontend(PipelineDTO savedPipe, Integer projectId) {
		Pipeline pipe = convertToFrontend(savedPipe);
		pipe.getProject().setID(projectId);
		
		return pipe;
	}
}
