package de.cit.backend.api.converter;

import de.cit.backend.api.model.DeploymentInfo;
import de.cit.backend.api.model.PartialDeployment;
import de.cit.backend.mgmt.helper.model.DeploymentInformation;

public class DeploymentInfoConverter implements Converter<DeploymentInformation, DeploymentInfo> {

	public DeploymentInformation convertToBackend(DeploymentInfo instanceToConvert){
		return null;
	}
	
	public DeploymentInfo convertToFrontend(DeploymentInformation instanceToConvert){
		DeploymentInfo deployment = new DeploymentInfo();
		deployment.setStatus("running");//TODO
		return null;
	}
	
	public DeploymentInfo convertToFrontend(DeploymentInformation[] instanceToConvert){
		DeploymentInfo deployment = new DeploymentInfo();
		deployment.setStatus("running");//TODO
		deployment.setHistoryID(-1);
		for(DeploymentInformation d : instanceToConvert){
			PartialDeployment part = new PartialDeployment();
			part.setAgentAdress(d.getAgentAdress());
			part.setPartialScript(d.getScript());//FIXME
			part.setPipelineIdOnAgent(d.getPipelineIdOnAgent());
			
			deployment.getPartialDeployments().add(part);
		}
		return deployment;
	}
}
