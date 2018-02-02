package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import de.cit.backend.api.model.Capability;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;

public class CapabilityConverter implements Converter<CapabilityDTO, Capability> {

		public CapabilityDTO convertToBackend(Capability in) {
			CapabilityDTO out = new CapabilityDTO();
			out.setName(in.getName());
			out.setIsFork(in.getIsFork());
			out.setDescription(in.getDescription());
			if(in.getRequiredParams()!=null)
			{
				StringBuilder rparams = new StringBuilder() ;
				for (String str : in.getRequiredParams())
				{
					rparams.append(str);
					rparams.append(",");
				}
				out.setRequiredParams(rparams.toString());
			}
			if(in.getOptionalParams()!=null)
			{
				StringBuilder oparams = new StringBuilder() ;
				for (String str : in.getOptionalParams())
				{
					oparams.append(str);
					oparams.append(",");
				}
				out.setOptionalParams(oparams.toString());
			}
			return out;
		}

		public Capability convertToFrontend(CapabilityDTO in) {
			Capability out = new Capability();
			out.setName(in.getName());
			out.setIsFork(in.isIsFork());
			out.setDescription(in.getDescription());
			if(in.getRequiredParams()!=null) {
				List<String> rparams = new ArrayList<String>(Arrays.asList(in.getRequiredParams().split(",")));
				out.setRequiredParams(rparams);
			}
			if(in.getOptionalParams()!=null) {
				List<String> oparams = new ArrayList<String>(Arrays.asList(in.getOptionalParams().split(",")));
				out.setOptionalParams(oparams);
			}
			return out;
		}
		
		public List<Capability> convertToFrontend(Set<CapabilityDTO> in){
			List<Capability> out = new ArrayList<Capability>();
			for (CapabilityDTO capabilityDTO : in) {
				out.add(this.convertToFrontend(capabilityDTO));
			}
			return out;
		}

}
