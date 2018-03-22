package de.cit.backend.mgmt.services;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.jboss.logging.Logger;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;
import de.cit.backend.mgmt.services.interfaces.IInfoService;

@Stateless
@Local(IInfoService.class)
public class InfoService implements IInfoService {

	private static final Logger log = Logger.getLogger(InfoService.class);
	public static final String AGENT_ERROR_OBJECT = "Agent";
	
	@Inject
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}

	@Override
	public List<AgentDTO> loadInfos() {
		List<AgentDTO> agents = persistence.findAgents();
		return agents;
	}

	@Override
	public Set<CapabilityDTO> loadAgentCapabilities(int agentId) throws BitflowException {
		AgentDTO agent = persistence.findAgent(agentId);
		if(agent==null)
		{
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, AGENT_ERROR_OBJECT);
		}
		Hibernate.initialize(agent.getCapabilities());
		return agent.getCapabilities();
	}

	@Override
	public void registerAgent(String ip, int port) throws BitflowException {
		AgentDTO agent = new AgentDTO();
		if (port < 0 || port > 65535)
		{
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR,"Invalid port range");
		}
		agent.setIpAddress(ip);
		agent.setPort(port);
		persistence.saveObject(agent);
	}

}
