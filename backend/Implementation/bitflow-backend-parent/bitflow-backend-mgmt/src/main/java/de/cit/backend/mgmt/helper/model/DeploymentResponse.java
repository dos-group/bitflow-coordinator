package de.cit.backend.mgmt.helper.model;

public class DeploymentResponse {

	private DeploymentInformation[] partialDeployments;
	private int historyId;
	
	public DeploymentResponse(DeploymentInformation[] partialDeployments, int historyId) {
		this.partialDeployments = partialDeployments;
		this.historyId = historyId;
	}
	
	public DeploymentInformation[] getPartialDeployments() {
		return partialDeployments;
	}
	public int getHistoryId() {
		return historyId;
	}
}
