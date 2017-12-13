package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.api.model.Agent;
import de.cit.backend.mgmt.persistence.model.AgentDTO;

public class AgentConverter implements Converter<AgentDTO, Agent> {

	public AgentDTO convertToBackend(Agent instanceToConvert) {
		// TODO Auto-generated method stub
		return null;
	}

	public Agent convertToFrontend(AgentDTO in) {
		Agent out = new Agent();
		out.setHostname(in.getIpAddress() + ":" + in.getPort());

		return out;
	}
	
	public List<Agent> convertListToFrontend(List<AgentDTO> list){
		List<Agent> outList = new ArrayList<Agent>();
		for(AgentDTO t : list){
			outList.add(convertToFrontend(t));
		}
		
		return outList;
	}

}
