package de.cit.backend.mgmt.services.interfaces;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;;

@Local
public interface IProjectService {

	ProjectDTO loadProject(int projectId);
	PipelineDTO loadProjectPipelines(int projectId);
}
