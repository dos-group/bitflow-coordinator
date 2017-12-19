package de.cit.backend.api.impl;

import java.util.Arrays;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.InfoApiService;
import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.converter.AgentConverter;
import de.cit.backend.api.model.Agent;
import de.cit.backend.api.model.Info;
import de.cit.backend.api.model.Tag;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.services.interfaces.IInfoService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class InfoApiServiceImpl extends InfoApiService {

	protected IInfoService infoService;

	public InfoApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			infoService = (IInfoService) ctx.lookup("java:module/InfoService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response infoGet(SecurityContext securityContext) throws NotFoundException {
		// do some magic!
		Info dummyInfo = new Info();
		dummyInfo.setNumberOfAgents(3);
		dummyInfo.setNumberOfIdleAgents(1);
		Agent agent1 = new Agent();
		agent1.setHostname("Hostname");
		agent1.setNumCores(8);
		Tag tag1 = new Tag();
		tag1.setResources("TestResource");
		tag1.setSlots("TestSlot?");
		agent1.setTags(tag1);
		dummyInfo.setAgents(Arrays.asList(agent1));

		List<AgentDTO> agents = infoService.loadInfos();
		Info info = new Info();
		info.setAgents(new AgentConverter().convertListToFrontend(agents));
		info.setNumberOfAgents(agents.size());
		
		return Response.ok().entity(info).build();
	}
}
