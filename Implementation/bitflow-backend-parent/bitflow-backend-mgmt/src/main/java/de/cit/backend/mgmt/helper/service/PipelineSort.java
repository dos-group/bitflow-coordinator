package de.cit.backend.mgmt.helper.service;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.helper.model.ForkJoinTracker;
import de.cit.backend.mgmt.helper.model.ForkStorage;
import de.cit.backend.mgmt.helper.model.SuccessorTracker;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;

public class PipelineSort {

	public static void sortPipeline(PipelineDTO pipeline) {
		CycleChecker.checkForCycles(pipeline);
		List<PipelineStepDTO> steps = pipeline.getPipelineSteps();

		if(steps.isEmpty()){
			return;
		}
		handleSourceOnFirstPosition(steps);

		List<Integer> visited = new ArrayList<>();
		handleSuccessorsRecursive(steps, 0, new ForkStorage(), visited);
	}

	private static void handleSuccessorsRecursive(List<PipelineStepDTO> steps, int index, ForkStorage forkInfos, List<Integer> visitedIndexes)
			throws IllegalStateException {
		if(visitedIndexes.contains(index)){
			handleSuccessorsRecursive(steps, index + 1 , forkInfos, visitedIndexes);
			return;
		}else{
			visitedIndexes.add(index);
		}
		if(index >= steps.size()){
			return;
		}
		
		List<Integer> succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		
		if(succIndexes.size() > 1){
			forkInfos.addNewFork(succIndexes.size(), ForkJoinTracker.findForkJoinStepNumber(steps, index, succIndexes));
		}else if(succIndexes.size() == 0){
			return;
		}else {
			if(forkInfos.isForkBranchClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkInfos.closeForkBranch();
				return;
			}else if(forkInfos.isForkClosed(steps.get(succIndexes.get(0)).getStepNumber())){
				forkInfos.closeFork();
			}
		}

		SuccessorTracker succTracker;// = SuccessorTracker.findWrongSuccessorIndexes(steps, succIndexes, index, forkInfos);//forkCurrent, forkJoinStepNumber

		int j = 0;
		while((succTracker = SuccessorTracker.findWrongSuccessorIndexes(steps, succIndexes, index, forkInfos)).getWrongIndexes().size() > 0){
			for (int i = 0; i < succTracker.getWrongIndexes().size(); i++) {
				rearangeList(steps, succTracker.getWrongIndexes().get(i), succTracker.getIndexToOverride(index + i + j + 1));
			}
			j += succTracker.getWrongIndexes().size();
			succIndexes = SuccessorTracker.findSuccessorIndexes(steps, index);
		}

		
		
		if(succIndexes.size() == 1){
			handleSuccessorsRecursive(steps, index + 1 , forkInfos, visitedIndexes);
		}else{
			for (int i = 0; i < succIndexes.size(); i++) {//i, succIndexes.size() - 1, forkJoinStepNumber
				handleSuccessorsRecursive(steps, index + 1 + i, forkInfos, visitedIndexes);
			}
		}
	}

	private static void handleSourceOnFirstPosition(List<PipelineStepDTO> steps) throws IllegalStateException {
		if (steps.get(0).getType() != StepTypeEnum.SOURCE) {
			int sourceIndex = findSourceIndex(steps);
			rearangeList(steps, sourceIndex, 0);
		}
	}

	private static void rearangeList(List<PipelineStepDTO> steps, int from, int to) {
		PipelineStepDTO source = steps.remove(from);
		steps.add(to, source);
	}

	private static int findSourceIndex(List<PipelineStepDTO> steps) throws IllegalStateException {
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).getType() == StepTypeEnum.SOURCE) {
				return i;
			}
		}
		throw new IllegalStateException("There is no source defined in the pipeline!");
	}
}
