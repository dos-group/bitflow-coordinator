package de.cit.backend.mgmt.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.AgentState;
import de.cit.backend.mgmt.services.interfaces.IInfoService;

@Stateless
@Local(IInfoService.class)
public class InfoService implements IInfoService {

	private static final Logger log = Logger.getLogger(InfoService.class);
	
	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}

	@Override
	public List<AgentDTO> loadInfos() {
		List<AgentDTO> agents = persistence.findAgentsByState(AgentState.ONLINE);
		
		return agents;
	}

}
