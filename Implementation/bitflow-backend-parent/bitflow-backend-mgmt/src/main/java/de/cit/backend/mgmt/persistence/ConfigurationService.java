package de.cit.backend.mgmt.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.model.ConfigurationDTO;

@Stateless
public class ConfigurationService {

	private static final Logger log = Logger.getLogger(ConfigurationService.class);

	public static final String CONFIG_AGENT_MONITOR_INTERVAL = "BITFLOW_MONITOR_INTERVAL";
	public static final String CONFIG_MAX_DISTRIBUTION = "BITFLOW_MAX_AGENT_DIST";
	public static final String CONFIG_PROXY_PORT = "BITFLOW_PROXY_PORT";
	public static final String CONFIG_PROXY_PORT_RETRY = "BITFLOW_PROXY_PORT_RETRY";
	public static final String CONFIG_PIPELINE_MONITOR_INTERVAL = "BITFLOW_PIPELINE_MONITOR_INTERVAL";
	
	@PersistenceContext(unitName = "bitflow-backend-mgmt-server")
	private EntityManager entityManager;

	public Object getConfigByKey(String key, Object defaultValue) {
		String hqlQuery = "SELECT conf FROM ConfigurationDTO conf WHERE conf.configKey = :key";
		Query query = entityManager.createQuery(hqlQuery);
		query.setParameter("key", key);

		try {
			return ((ConfigurationDTO) query.getSingleResult()).getConfigValue();
		} catch (Exception e) {
			log.warn("Configuration with key " + key + " could not be retrieved.", e);
			return defaultValue;
		}
	}
}
