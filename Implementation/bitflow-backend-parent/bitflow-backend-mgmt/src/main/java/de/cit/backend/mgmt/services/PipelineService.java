package de.cit.backend.mgmt.services;

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
import de.cit.backend.mgmt.helper.model.DeploymentResponse;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.services.interfaces.IPipelineService;

@Stateless
@Local(IPipelineService.class)
public class PipelineService implements IPipelineService {

	private static final Logger log = Logger.getLogger(PipelineService.class);
	public static final String PIPELINE_ERROR_OBJECT = "Pipeline";
	
	@EJB
	private PipelineDistributerService pipelineDistributer;
	
	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}
	
	@Override
	public PipelineDTO saveNewPipeline(PipelineDTO pipeline, Integer projectId) throws BitflowException {
		ProjectDTO project = persistence.findProject(projectId);
		if(project == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, ProjectService.PROJECT_ERROR_OBJECT);
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
	public PipelineDTO loadPipelineFromProject(int projectId, int pipelineId) throws BitflowException {
		ProjectDTO pro = persistence.findProject(projectId);

		if(pro == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, ProjectService.PROJECT_ERROR_OBJECT);
		}
		PipelineDTO pipe = persistence.findPipeline(pipelineId);
		if(pipe == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PIPELINE_ERROR_OBJECT);
		}
		
		for (PipelineStepDTO step : pipe.getPipelineSteps()) {
			Hibernate.initialize(step.getSuccessors());
		}
		if (pro.getPipelines().contains(pipe)) {
			return pipe;
		} else {
			throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
		}
	}

	@Override
	public List<PipelineDTO> loadPipelinesFromProject(int projectId) throws BitflowException {
		ProjectDTO pro = persistence.findProject(projectId);
		if(pro == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, ProjectService.PROJECT_ERROR_OBJECT);
		}
		for(PipelineDTO pipe : pro.getPipelines()) {
			for (PipelineStepDTO step : pipe.getPipelineSteps()) {
				Hibernate.initialize(step.getSuccessors());
			}
		}
		return pro.getPipelines();
	}

	@Override
	public DeploymentResponse executePipeline(Integer projectId, Integer pipelineId) throws BitflowException {
		PipelineDTO pipeline = loadPipelineFromProject(projectId, pipelineId);	
		
		return pipelineDistributer.distributedDeployment(pipeline);//.get(0);
	}

	@Override
	public List<PipelineHistoryDTO> loadPipelineHistory(Integer projectId, Integer pipelineId) throws BitflowException {
		PipelineDTO pipe = persistence.findPipeline(pipelineId);
		if(pipe == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PIPELINE_ERROR_OBJECT);
		}
		Hibernate.initialize(pipe.getPipelineHistory());
		
		return pipe.getPipelineHistory();
	}

	@Override
	public PipelineHistoryDTO loadPipelineHistoryLast(Integer projectId, Integer pipelineId) throws BitflowException {
		List<PipelineHistoryDTO> history = loadPipelineHistory(projectId, pipelineId);
		return history.get(0);
	}

	@Override
	public void updatePipeline(int projectId, int pipelineId, PipelineDTO pipeline) throws BitflowException {
		PipelineDTO pipe = this.loadPipelineFromProject(projectId, pipelineId);
		pipe.setLastChanged(new Date());
		
		pipeline.setLastChanged(new Date());
		persistence.mergeObject(pipeline);
		
		for(PipelineStepDTO step : pipeline.getPipelineSteps()){
			step.getSuccessors().clear();
		}
		//FIXME further testing and simplification
		persistence.flush();
		setSuccessors(pipeline.getPipelineSteps());
		persistence.mergeObject(pipeline);
		persistence.flush();
	}
}
