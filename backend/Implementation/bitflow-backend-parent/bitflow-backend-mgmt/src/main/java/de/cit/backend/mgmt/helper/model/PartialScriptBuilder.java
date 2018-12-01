package de.cit.backend.mgmt.helper.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PartialScriptBuilder {

	private static final String OPEN_FORK = "{";
	private static final String CLOSE_FORK = "}";
	private static final String CLOSE_BRANCH = ";";
	
	private List<Integer> forkOnDifferentAgents;
	private Map<Integer, DeploymentInformation> partialScriptMap;

	public PartialScriptBuilder() {
		partialScriptMap = new HashMap<>();
		forkOnDifferentAgents = new ArrayList<>();
	}
	
	public void appendToScript(int agent, List<Integer> succAgents, String script){
		initIfNeeded(agent);
		partialScriptMap.get(agent).mergeSuccessors(succAgents);
		partialScriptMap.get(agent).appendToScript(script);
	}
	private void deleteFromScript(int agent, int count){
		partialScriptMap.get(agent).deleteFromScript(count);
	}

	public void forkDetected(Integer agent, int forkSizeOverall, int forkSizeOnDifferentAgents) {
		partialScriptMap.get(agent).setNumberOfProxySinks(forkSizeOnDifferentAgents);
		appendToScript(agent, null, " -> { ");
		for (int i = 0; i < forkSizeOnDifferentAgents; i++) {
			appendToScript(agent, null, DeploymentInformation.PLACEHOLDER_SINK + "; ");
		}
		if (forkSizeOnDifferentAgents == forkSizeOverall) {
			deleteFromScript(agent, 2);
			appendToScript(agent, null, "} ");
		}

		forkOnDifferentAgents.add(forkSizeOverall - forkSizeOnDifferentAgents);
	}
	
	public void initIfNeeded(int agent){
		if(!partialScriptMap.containsKey(agent)){
			partialScriptMap.put(agent, new DeploymentInformation(agent));
			
			if(partialScriptMap.size() > 1){
				partialScriptMap.get(agent).appendToScript(DeploymentInformation.PLACEHOLDER_SOURCE + " -> ");//add proxy source
			}
		}
	}

	public DeploymentInformation[] buildScripts() {
		validateAndClean();
		
		DeploymentInformation[] ret = new DeploymentInformation[partialScriptMap.size()];
		Integer[] keys = new Integer[partialScriptMap.size()];
		partialScriptMap.keySet().toArray(keys);
		Arrays.sort(keys);
		
		int i = 0;
		for(int key : keys){
			ret[i] = partialScriptMap.get(key);
			i++;
		}
		
		calculateTCPLimit(ret);
		
		return ret;
	}
	
	private void calculateTCPLimit(DeploymentInformation[] infos) {
		int countRef = 0;
		int identifier = 0;
		for(int i=0; i<infos.length; i++){
			countRef = 0;
			identifier = infos[i].getIdentifier();
			for(DeploymentInformation info : infos){
				if(info.getSuccessorAgents().contains(identifier)){
					countRef++;
				}
			}
			infos[i].setTcpLimit(countRef);
		}
	}

	private void validateAndClean(){
		for(DeploymentInformation info : partialScriptMap.values()){
			validateAndClean(info.getScriptBuider());
		}
	}

	private void validateAndClean(StringBuilder scriptBuilder) {
		int openForkcount = StringUtils.countMatches(scriptBuilder, OPEN_FORK);
		int closeForkcount = StringUtils.countMatches(scriptBuilder, CLOSE_FORK);
		
		if(openForkcount + 1 == closeForkcount && scriptBuilder.lastIndexOf(CLOSE_FORK) >= scriptBuilder.length() - 3){
			scriptBuilder.delete(scriptBuilder.length() - 3, scriptBuilder.length());
		}
		
		if(scriptBuilder.lastIndexOf(CLOSE_BRANCH) >= scriptBuilder.length() - 2){
			scriptBuilder.delete(scriptBuilder.length() - 2, scriptBuilder.length());
		}
	}
	
	public void closeParatheses(int agent){
		int openForkcount = StringUtils.countMatches(partialScriptMap.get(agent).getScriptBuider(), OPEN_FORK);
		int closeForkcount = StringUtils.countMatches(partialScriptMap.get(agent).getScriptBuider(), CLOSE_FORK);
		
		for(int i=0; i< openForkcount - closeForkcount; i++){
			appendToScript(agent, null, " } ");
		}
	}

	public void splitSourceToAgents(int currentAgent, List<Integer> succAgents, String sourceDef) {
		appendToScript(currentAgent, null, " -> ");
		
		for(int successorAgent : succAgents){
			if(currentAgent != successorAgent){
				if(partialScriptMap.containsKey(successorAgent)){
					throw new IllegalStateException("The order seems to be messed up, please fix.");
				}
				partialScriptMap.put(successorAgent, new DeploymentInformation(successorAgent));
				
				partialScriptMap.get(successorAgent).appendToScript(sourceDef + " -> ");
				
			}
		}
		partialScriptMap.get(currentAgent).removeSuccessors(succAgents);
	}
}
