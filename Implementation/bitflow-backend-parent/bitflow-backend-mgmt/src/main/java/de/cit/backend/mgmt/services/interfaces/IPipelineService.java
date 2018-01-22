package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import de.cit.backend.agent.api.model.PipelineResponse;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public interface IPipelineService {

	List<PipelineDTO> loadPipelinesFromProject(int projectId);
	PipelineDTO loadPipelineFromProject(int projectId, int pipelineId);
	PipelineResponse executePipeline(Integer projectId, Integer pipelineId);
	
}
