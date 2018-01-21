package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;

@Local
public interface IProjectService {

	ProjectDTO loadProject(int projectId);
	List<ProjectDTO> loadProjects(String username);
	PipelineDTO saveNewPipeline(PipelineDTO pipeline, Integer id);
	void assignUserToProject(Integer projectId, Integer userId);
	void removeUserFromProject(Integer projectId, Integer userId);

}
