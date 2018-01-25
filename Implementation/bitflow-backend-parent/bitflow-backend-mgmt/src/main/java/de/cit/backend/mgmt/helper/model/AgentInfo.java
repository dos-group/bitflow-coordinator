package de.cit.backend.mgmt.helper.model;

public class AgentInfo {

	private static final int FORK_LIMIT = 3;
	private int currentAgentNumber = 0;
	private int limit;
	private boolean locked = false;
	private int forkCount = 0;
	private int forksOnSeparatAgents = 0;
	
	public AgentInfo(){
		this.limit = -1;
	}
	
	public AgentInfo(int limit){
		this.limit = limit;
	}
	
	private void incrementAgent(){
		if(isIncrementActive()){
			currentAgentNumber++;
		}
	}
	
	public int getCurrentAgent(){
		return currentAgentNumber;
	}

	public void closeForkBranch(){
		incrementAgent();
	}
	
	public void closeFork(){
		forkCount--;
		incrementAgent();
	}
	
	private boolean isIncrementActive(){
		return forksOnSeparatAgents > forkCount || !locked && (limit < 0 || currentAgentNumber < limit - 1);
	}
	
	/**
	 * This requires to have at least 3 more agents accessible.
	 * If not, the fork won't be executed on a separat agent
	 */
	public void incrementAgentByFork() {
		if(limit < 0 || currentAgentNumber + FORK_LIMIT <= limit - 1){
			currentAgentNumber++;
			forksOnSeparatAgents++;
		}else{
			locked = true;
		}
		forkCount++;
	}
}
