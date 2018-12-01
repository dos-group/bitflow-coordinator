package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.ForkStorage;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

/**
 * Supports full capability of distributing a pipeline.
 * 
 * @author Sven
 *
 */
public class PipelineDistributer {

	
	private static final Logger log = Logger.getLogger(PipelineDistributer.class);
	
	private int agentNumber;
	private int increment;
	private List<Integer> usedAgents;
	private int maxAgents;
	private boolean locked;
	
	public PipelineDistributer() throws BitflowException{
		this(-1);
	}
	
	public PipelineDistributer(int maxNumberOfAgents) throws BitflowException{
		agentNumber = 0;
		increment = 1;
		usedAgents = new ArrayList<>();
		maxAgents = maxNumberOfAgents;
		locked = false;
		
		if(maxNumberOfAgents == 0){
			throw new BitflowException(ExceptionConstants.NO_AGENT_ONLINE_ERROR);
		}
	}
	
	public void distributePipeline(PipelineDTO pipeline) throws BitflowException{
		PipelineSort.sortPipeline(pipeline);

		if(pipeline.isSequence()){
			return;//a sequence will be deployed at exactly one agent, it will not be split up
		}
		
		try {
			List<Integer> visited = new ArrayList<>();
			List<PipelineStepDTO> steps = pipeline.getPipelineSteps();
			List<Integer> neverJoinedForks = new ArrayList<>();
			List<Integer> joinedForks = new ArrayList<>();
			ForkStorage forks = new ForkStorage();
			
			distributePipelineRecursive(steps, 0, forks, visited, neverJoinedForks, joinedForks);
			
		} catch (IllegalStateException e) {
			log.error("Error while distributing the pipeline!",e);
			throw new BitflowException(e);
		}
	}

	private void distributePipelineRecursive(List<PipelineStepDTO> steps, int index, ForkStorage forkStorage,
			List<Integer> visitedIndexes, List<Integer> neverJoinedForks, List<Integer> joinedForks) {
		if(visitedIndexes.contains(index)){
			distributePipelineRecursive(steps, index + 1, forkStorage, visitedIndexes, neverJoinedForks, joinedForks);
			return;
		}else{
			visitedIndexes.add(index);
		}
		if(index >= steps.size()){
			return;
		}
		
		steps.get(index).setAgentAdvice(this.agentNumber);
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		
		if(succIndexes.size() > 1){
			int joinNumber = ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes);
			forkStorage.addNewFork(succIndexes.size(), joinNumber);
			checkAgentNumber(succIndexes.size(), joinNumber);
			if(!locked){
				usedAgents.add(this.agentNumber);
			}
		} else if(succIndexes.size() == 0){
			if(forkStorage.isForkBranchClosedAtSink()){
				forkStorage.closeForkBranch();
				increment();
			}else if(forkStorage.isForkClosedAtSink()){
				int remainingForks = forkStorage.closeFork();
				if(index != steps.size() - 1){
					forkStorage.closeForkBranch();
				}
				if(remainingForks == this.usedAgents.size()){
					this.locked = false;
				}
				increment();
			}
			return;
		} else {
			if(forkStorage.isForkBranchClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkStorage.closeForkBranch();
				increment();
				return;
			}else if(forkStorage.isForkClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				int remainingForks = forkStorage.closeFork();
				
				if(!locked){
					int lastNumberBeforeFork = this.usedAgents.remove(this.usedAgents.size() - 1);
					//this.agentNumber = lastNumberBeforeFork;
					//this.increment++;
					increment();
				}
				if(remainingForks == this.usedAgents.size()){
					this.locked = false;
				}
			}
		}
		
		if(succIndexes.size() == 1){
			distributePipelineRecursive(steps, index + 1, forkStorage, visitedIndexes, neverJoinedForks, joinedForks);
		}else{
			for (int i = 0; i < succIndexes.size(); i++) {
				distributePipelineRecursive(steps, index + 1 + i, forkStorage, visitedIndexes, neverJoinedForks, joinedForks);
			}
		}
	}
	
	private void checkAgentNumber(int forkSize, int joinNumber) {
		if(locked || this.maxAgents < 0){
			return;
		}
		if(joinNumber < 0){
			if(this.agentNumber + this.increment + this.usedAgents.size() + forkSize - 1> this.maxAgents){//+ this.usedAgents.size() 
				locked = true;
			}
		}else{
			if(this.agentNumber + this.increment + this.usedAgents.size() + forkSize > this.maxAgents){//+ this.usedAgents.size() 
				locked = true;
			}
		}
	}
	
	private void increment(){
		if(!locked){
			this.agentNumber += increment;
		}
	}

	private void assignAgents(List<PipelineStepDTO> steps, List<Integer> neverJoinedForks, List<Integer> joinedForks, int numberOfAgents) {
		int agent = 0;
		List<Integer> successors = new ArrayList<>();
		for(PipelineStepDTO step : steps){
			if(canAssignNewAgent(step, successors, numberOfAgents, agent)){
				agent++;
			}
			if(neverJoinedForks.contains(step.getStepNumber())
					|| joinedForks.contains(step.getStepNumber()) ){
				addAllButOne(successors, step);
			}
			
			step.setAgentAdvice(agent);
		}
	}
	
	private void addAllButOne(List<Integer> successors, PipelineStepDTO step) {
		List<Integer> flatList = step.getSuccessorsFlatUpdated();
		flatList.remove(0);
		successors.addAll(flatList);
	}

	private boolean canAssignNewAgent(PipelineStepDTO step, List<Integer> successors ,int numberOfAgents , int currentAgent){
		return successors.contains(step.getStepNumber()) 
				&& (numberOfAgents < 0 || currentAgent < numberOfAgents - 1);
	}
	
	public void clear(){
		this.agentNumber = 0;
		this.increment = 1;
		usedAgents.clear();
		this.locked = false;
	}
}
