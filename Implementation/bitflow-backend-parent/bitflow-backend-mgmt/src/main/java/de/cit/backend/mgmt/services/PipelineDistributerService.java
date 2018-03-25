package de.cit.backend.mgmt.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.ApiException;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.PipelineApi;
import de.cit.backend.agent.api.model.PipelineResponse;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.helper.model.DeploymentResponse;
import de.cit.backend.mgmt.helper.service.PartialScriptGenerator;
import de.cit.backend.mgmt.helper.service.PipelineDistributer;
import de.cit.backend.mgmt.helper.service.ScriptGenerator;
import de.cit.backend.mgmt.persistence.ConfigurationService;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;
import de.cit.backend.mgmt.persistence.model.enums.PipelineStateEnum;

@Singleton
public class PipelineDistributerService {

	private static final Logger log = Logger.getLogger(PipelineDistributerService.class);
	
	private static final String PORT_ERROR = "listen tcp %s: bind: Normalerweise";
	private static final String PARAM_TCP_LIMIT = "-tcp-limit 1";
	
	@Inject
	private ConfigurationService config;
	
	@Inject
	private PersistenceService persistence;
	
	@Inject
	private PipelineMonitoringService pipeMonitor;
	
	private List<AgentDTO> idleAgents = new ArrayList<>();
	private int maxSplit;
	private int proxyPort;
	private int proxyTries;
	
	
	@PostConstruct
	public void init(){
		idleAgents = reloadAvailableAgents();
		maxSplit = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_MAX_DISTRIBUTION, "1"));
		proxyPort = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_PROXY_PORT, "60001"));
		proxyTries = Integer.parseInt((String)config.getConfigByKey(ConfigurationService.CONFIG_PROXY_PORT_RETRY, "100"));
	}
	
	private List<AgentDTO> reloadAvailableAgents(){
		List<AgentDTO> list = persistence.findAgentsByState(AgentState.ONLINE);
		return list;
	}
	
	/**
	 * Deploys the given Pipeline. If possible the pipeline will be distributed onto multiple agents.
	 * 
	 * @param pipeline
	 * @return
	 * @throws BitflowException
	 */
	public DeploymentResponse distributedDeployment(PipelineDTO pipeline) throws BitflowException{
		if(pipeline.getPipelineSteps().isEmpty()){
			return null;
		}
		init();

		PipelineDistributer distributer;
		if(idleAgents.size() < maxSplit){
			distributer = new PipelineDistributer(idleAgents.size());
		}else{
			distributer = new PipelineDistributer(maxSplit);
		}
		//calculate a distribution
		distributer.distributePipeline(pipeline);
		//generate partial scripts for this distribution
		PartialScriptGenerator scriptGen = new PartialScriptGenerator();
		DeploymentInformation[] deployment = scriptGen.generateParallelScripts(pipeline);
		
		try{
			deployPipelines(deployment);
		}catch (BitflowException ex) {
			log.error(ex);
			log.info("Doing complete deployment instead.");
			deployment = deployOnSingleAgent(pipeline);
		}
		
		PipelineHistoryDTO hist = createPipelineHistory(pipeline);
		
		pipeMonitor.monitorPipeline(deployment, hist);
		
		return new DeploymentResponse(deployment, hist.getId());
	}

	private void deployPipelines(DeploymentInformation[] deployment) throws BitflowException {
		Map<Integer, String> agentMapping = new HashMap<>();
		
		for(DeploymentInformation info : deployment){
			log.info(info);
		}
		
		int i= 0;
		while(i < deployment.length){
			
			for(DeploymentInformation info : deployment){
				if(agentMapping.containsKey(info.getIdentifier())){
					continue;
				}
				//only assign a pipeline step to an agent, if all its dependencies are already assigned
				//dependencies meaning its successors
				if(allDependenciesFulfilled(agentMapping, info)){
					String proxyAdress = findPortAndDeploy(info, agentMapping);
					agentMapping.put(info.getIdentifier(), proxyAdress);
				}
			}
			i++;
		}
	}

	private boolean allDependenciesFulfilled(Map<Integer, String> agentMapping, DeploymentInformation info) {
		for(int i : info.getSuccessorAgents()){
			if(!agentMapping.containsKey(i)){
				return false;
			}
		}
		return true;
	}

	private String findPortAndDeploy(DeploymentInformation info, Map<Integer, String> agentMapping) throws BitflowException {
		String[] dependencies = new String[info.getSuccessorAgents().size()];
		
		for(int i=0;i<dependencies.length;i++){
			dependencies[i] = agentMapping.get(info.getSuccessorAgents().get(i));
		}
		
		if(idleAgents.isEmpty()){
			throw new BitflowException(ExceptionConstants.NO_AGENT_ONLINE_ERROR);
		}
		
		info.deployOnAgent(idleAgents.get(0), proxyPort);
		idleAgents.remove(0);
		
		return deployPipelineOn(info, dependencies);
	}


	private String deployPipelineOn(DeploymentInformation deploy, String[] sinkParams) throws BitflowException{
		for(int i = 0; i < proxyTries; i++){
			ApiClient conf = getApiClientConfig(deploy.getAgentAdress());
			PipelineApi pipelineApi = new PipelineApi(conf);
			
			try {
				log.info(deploy.getFormattedScript(i, sinkParams));
				PipelineResponse resp = pipelineApi.pipelinePost(deploy.getFormattedScript(i, sinkParams), null, deploy.getTcpLimitFormatted());
				log.info("Deployed with proxy " + deploy.getAdjustedTCPAdress(i));
				deploy.setPipelineIdOnAgent(resp.getID());
				deploy.deployedWithProxy(i, sinkParams);
				return deploy.getAdjustedTCPAdress(i);
			} catch (ApiException e) {
				log.debug(String.format(PORT_ERROR, deploy.getAdjustedTCPAdress(i)));
				if(e.getResponseBody() != null && e.getResponseBody().contains(String.format(PORT_ERROR, deploy.getAdjustedTCPAdress(i)))){
					continue;					
				}
			}			
		}
		
		throw new BitflowException(ExceptionConstants.DISTRIBUTED_DEPLOYMENT_FAILED_ERROR);
	}
	
	private DeploymentInformation[] deployOnSingleAgent(PipelineDTO pipeline) throws BitflowException {
		init();
		if(idleAgents.isEmpty()){
			throw new BitflowException(ExceptionConstants.NO_AGENT_ONLINE_ERROR);
		}
		
		DeploymentInformation deployInfo = new DeploymentInformation(0);
		deployInfo.deployOnAgent(idleAgents.get(0), proxyPort);
		idleAgents.remove(0);
		deployInfo.appendToScript(ScriptGenerator.generateScriptForPipeline(pipeline));
		
		ApiClient conf = getApiClientConfig(deployInfo.getAgentAdress());
		PipelineApi pipelineApi = new PipelineApi(conf);
		
		PipelineResponse resp;
		try {
			resp = pipelineApi.pipelinePost(deployInfo.getScript(), null, null);
			deployInfo.setPipelineIdOnAgent(resp.getID());
			return new DeploymentInformation[]{deployInfo};
		} catch (ApiException e) {
			log.error(e);
			throw new BitflowException(e);
		}
	}
	
	private ApiClient getApiClientConfig(String agendAdress){
		ApiClient conf = Configuration.getDefaultApiClient();
		conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);
		conf.setBasePath(agendAdress);
		return conf;
	}
	
	private PipelineHistoryDTO createPipelineHistory(PipelineDTO pipeline) {
		PipelineHistoryDTO hist = new PipelineHistoryDTO();
		hist.setStartedAt(new Date());
		hist.setStatus(PipelineStateEnum.RUNNING);
		hist.setPipeline(pipeline);
		hist.setScript(ScriptGenerator.generateScriptForPipeline(pipeline));
		
		persistence.saveObject(hist);
		persistence.flush();
		return hist;
	}
}
