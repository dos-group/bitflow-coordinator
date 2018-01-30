package de.cit.backend.api.impl;

import java.util.ArrayList;
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
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.services.interfaces.IAgentMonitoringService;
import de.cit.backend.mgmt.services.interfaces.IInfoService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class InfoApiServiceImpl extends InfoApiService {

	protected IInfoService infoService;
	protected IAgentMonitoringService agentMonitoringService;

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
		List<AgentDTO> agents = infoService.loadInfos();
		Info info = new Info();
		final List<Agent> agentList = new ArrayList<Agent>(agents.size());
		final AgentConverter agentConverter = new AgentConverter();
		for(AgentDTO agent : agents) {
			final de.cit.backend.agent.api.model.Info agentInfo = agentMonitoringService.getAgentInfo(agent.getId());
			agentList.add(agentConverter.convertToFrontend(agent, agentInfo));
		}
		info.setAgents(agentList);
		info.setNumberOfAgents(agents.size());
		return Response.ok().entity(info).build();
	}
}
