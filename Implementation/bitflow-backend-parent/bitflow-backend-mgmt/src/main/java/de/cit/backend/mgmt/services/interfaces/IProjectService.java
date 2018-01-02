package de.cit.backend.mgmt.services.interfaces;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.ProjectDTO;

@Local
public interface IProjectService {

	ProjectDTO loadProject(int projectId);

}
