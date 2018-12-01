package de.cit.backend.mgmt.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.cit.backend.agent.api.model.Capability;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;

public class CapabilityConverter extends Converter <CapabilityDTO, Capability> {
	
	public CapabilityDTO convertToBackend(Capability in) {
		CapabilityDTO out = new CapabilityDTO();
		out.setName(in.getName());
		out.setIsFork(in.getIsFork());
		out.setDescription(in.getDescription());
		StringBuilder rparams = new StringBuilder();
		if (in.getRequiredParams() != null)
		{
			for (String str : in.getRequiredParams())
			{
				rparams.append(str);
				rparams.append(",");
			}
			out.setRequiredParams(rparams.toString());
		}
		StringBuilder oparams = new StringBuilder() ;
		if(in.getOptionalParams()!=null)
		{
			for (String str : in.getOptionalParams())
			{
				oparams.append(str);
				oparams.append(",");
			}
			out.setOptionalParams(oparams.toString());
		}
		return out;
	}
	
	public List<CapabilityDTO> convertToBackend(List<Capability> in){
		List<CapabilityDTO> out = new ArrayList<CapabilityDTO>();
		for (Capability capability : in) {
			out.add(this.convertToBackend(capability));
		}
		return out;
	}

	public Capability convertToFrontend(CapabilityDTO in) {
		Capability out = new Capability();
		out.setName(in.getName());
		out.setIsFork(in.isIsFork());
		out.setDescription(in.getDescription());
		
		List<String> rparams = new ArrayList<String>(Arrays.asList(in.getRequiredParams().split(",")));
		List<String> oparams = new ArrayList<String>(Arrays.asList(in.getOptionalParams().split(",")));
		out.setRequiredParams(rparams);
		out.setOptionalParams(oparams);
		
		return out;
	}
}
