package de.cit.backend.mgmt.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Hibernate;
import org.jboss.logging.Logger;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
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
	public static final String PROJECT_ERROR_OBJECT = "Project";
	
	
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
	public PipelineDTO saveNewPipeline(PipelineDTO pipeline, Integer projectId) throws BitflowException {
		ProjectDTO project = persistence.findProject(projectId);
		if(project == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PROJECT_ERROR_OBJECT);
		}
		pipeline.setLastChanged(new Date());
		pipeline.getProjects().add(project);
		project.getPipelines().add(pipeline);
		persistence.saveObject(pipeline);
		
		setSuccessors(pipeline.getPipelineSteps());
		return pipeline;
	}

	private void setSuccessors(List<PipelineStepDTO> pipelineSteps) throws BitflowException {
		for(PipelineStepDTO step : pipelineSteps){
			for(int succ : step.getSuccessorsFlat()){
				step.getSuccessors().add(findPipelineStepByNumber(pipelineSteps, succ));
			}
		}
	}

	private PipelineStepDTO findPipelineStepByNumber(List<PipelineStepDTO> pipelineSteps, int succ) throws BitflowException {
		for(PipelineStepDTO step : pipelineSteps){
			if(step.getStepNumber() == succ){
				return step;
			}
		}
		throw new BitflowException(ExceptionConstants.PIPELINE_VALIDATION_ERROR, 
				"There is a stepnumber referenced, that is not assigned to any pipeline step!");
	}

	@Override
	public List<ProjectDTO> loadProjects(String username) {
		UserDTO user = persistence.findUser(username);
		
		List<ProjectDTO> allProjectsFromUser = new ArrayList<>();
		allProjectsFromUser.addAll(user.getJoinedProjects());
		allProjectsFromUser.addAll(user.getCreatedProjects());
		
		for(ProjectDTO pro : allProjectsFromUser){
			Hibernate.initialize(pro.getUserdata());
			Hibernate.initialize(pro.getProjectMembers());
		}
		
		return allProjectsFromUser;
	}

	@Override
	public void assignUserToProject(Integer projectId, Integer userId) throws BitflowException {
		ProjectDTO project = persistence.findProject(projectId);
		UserDTO user = persistence.findUser(userId);
		if(project == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PROJECT_ERROR_OBJECT);
		}
		if(user == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, UserService.USER_ERROR_OBJECT);
		}
		if(!project.getProjectMembers().contains(user)){
			project.getProjectMembers().add(user);
			user.getJoinedProjects().add(project);
		}
	}

	@Override
	public void removeUserFromProject(Integer projectId, Integer userId) throws BitflowException {
		ProjectDTO project = persistence.findProject(projectId);
		UserDTO user = persistence.findUser(userId);
		if(project == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PROJECT_ERROR_OBJECT);
		}
		if(user == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, UserService.USER_ERROR_OBJECT);
		}
		if(project.getProjectMembers().contains(user)){
			project.getProjectMembers().remove(user);
			user.getJoinedProjects().remove(project);
		}
	}

	@Override
	public void deleteProject(Integer projectId) throws BitflowException {
		ProjectDTO project = persistence.findProject(projectId);
		if(project == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PROJECT_ERROR_OBJECT);
		}
		
		persistence.deleteProject(project);
	}

	@Override
	public void deletePipeline(Integer pipelineId) throws BitflowException {
		PipelineDTO pipeline = persistence.findPipeline(pipelineId);
		if(pipeline == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PipelineService.PIPELINE_ERROR_OBJECT);
		}
		persistence.deletePipeline(pipelineId);
	}

	@Override
	public ProjectDTO createProject(ProjectDTO project) throws BitflowException {
		if (project.getName().length() > 256) {
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR,"Project name too long.");
		}
		project.setCreatedAt(new Date());
		persistence.saveObject(project);
		return project;
	}

	@Override
	public ProjectDTO updateProject(int projectId, ProjectDTO project) throws BitflowException {
		ProjectDTO pro = persistence.findProject(projectId);
		if(pro == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PROJECT_ERROR_OBJECT);
		}
		if (project.getName().length() > 256) {
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR,"Project name too long.");
		}
		Hibernate.initialize(pro.getUserdata());
		Hibernate.initialize(pro.getProjectMembers());
		Hibernate.initialize(pro.getPipelines());
		pro.setName(project.getName());
		return pro;
	}

}
