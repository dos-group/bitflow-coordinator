package de.cit.backend.mgmt.services;

import java.util.Date;
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
import de.cit.backend.mgmt.persistence.model.UserDTO;
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
//		List<PipelineDTO> lines = pro.getPipelines();
//		for (PipelineDTO pipelineDTO : lines) {
//			Hibernate.initialize(pipelineDTO.getPipelineSteps());
//		}
		return pro;
	}

	@Override
	public PipelineDTO saveNewPipeline(PipelineDTO pipeline, Integer projectId) {
		if(projectId == null){
			throw new IllegalArgumentException("Saving new Pipeline: Project ID must be provided!");
		}
		ProjectDTO project = persistence.findProject(projectId);
		if(project == null){
			throw new IllegalArgumentException("Saving new Pipeline: Project ID does not exist!");
		}
		pipeline.setLastChanged(new Date());
		pipeline.getProjects().add(project);
		project.getPipelines().add(pipeline);
		persistence.saveObject(pipeline);
		
		setSuccessors(pipeline.getPipelineSteps());
		return pipeline;
	}

	private void setSuccessors(List<PipelineStepDTO> pipelineSteps) {
		for(PipelineStepDTO step : pipelineSteps){
			for(int succ : step.getSuccessorsFlat()){
				step.getSuccessors().add(findPipelineStepByNumber(pipelineSteps, succ));
			}
		}
	}

	private PipelineStepDTO findPipelineStepByNumber(List<PipelineStepDTO> pipelineSteps, int succ) {
		for(PipelineStepDTO step : pipelineSteps){
			if(step.getStepNumber() == succ){
				return step;
			}
		}
		throw new IllegalStateException("There is a stepnumber referenced, that is not assigned to any pipeline step!");
	}

	@Override
	public List<ProjectDTO> loadProjects(String username) {
		UserDTO user = persistence.findUser(username);
		List<ProjectDTO> pros = user.getJoinedProjects();
		for (ProjectDTO projectDTO : pros) {
			this.loadProject(projectDTO.getId());
		}
		return user.getJoinedProjects();
	}

}
