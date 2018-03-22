package de.cit.backend.mgmt.services;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.InfosApi;
import de.cit.backend.agent.api.model.Info;
import de.cit.backend.mgmt.converter.CapabilityConverter;
import de.cit.backend.mgmt.persistence.ConfigurationService;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;
import de.cit.backend.mgmt.services.interfaces.IAgentMonitoringService;

@Singleton
@Startup
@Local(IAgentMonitoringService.class)
public class AgentMonitoringService implements IAgentMonitoringService{

	private static final String PONG = "pong";
	
	private static final Logger log = Logger.getLogger(AgentMonitoringService.class);
	
	@Inject
	private ConfigurationService config;
	
	@Inject
	private PersistenceService persistence;

	private final Map<Integer, Info> agentInfos = new HashMap<>();
	
	@Schedule(second="*/30", minute="*", hour="*", persistent=false)
	public void monitorAgents(){
		//read agents from db
		List<AgentDTO> agents = persistence.findAgents();
		

		//query the status of each agent
		ApiClient conf = Configuration.getDefaultApiClient();
		conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);

		for(AgentDTO agent : agents){
			agent.setLastChecked(new Date());
			conf.setBasePath("http://" + agent.getIpAddress() + ":" + agent.getPort());
			
			//check if agent is still online
			boolean isOnline = pingAgent(agent, conf);
			
			//if null, read capabilities of agent
			//if(isOnline && agent.getCapabilities() == null){
			  if(isOnline) {
				updateAgentInfos(agent, conf);
				if(agent.getCapabilities().isEmpty()) {
					getCapabilitiesAgent(agent, conf);					
				}

			}
		}
	}
	
	private boolean pingAgent(AgentDTO agent, ApiClient conf){
		InfosApi agentApi = new InfosApi(conf);
		
		log.debug("Requesting agent at " + conf.getBasePath());
		try{			
			String pong = agentApi.pingGet();
			if(pong.trim().equals(PONG)){
				log.debug("Agent successfully responded.");
				agent.setStatus(AgentState.ONLINE);
				return true;
			}
		}catch (Exception e) {
			log.debug("Agent did not respond successfully.");
			agent.setStatus(AgentState.OFFLINE);
		}
		return false;
	}

	private void updateAgentInfos(AgentDTO agent, ApiClient conf){
		InfosApi agentApi = new InfosApi(conf);
		try{
			final Info info = agentApi.infoGet();
			synchronized (agentInfos) {
				agentInfos.put(agent.getId(), info);
			}
		}catch (Exception e) {
			log.error("GetInfo failed", e);
		}
	}

	public Info getAgentInfo(Integer agentId) {
		final Info info;
		synchronized (agentInfos) {
			info = agentInfos.get(agentId);
		}
		return info == null ? new Info() : info;
	}
	
	private void getCapabilitiesAgent(AgentDTO agent, ApiClient conf){
		InfosApi agentApi = new InfosApi(conf);
		try {
			/*			
			 List<CapabilityDTO> apicapas = new de.cit.backend.mgmt.converter.CapabilityConverter()
			.convertToBackend(agentApi.capabilitiesGet());
			List<CapabilityDTO> capabilities = new ArrayList<CapabilityDTO>();
			for (CapabilityDTO apicapa : apicapas) {
				CapabilityDTO capa = persistence.findCapability(apicapa);
				if (capa == null) {
					persistence.saveObject(apicapa);
					capa = apicapa;
				}
				if (!agent.getCapabilities().contains(capa)) {
					agent.getCapabilities().add(capa);
				}
				capabilities.add(capa);
			}
			agent.getCapabilities().retainAll(capabilities);
			*/

			List<CapabilityDTO> apicapas = new CapabilityConverter()
					.convertToBackend(agentApi.capabilitiesGet());
			Set<CapabilityDTO> capabilities = new HashSet<CapabilityDTO>();
			for (CapabilityDTO apicapa : apicapas) {
				CapabilityDTO capa = persistence.findCapability(apicapa);
				if (capa == null) {
					persistence.saveObject(apicapa);
					capa = apicapa;
				}
				capabilities.add(capa);
			}
			//agent.getCapabilities().clear();
			agent.setCapabilities(capabilities);
		} catch (Exception e) {
			log.error("GetCapabilities failed", e);
		}
	}

}
