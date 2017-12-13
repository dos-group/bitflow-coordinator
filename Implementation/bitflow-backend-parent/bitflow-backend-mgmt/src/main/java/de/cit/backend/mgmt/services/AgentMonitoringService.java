package de.cit.backend.mgmt.services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.InfosApi;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.AgentState;

@Singleton
@Startup
public class AgentMonitoringService {

	private static final String PONG = "pong";
	
	private static final Logger log = Logger.getLogger(AgentMonitoringService.class);
	
	@EJB
	private PersistenceService persistence;
	
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
			InfosApi agentApi = new InfosApi(conf);
			
			log.debug("Requesting agent at " + conf.getBasePath());
			try{
				String pong = agentApi.pingGet();
				if(pong.equals(PONG)){
					log.debug("Agent successfully responded.");
					agent.setStatus(AgentState.ONLINE);
				}
			}catch (Exception e) {
				log.debug("Agent did not respond successfully.");
				agent.setStatus(AgentState.OFFLINE);
			}
		}
	}
}
