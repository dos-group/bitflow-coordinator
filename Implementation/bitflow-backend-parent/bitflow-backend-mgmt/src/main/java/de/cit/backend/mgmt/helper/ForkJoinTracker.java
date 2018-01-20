package de.cit.backend.mgmt.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

class ForkJoinTracker {

	public static int findForkJoinStepNumber(List<PipelineStepDTO> steps, int index, List<Integer> succIndexes) throws IllegalStateException {
		
		List<List<Integer>> parallelSteps = new ArrayList<>();
		for(Integer succIndex : succIndexes){
			parallelSteps.add(new ArrayList<>());
		}
		
		int count = 0;
		for(Integer succIndex : succIndexes){
			int res = findForkJoinStepNumberRecursive(steps, Arrays.asList(succIndex), parallelSteps, count);
			if(res >= 0){
				return res;
			}
			count++;
		}
		return -1;
	}

	private static int findForkJoinStepNumberRecursive(List<PipelineStepDTO> steps, List<Integer> succIndexes,
			List<List<Integer>> parallelSteps, int forkIndex) throws IllegalStateException {

		int stepnumber = checkForCommonNumberInMultipleLists(parallelSteps);
		if(stepnumber >= 0){
			return stepnumber;
		}
		
		for(int i=0; i<succIndexes.size();i++){
			List<Integer> successors = SuccessorTracker.findSuccessorIndexes(steps, succIndexes.get(i));
			for(Integer successor : successors){
				parallelSteps.get(forkIndex).add(steps.get(successor).getStepNumber());				
			}			
		}
		
		for(Integer succIndex : succIndexes){
			int res = findForkJoinStepNumberRecursive(steps, SuccessorTracker.findSuccessorIndexes(steps, succIndex), parallelSteps, forkIndex);
			if(res >= 0){
				return res;
			}
		}
		
		return -1;
	}

	private static int checkForCommonNumberInMultipleLists(List<List<Integer>> parallelSteps) {
		boolean found = false;
		for(Integer stepnumber : parallelSteps.get(0)){
			for (int i = 1; i < parallelSteps.size(); i++) {
				if(parallelSteps.get(i).contains(stepnumber)){
					found = true;
				}else{
					found = false;
					break;
				}
			}
			if(found){
				return stepnumber;
			}
		}
		return -1;
	}
}
