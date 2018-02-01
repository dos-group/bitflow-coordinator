package de.cit.backend.mgmt.services;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.cit.backend.agent.ApiClient;
import de.cit.backend.agent.ApiException;
import de.cit.backend.agent.Configuration;
import de.cit.backend.agent.api.PipelineApi;
import de.cit.backend.agent.api.model.PipelineResponse;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;
import de.cit.backend.mgmt.persistence.model.enums.PipelineStateEnum;

@Stateless
@LocalBean
public class PipelineMonitoringService {

	private static final Logger log = Logger.getLogger(PipelineMonitoringService.class);
	private static final int QUERY_PERIOD = 30000;
	
	@EJB
	private PersistenceService persistence;
	
	@Asynchronous
	public void monitorPipeline(int id){
		for(int i = 0; i<20; i++){
			try {
				Thread.sleep(10000);
				log.info("Monitor " + id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Asynchronous
	public void monitorPipeline(DeploymentInformation[] deployment, PipelineHistoryDTO hist) {
		
		boolean finished = false;
		
		while(true){
			finished = true;
			for(DeploymentInformation deploy : deployment){
				ApiClient conf = Configuration.getDefaultApiClient();
				conf.getHttpClient().setConnectTimeout(10, TimeUnit.SECONDS);
				conf.setBasePath(deploy.getAgentAdress());
				PipelineApi pipelineApi = new PipelineApi(conf);
				try {
					PipelineResponse response = pipelineApi.pipelineIdGet(deploy.getPipelineIdOnAgent());
					log.info(response.getStatus());
					if(!response.getStatus().equalsIgnoreCase("finished")){
						finished = false;
						if(response.getStatus().equalsIgnoreCase("failed")){
							pipelineFailed(hist);
							return;
						}
					}
				} catch (ApiException e) {
					log.error(e);
					finished = false;
				}
				
			}
			
			if(finished){
				pipelineFinished(hist);
				return;
			}
			
			try {
				Thread.sleep(QUERY_PERIOD);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	private void pipelineFailed(PipelineHistoryDTO hist) {
		hist.setFinishedAt(new Date());
		hist.setStatus(PipelineStateEnum.FAILED);
		persistence.mergeObject(hist);
		log.info("Pipeline failed: " + hist.getPipeline().getId());
	}

	private void pipelineFinished(PipelineHistoryDTO hist) {
		hist.setFinishedAt(new Date());
		hist.setStatus(PipelineStateEnum.FINISHED);
		persistence.mergeObject(hist);
		log.info("Pipeline finished: " + hist.getPipeline().getId());
	}
}
