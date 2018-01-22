package de.cit.backend.mgmt.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.ApiException;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.PipelineApi;
import de.cit.backend.agent.api.model.PipelineResponse;
import de.cit.backend.mgmt.helper.PipelineDistributer;
import de.cit.backend.mgmt.helper.ScriptGenerator;
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
	private int maxSplit;
	
	private boolean useLocalhost = false;//true;
	private final String local = "127.0.0.1"; 
	
	@PostConstruct
	public void init(){
		idleAgents = reloadAvailableAgents();
		maxSplit = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_MAX_DISTRIBUTION, 1));
	}
	
	private List<AgentDTO> reloadAvailableAgents(){
		List<AgentDTO> list = persistence.findAgentsByState(AgentState.ONLINE);
		if(useLocalhost){
			return list.stream().filter((a) -> a.getIpAddress().contains(local)).collect(Collectors.toList());
		}else{
			return list.stream().filter((a) -> !a.getIpAddress().contains(local)).collect(Collectors.toList());
		}
	}
	
	public PipelineDTO suggestPipelineDistribution(PipelineDTO pipeline){
		if(idleAgents.size() < maxSplit){
			PipelineDistributer.distributePipeline(pipeline, idleAgents.size());
		}else{
			PipelineDistributer.distributePipeline(pipeline, maxSplit);
		}
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
		agentMap.clear();
	}
	
	public PipelineResponse deployPipeline(PipelineDTO pipeline){
		if(pipeline.getPipelineSteps().isEmpty()){
			return null;
		}
		AgentDTO agent = pipeline.getPipelineSteps().get(0).getAgent();
		if(agent == null){
			log.error("No agent seems to be assigned to this pipeline!");
		}
		
		ApiClient conf = Configuration.getDefaultApiClient();
		conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);
		conf.setBasePath("http://" + agent.getIpAddress() + ":" + agent.getPort());
		
		PipelineApi pipelineApi = new PipelineApi(conf);
		log.debug("Requesting agent at " + conf.getBasePath());
		
		try {
			PipelineResponse resp = pipelineApi.pipelinePost(ScriptGenerator.generateScriptForPipeline(pipeline), null, null);
			return resp;
		} catch (ApiException e) {
			log.error(e);
		}
		return null;
	}
}
