package de.cit.backend.mgmt.helper.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PartialScriptBuilder {

	private static final String OPEN_FORK = "{";
	private static final String CLOSE_FORK = "}";
	private static final String CLOSE_BRANCH = ";";
	
	private Map<Integer, DeploymentInformation> partialScriptMap;

	public PartialScriptBuilder() {
		partialScriptMap = new HashMap<>();
	}
	
	public void appendToScript(int agent, List<Integer> succAgents, String script){
		initIfNeeded(agent);
		partialScriptMap.get(agent).mergeSuccessors(succAgents);
		partialScriptMap.get(agent).appendToScript(script);
	}
	private void deleteFromScript(int agent, int count){
		partialScriptMap.get(agent).deleteFromScript(count);
	}
	
	public void forkDetected(int agent, int forkSizeOverall, int forkSizeOnDifferentAgents){
		partialScriptMap.get(agent).setNumberOfProxySinks(forkSizeOnDifferentAgents);
		appendToScript(agent, null, " -> { ");
		for(int i=0; i<forkSizeOnDifferentAgents;i++){
			appendToScript(agent, null, DeploymentInformation.PLACEHOLDER_SINK + "; ");
		}
		if(forkSizeOnDifferentAgents == forkSizeOverall){
			deleteFromScript(agent, 2);
			appendToScript(agent, null,"} ");
		}
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
		
		return ret;
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
	
	
}
