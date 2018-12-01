package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.api.model.PipelineHistory;
import de.cit.backend.api.model.PipelineHistory.StatusEnum;
import de.cit.backend.mgmt.persistence.model.PipelineHistoryDTO;

public class PipelineHistoryConverter implements Converter<PipelineHistoryDTO, PipelineHistory> {

	public PipelineHistoryDTO convertToBackend(PipelineHistory instanceToConvert) {
		return null;
	}

	public PipelineHistory convertToFrontend(PipelineHistoryDTO instanceToConvert) {
		PipelineHistory hist = new PipelineHistory();
		hist.setStartedAt(instanceToConvert.getStartedAt());
		hist.setFinishedAt(instanceToConvert.getFinishedAt());
		hist.setScript(instanceToConvert.getScript());
		switch (instanceToConvert.getStatus()) {
        case RUNNING:  
        	hist.setStatus(StatusEnum.RUNNING);
            break;
        case FINISHED:  
        	hist.setStatus(StatusEnum.FINISHED);
            break;
        case FAILED:  
        	hist.setStatus(StatusEnum.FAILED);
            break;
        case TERMINATED:  
        	hist.setStatus(StatusEnum.TERMINATED);
            break;
		}
		
		hist.setID(instanceToConvert.getId());
		return hist;
	}
	
	public List<PipelineHistory> convertToFrontend(List<PipelineHistoryDTO> histDto) {
		List<PipelineHistory> histList = new ArrayList<PipelineHistory>();
		for (PipelineHistoryDTO hist : histDto) {
			histList.add(this.convertToFrontend(hist));
		}
		return histList;
}

}
