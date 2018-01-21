package de.cit.backend.mgmt.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.helper.PipelineDistributer;
import de.cit.backend.mgmt.persistence.ConfigurationService;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.AgentState;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

@Singleton
public class PipelineDistributerService {

	private static final Logger log = Logger.getLogger(PipelineDistributerService.class);
	
	@EJB
	private ConfigurationService config;
	
	@EJB
	private PersistenceService persistence;
	
	private List<AgentDTO> idleAgents = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		idleAgents = reloadAvailableAgents();
	}
	
	private List<AgentDTO> reloadAvailableAgents(){
		return persistence.findAgentsByState(AgentState.IDLE);
	}
	
	public PipelineDTO suggestPipelineDistribution(PipelineDTO pipeline){
		PipelineDistributer.distributePipeline(pipeline, idleAgents.size());
		
		assignAgentsToPipeline(pipeline);
		return pipeline;
	}
	
	private void assignAgentsToPipeline(PipelineDTO pipeline){
		Map<Integer, Integer> agentMap = new HashMap<>();
		int index = 0;
		for(PipelineStepDTO step : pipeline.getPipelineSteps()){
			if(!agentMap.keySet().contains(step.getAgentAdvice())){
				agentMap.put(step.getAgentAdvice(), index);
				index++;
			}
			step.setAgent(idleAgents.get(agentMap.get(step.getAgentAdvice())));
		}
	}
	
	public boolean checkForAvailabilityOfAgents(){
		
		return true;
	}
	
	public void deployPipeline(PipelineDTO pipeline){
		
	}
}
