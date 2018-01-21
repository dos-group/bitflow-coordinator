package de.cit.backend.mgmt.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Hibernate;
import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.services.interfaces.IPipelineService;

@Stateless
@Local(IPipelineService.class)
public class PipelineService implements IPipelineService {

	private static final Logger log = Logger.getLogger(PipelineService.class);
	
	
	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}
	
	@Override
	public PipelineDTO loadPipelineFromProject(int projectId, int pipelineId) {
		ProjectDTO pro = persistence.findProject(projectId);

		PipelineDTO pipe = persistence.findPipeline(pipelineId);
		for(PipelineStepDTO step : pipe.getPipelineSteps()){
			Hibernate.initialize(step.getSuccessors());
		}
		if (pro.getPipelines().contains(pipe))
		{
			return pipe;
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<PipelineDTO> loadPipelinesFromProject(int projectId) {
		ProjectDTO pro = persistence.findProject(projectId);
		Hibernate.initialize(pro.getPipelines());
		for(PipelineDTO pipe : pro.getPipelines()) {
			Hibernate.initialize(pipe.getPipelineSteps());
			for (PipelineStepDTO step : pipe.getPipelineSteps()) {
				Hibernate.initialize(step.getSuccessors());
			}
		}
		return pro.getPipelines();
	}

}
