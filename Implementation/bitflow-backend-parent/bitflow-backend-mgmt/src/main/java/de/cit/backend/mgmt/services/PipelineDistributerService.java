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
import de.cit.backend.mgmt.helper.model.DeploymentInfo;
import de.cit.backend.mgmt.helper.service.PartialScriptGenerator;
import de.cit.backend.mgmt.helper.service.PipelineDistributer2;
import de.cit.backend.mgmt.helper.service.ScriptGenerator;
import de.cit.backend.mgmt.persistence.ConfigurationService;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;

@Singleton
public class PipelineDistributerService {

	private static final Logger log = Logger.getLogger(PipelineDistributerService.class);
	
	private static final String PORT_ERROR = "listen tcp %s: bind: Normalerweise";
	private static final String PARAM_TCP_LIMIT = "-tcp-limit 1";
	
	@EJB
	private ConfigurationService config;
	
	@EJB
	private PersistenceService persistence;
	
	private List<AgentDTO> idleAgents = new ArrayList<>();
	private int maxSplit;
	private int proxyPort;
	private int proxyTries;
	
	private boolean useLocalhost = true;//true;
	private final String local = "127.0.0.1"; 
	
	@PostConstruct
	public void init(){
		idleAgents = reloadAvailableAgents();
		maxSplit = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_MAX_DISTRIBUTION, "1"));
		proxyPort = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_PROXY_PORT, "60001"));
		proxyTries = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_PROXY_PORT_RETRY, "100"));
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
			PipelineDistributer2.distributePipeline(pipeline, idleAgents.size());
		}else{
			PipelineDistributer2.distributePipeline(pipeline, maxSplit);
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
	
	public List<PipelineResponse> distributedDeployment(PipelineDTO pipeline){
		if(pipeline.getPipelineSteps().isEmpty()){
			return null;
		}
		init();
		suggestPipelineDistribution(pipeline);
		DeploymentInfo[] deployment = PartialScriptGenerator.generateParallelScripts(pipeline);
		
		deployPipelines(deployment);
		//assignAgentsToPipeline(pipeline);
		
		
		return null;
	}
	
	private void deployPipelines(DeploymentInfo[] deployment) {
		
		Map<Integer, String> agentMapping = new HashMap<>();
		
		int i= 0;
		while(i < deployment.length){
			for(DeploymentInfo info : deployment){
				if(agentMapping.containsKey(info.getIdentifier())){
					continue;
				}
				if(dependenciesFulfilled(agentMapping, info)){
					agentMapping.put(info.getIdentifier(), findPortAndDeploy(info, agentMapping));
				}
			}
			i++;
		}
	}

	private boolean dependenciesFulfilled(Map<Integer, String> agentMapping, DeploymentInfo info) {
		for(int i : info.getSuccessorAgents()){
			if(!agentMapping.containsKey(i)){
				return false;
			}
		}
		return true;
	}

	private String findPortAndDeploy(DeploymentInfo info, Map<Integer, String> agentMapping) {
		String[] dependencies = new String[info.getSuccessorAgents().size()];
		for(int i=0;i<dependencies.length;i++){
			dependencies[i] = agentMapping.get(info.getSuccessorAgents().get(i));
		}
		
		
		
		info.deployOnAgent(idleAgents.get(0), proxyPort);
		idleAgents.remove(0);
		
		return deployPipelineOn(info, dependencies);
	}


	private String deployPipelineOn(DeploymentInfo deploy, String[] sinkParams){
		for(int i = 0; i < proxyTries; i++){
			ApiClient conf = Configuration.getDefaultApiClient();
			conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);
			conf.setBasePath(deploy.getAgentAdress());

			PipelineApi pipelineApi = new PipelineApi(conf);
			try {
				System.out.println(deploy.getFormattedScript(i));
				PipelineResponse resp = pipelineApi.pipelinePost(deploy.getFormattedScript(i, sinkParams), null, PARAM_TCP_LIMIT);
				System.out.println("Deployed on " + deploy.getAdjustedTCPAdress(i));
				return deploy.getAdjustedTCPAdress(i);
			} catch (ApiException e) {
				System.out.println(String.format(PORT_ERROR, deploy.getAdjustedTCPAdress(i)));
				if(e.getResponseBody() != null && e.getResponseBody().contains(String.format(PORT_ERROR, deploy.getAdjustedTCPAdress(i)))){
					continue;					
				}
			}			
		}
		return null;
	}
}
