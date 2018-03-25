package de.cit.backend.mgmt.helper.model;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.persistence.model.AgentDTO;

public class DeploymentInformation {

	public static final String LOCAL_IP = "127.0.0.1";
	public static final String PLACEHOLDER_SOURCE = "%source%";
	public static final String PLACEHOLDER_SINK = "%sink%";
	public static final String HTTP_PREFIX = "http://";
	public static final String LISTEN_PREFIX = "listen://";
	public static final String TCP_LIMIT = "-tcp-limit ";
	
	private int identifier;
	private String agentAdress;
	private StringBuilder scriptBuilder;
	private String tcpIP;
	private int tcpPort;
	private int tcpLimit;
	private boolean withProxy;
	private int numberOfProxySinks;
	private List<Integer> successorAgents;
	private int pipelineIdOnAgent = -1;
	private int portIndex;
	private String[] sinkParams;
	
	
	public DeploymentInformation(int identifier) {
		this.scriptBuilder = new StringBuilder();
		this.successorAgents = new ArrayList<>();
		this.identifier = identifier;
	}

	public String getAgentAdress() {
		return HTTP_PREFIX + agentAdress;
	}
	public void setAgentAdress(String agentAdress) {
		this.agentAdress = agentAdress;
	}
	public String getScript() {
		return scriptBuilder.toString();
	}
	StringBuilder getScriptBuider() {
		return scriptBuilder;
	}
	public String getTcpIP() {
		return tcpIP;
	}
	public void setTcpIP(String tcpIP) {
		this.tcpIP = tcpIP;
	}
	public int getTcpPort() {
		return tcpPort;
	}
	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}
	public boolean isWithProxy() {
		return withProxy;
	}
	public void setWithProxy(boolean withProxy) {
		this.withProxy = withProxy;
	}
	public int getIdentifier(){
		return this.identifier;
	}
	public void appendToScript(String script){
		this.scriptBuilder.append(script);
	}
	public void deleteFromScript(int count){
		this.scriptBuilder.delete(this.scriptBuilder.length() - count, this.scriptBuilder.length());
	}
	public int getNumberOfProxySinks() {
		return numberOfProxySinks;
	}
	public void setNumberOfProxySinks(int numberOfProxySinks) {
		this.numberOfProxySinks = numberOfProxySinks;
	}
	public List<Integer> getSuccessorAgents(){
		return this.successorAgents;
	}
	
	public int getPipelineIdOnAgent() {
		return pipelineIdOnAgent;
	}

	public void setPipelineIdOnAgent(int pipelineIdOnAgent) {
		this.pipelineIdOnAgent = pipelineIdOnAgent;
	}

	public String getAdjustedTCPAdress(int i){
		return tcpIP + ":" + (tcpPort + i);
	}
	
	public String getLocalTCPAdress(int i){
		return LOCAL_IP + ":" + (tcpPort + i);
	}
	
	private String getSinkDef(String sinkIpAndPort) {
		if(sinkIpAndPort.contains(this.tcpIP)){
			//this pipeline step runs on same machine as the successor
			return LOCAL_IP + ":" + extractPort(sinkIpAndPort);
		}else{
			return sinkIpAndPort;
		}
	}

	public String getFormattedScript(int portIndex, String... sinks){
		String script = getScript();
		if(sinks != null){
			for(int i=0;i<sinks.length;i++){
				script = script.replaceFirst(PLACEHOLDER_SINK, getSinkDef(sinks[i]));
			}
		}
		script = script.replaceFirst(PLACEHOLDER_SOURCE, LISTEN_PREFIX + getLocalTCPAdress(portIndex));
		return script;
	}

	public String getFormattedScript(){
		return getFormattedScript(this.portIndex, this.sinkParams);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("DeploymentInfo " + identifier + ": [" + numberOfProxySinks + " Sinks; ");
		sb.append(getScript());
		sb.append("; Successors: " + successorAgents);
		return sb.toString();
	}

	public void mergeSuccessors(List<Integer> succAgents) {
		if(succAgents == null){
			return;
		}
		for(int successor : succAgents){
			if(!successorAgents.contains(successor) && successor != this.identifier){
				successorAgents.add(successor);
			}
		}
	}
	
	public void removeSuccessors(List<Integer> succAgents) {
		for(Integer successor : succAgents){
			if(successorAgents.contains(successor)){
				successorAgents.remove(successor);
			}
		}
	}
	
	public void deployOnAgent(AgentDTO agentDto, int proxyPort){
		this.agentAdress = agentDto.getIpAddress() + ":" + agentDto.getPort();
		this.tcpIP = agentDto.getIpAddress();
		this.tcpPort = proxyPort;
	}
	
	public void deployedWithProxy(int portIndex, String... sinks){
		this.portIndex = portIndex;
		this.sinkParams = sinks;
	}

	public void setTcpLimit(int count) {
		this.tcpLimit = count;
	}
	public int getTcpLimit() {
		return this.tcpLimit;
	}
	public String getTcpLimitFormatted() {
		if(getScript().contains(PLACEHOLDER_SOURCE)){
			return TCP_LIMIT + this.tcpLimit;//-tcp-limit 1
		}
		return null;
	}
	
	public static String extractPort(String ipAndPort) {
		return ipAndPort.substring(ipAndPort.indexOf(":") + 1);
	}
}
