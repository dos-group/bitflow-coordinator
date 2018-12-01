package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.helper.model.DeploymentResponse;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;

public interface IPipelineService {

	PipelineDTO saveNewPipeline(PipelineDTO pipeline, Integer id) throws BitflowException;
	List<PipelineDTO> loadPipelinesFromProject(int projectId) throws BitflowException;
	PipelineDTO loadPipelineFromProject(int projectId, int pipelineId) throws BitflowException;
	PipelineDTO loadPipelineByName(String pipelineName) throws BitflowException;
	DeploymentResponse executePipeline(Integer projectId, Integer pipelineId) throws BitflowException;
	List<PipelineHistoryDTO> loadPipelineHistory(Integer projectId, Integer pipelineId) throws BitflowException;
	PipelineHistoryDTO loadPipelineHistoryLast(Integer projectId, Integer pipelineId) throws BitflowException;
	void updatePipeline(int projectId, int pipelineId, PipelineDTO pipeline) throws BitflowException;
}
