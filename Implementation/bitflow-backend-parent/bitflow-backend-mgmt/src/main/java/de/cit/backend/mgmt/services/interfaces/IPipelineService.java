package de.cit.backend.mgmt.services.interfaces;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;

public interface IPipelineService {

	PipelineDTO loadPipelineFromProject(int projectId, int pipelineId);
}
