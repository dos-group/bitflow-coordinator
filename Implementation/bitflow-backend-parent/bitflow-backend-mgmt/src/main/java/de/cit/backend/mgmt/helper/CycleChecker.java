package de.cit.backend.mgmt.helper;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;

public class CycleChecker {

	public static void checkForCycles(PipelineDTO pipeline){
		List<PipelineStepDTO> steps = pipeline.getPipelineSteps();
		for(int i = 0; i < steps.size();i++){
			List<Integer> visitedSteps = new ArrayList<>();
			checkForCyclesRecursive(steps, i, visitedSteps, 0);
		}
	}

	private static void checkForCyclesRecursive(List<PipelineStepDTO> steps, int index, List<Integer> visitedSteps, int size) {
		if(visitedSteps.contains(steps.get(index).getStepNumber())){
			throw new IllegalStateException("The pipeline is not supposed to contain cycles! Fix that.");
		}
		visitedSteps.add(steps.get(index).getStepNumber());
		size++;
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		for(int successor : succIndexes){
			checkForCyclesRecursive(steps, successor, visitedSteps, size);
			clearList(visitedSteps, size);
		}
	}
	
	private static void clearList(List<Integer> list, int size){
		for(int i=list.size() - 1; i >= size; i-- ){
			list.remove(i);
		}
	}
}
