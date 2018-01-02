package de.cit.backend.mgmt.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Hibernate;
import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.services.interfaces.IProjectService;

@Stateless
@Local(IProjectService.class)
public class ProjectService implements IProjectService {

	private static final Logger log = Logger.getLogger(ProjectService.class);
	
	
	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}
	
	@Override
	public ProjectDTO loadProject(int projectId) {
		ProjectDTO pro = persistence.findProject(projectId);
		if(pro == null) return null;//TODO remove later
		Hibernate.initialize(pro.getUserdata());
		Hibernate.initialize(pro.getProjectMembers());
		Hibernate.initialize(pro.getPipelines());
		List<PipelineDTO> lines = pro.getPipelines();
		for (PipelineDTO pipelineDTO : lines) {
			Hibernate.initialize(pipelineDTO.getPipelineSteps());
		}
		return pro;
	}

}
