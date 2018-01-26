package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.cit.backend.api.model.Capability;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;

public class CapabilityConverter implements Converter<CapabilityDTO, Capability> {

		public CapabilityDTO convertToBackend(Capability in) {
			CapabilityDTO out = new CapabilityDTO();
			out.setName(in.getName());
			out.setIsFork(in.getIsFork());
			out.setDescription(in.getDescription());
			StringBuilder rparams = new StringBuilder() ;
			for (String str : in.getRequiredParams())
			{
				rparams.append(str);
				rparams.append(",");
			}
			out.setRequiredParams(rparams.toString());
			StringBuilder oparams = new StringBuilder() ;
			for (String str : in.getOptionalParams())
			{
				oparams.append(str);
				oparams.append(",");
			}
			out.setOptionalParams(oparams.toString());
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
		
		public List<Capability> convertToFrontend(List<CapabilityDTO> in){
			List<Capability> out = new ArrayList<Capability>();
			for (CapabilityDTO capabilityDTO : in) {
				out.add(this.convertToFrontend(capabilityDTO));
			}
			return out;
		}

}
