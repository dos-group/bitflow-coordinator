package de.cit.backend.mgmt.persistence.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "CONFIGURATION", catalog = "citBitDB", uniqueConstraints = @UniqueConstraint(columnNames = "CONFIG_KEY"))
public class ConfigurationDTO extends BaseIdEntity {
	
	private Integer id;
	private String configKey;
	private String configValue;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "CONFIG_KEY", nullable = false, length = 64, unique=true)
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	
	@Column(name = "CONFIG_VALUE", nullable = false, length = 64)
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}
