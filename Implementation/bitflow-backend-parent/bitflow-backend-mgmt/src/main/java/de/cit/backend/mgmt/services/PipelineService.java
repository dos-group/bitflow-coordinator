package de.cit.backend.mgmt.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Hibernate;
import org.jboss.logging.Logger;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
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
	public DeploymentInformation[] executePipeline(Integer projectId, Integer pipelineId) throws BitflowException {
		PipelineDTO pipeline = loadPipelineFromProject(projectId, pipelineId);	
		
		//pipelineDistributer.suggestPipelineDistribution(pipeline);
		//return pipelineDistributer.deployPipeline(pipeline);
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
		PipelineDTO pipe = persistence.findPipeline(pipelineId);
		if(pipe == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, PIPELINE_ERROR_OBJECT);
		}
		Hibernate.initialize(pipe.getPipelineHistory());
		
		return pipe.getPipelineHistory().get(0);
	}

}
