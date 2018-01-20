package de.cit.backend.mgmt.helper;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

class SuccessorTracker {

	private List<Integer> wrongfullyPlacedSuccIndexes = new ArrayList<>();
	private List<Integer> rightfullyPlacedSuccIndexes = new ArrayList<>();
	
	public void addWronglyPlacedSuccessorIndex(int index){
		wrongfullyPlacedSuccIndexes.add(index);
	}
	
	public void addRightfullyPlacedSuccessorIndex(int index){
		rightfullyPlacedSuccIndexes.add(index);
	}
	
	public List<Integer> getWrongIndexes(){
		return wrongfullyPlacedSuccIndexes;
	}
	
	public int getIndexToOverride(int start){
		if(rightfullyPlacedSuccIndexes.contains(start)){
			return getIndexToOverride(start + 1);
		}else{
			return start;
		}
	}
	
	public boolean isRightfullyPlaced(int index){
		return rightfullyPlacedSuccIndexes.contains(index);
	}
	
	public static SuccessorTracker findWrongSuccessorIndexes(List<PipelineStepDTO> steps, List<Integer> succIndexes,
			int index, ForkStorage forkInfos) throws IllegalStateException {
		SuccessorTracker ret = new SuccessorTracker();

//		if(forkSizeLeft > 0 && succIndexes.size() == 1 && steps.get(succIndexes.get(0)).getStepNumber() == forkJoinStepNumber){
//			if (succIndexes.get(0) < index || succIndexes.get(0) > index + succIndexes.size() + forkSizeLeft) {
//				ret.addWronglyPlacedSuccessorIndex(succIndexes.get(0));
//			} else {
//				ret.addRightfullyPlacedSuccessorIndex(succIndexes.get(0));
//			}
//		}
		
		for (int i = 0; i < succIndexes.size(); i++) {
			if (succIndexes.get(i) < index || succIndexes.get(i) > index + succIndexes.size()) {
				ret.addWronglyPlacedSuccessorIndex(succIndexes.get(i));
			} else {
				ret.addRightfullyPlacedSuccessorIndex(succIndexes.get(i));
			}
		}
		return ret;
	}
	
	public static List<Integer> findSuccessorIndexes(List<PipelineStepDTO> steps, int index)
			throws IllegalStateException {
		List<Integer> succIndexes = new ArrayList<>();
		for (int i = 0; i < steps.get(index).getSuccessors().size(); i++) {
			succIndexes.add(findStepIndex(steps, steps.get(index).getSuccessors().get(i).getStepNumber()));
		}
		return succIndexes;
	}
	
	private static int findStepIndex(List<PipelineStepDTO> steps, int stepNumber) throws IllegalStateException {
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).getStepNumber() == stepNumber) {
				return i;
			}
		}
		throw new IllegalStateException(
				"There is no pipeline step with the number " + stepNumber + ", fix the references!");
	}
}
