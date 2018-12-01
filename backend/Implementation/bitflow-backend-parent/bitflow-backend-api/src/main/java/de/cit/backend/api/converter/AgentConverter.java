package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.agent.api.model.Info;
import de.cit.backend.api.model.Agent;
import de.cit.backend.api.model.Agent.StatusEnum;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;

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

	public Agent convertToFrontend(AgentDTO in, Info info) {
		Agent out = convertToFrontend(in);
		out.setGoroutines(info.getGoroutines());
		out.setNumCores(info.getNumCores());
		out.setNumProcs(info.getNumProcs());
		//out.setTags(info.getTags());
		out.setTotalMem(info.getTotalMem());
		out.setUsedCpu(info.getUsedCpu());
		out.setUsedCpuCores(info.getUsedCpuCores());
		out.setUsedMem(info.getUsedMem());
		out.setStatus(AgentState.ONLINE.equals(in.getStatus()) ? StatusEnum.ONLINE : StatusEnum.OFFLINE);
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
