package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cit.backend.mgmt.persistence.model.PipelineParameterDTO;

public class PipelineParamConverter implements Converter <PipelineParameterDTO, Map<String, String>> {

	public PipelineParameterDTO convertToBackend(Map<String, String> instanceToConvert) {
		PipelineParameterDTO paramDto = new PipelineParameterDTO();
		for(String key : instanceToConvert.keySet()){
			if(key.equalsIgnoreCase(ID_KEY)){
				paramDto.setId(Integer.valueOf(instanceToConvert.get(key)));
			}else{
				paramDto.setParamName(key);
				paramDto.setParamValue(instanceToConvert.get(key));				
			}
		}
		return paramDto;
	}

	public Map<String, String> convertToFrontend(PipelineParameterDTO instanceToConvert) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(instanceToConvert.getParamName(), instanceToConvert.getParamValue());
		map.put(ID_KEY, Integer.toString(instanceToConvert.getId()));
		return map;
	}

	public List<PipelineParameterDTO> convertToBackend(List<Object> params) {
		List<PipelineParameterDTO> out = new ArrayList<PipelineParameterDTO>();
		for (Object param : params) {
			PipelineParameterDTO paramDto = convertToBackend((Map<String, String>)param);
			if(paramDto != null){
				out.add(paramDto);
			}
		}
		return out;
	}
	
	public List<Map<String, String>> convertToFrontend(List<PipelineParameterDTO> params) {
		List<Map<String, String>> out = new ArrayList<Map<String, String>>();
		for (PipelineParameterDTO param : params) {
			out.add(convertToFrontend(param));
		}
		return out;
	}

}
