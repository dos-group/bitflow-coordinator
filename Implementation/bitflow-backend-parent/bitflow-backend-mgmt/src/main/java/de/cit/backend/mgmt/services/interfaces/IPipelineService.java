package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import de.cit.backend.agent.api.model.PipelineResponse;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public interface IPipelineService {

	List<PipelineDTO> loadPipelinesFromProject(int projectId) throws BitflowException;
	PipelineDTO loadPipelineFromProject(int projectId, int pipelineId) throws BitflowException;
	PipelineResponse executePipeline(Integer projectId, Integer pipelineId) throws BitflowException;
	
}
