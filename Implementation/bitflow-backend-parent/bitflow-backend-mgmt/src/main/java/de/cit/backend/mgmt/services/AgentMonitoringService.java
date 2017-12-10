package de.cit.backend.mgmt.services;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.InfosApi;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;

@Singleton
@Startup
public class AgentMonitoringService {

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
			conf.setBasePath("http://" + agent.getIpAddress() + ":" + agent.getPort());
			InfosApi agentApi = new InfosApi(conf);
			
			try{
				String pong = agentApi.pingGet();
				System.out.println(pong);
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Ping failed");
			}
		}
	}
}
