package de.cit.backend.mgmt.helper.model;

import java.util.ArrayList;
import java.util.List;

public class ForkStorage {

	private List<Integer> currentForkList = new ArrayList<>();
	private List<Integer> forkSizeList = new ArrayList<>();
	private List<Integer> forkJoinSteps = new ArrayList<>();
	private int size = 0;
	
	public void addNewFork(int size, int joiner){
		forkSizeList.add(size - 1);
		forkJoinSteps.add(joiner);
		currentForkList.add(0);
		this.size++;
	}
	
	public void closeForkBranch(){
		int count = currentForkList.remove(currentForkList.size() - 1);
		currentForkList.add(count + 1);
	}
	
	public void closeFork(){
		forkSizeList.remove(size - 1);
		forkJoinSteps.remove(size - 1);
		currentForkList.remove(size - 1);
		size--;
	}
	
	public boolean isForkClosed(int stepnumber){
		if(!isFork()){
			return false;
		}
		return isForkJoiner(stepnumber) && currentForkList.get(size - 1) == forkSizeList.get(size - 1);
	}
	
	public boolean isForkBranchClosed(int stepnumber){
		if(!isFork()){
			return false;
		}
		return isForkJoiner(stepnumber) && currentForkList.get(size - 1) != forkSizeList.get(size - 1);
	}
	
	public boolean isForkBranchClosedAtSink(){
		if(!isFork()){
			return false;
		}
		return currentForkList.get(size - 1) != forkSizeList.get(size - 1);
	}
	
	public boolean isForkClosedAtSink(){
		if(!isFork()){
			return false;
		}
		return currentForkList.get(size - 1) == forkSizeList.get(size - 1);
	}
	

	private boolean isForkJoiner(int stepnumber){
		if(!isFork()){
			return false;
		}
		return forkJoinSteps.get(size - 1) == stepnumber;
	}
	
	public boolean isFork(){
		return size >= 1;
	}
	
	
}
