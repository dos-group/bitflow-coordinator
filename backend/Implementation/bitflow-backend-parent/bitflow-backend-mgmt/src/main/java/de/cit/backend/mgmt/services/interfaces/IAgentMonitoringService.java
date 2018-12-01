package de.cit.backend.mgmt.services.interfaces;

import de.cit.backend.agent.api.model.Info;
import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IAgentMonitoringService {

	Info getAgentInfo(Integer agentId);

}
