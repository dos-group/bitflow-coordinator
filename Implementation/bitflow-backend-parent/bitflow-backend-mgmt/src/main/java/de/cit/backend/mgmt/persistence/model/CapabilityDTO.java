package de.cit.backend.mgmt.persistence.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "CAPABILITY", catalog = "citBitDB")
public class CapabilityDTO extends BaseIdEntity implements java.io.Serializable {

	private Integer id;
	private String name;
	private boolean isFork;
	private String description;
	private String requiredParams;
	private String optionalParams;
	private List<AgentDTO> agents = new ArrayList<>();
	
	public CapabilityDTO() {
	}

	public CapabilityDTO(String name, boolean isFork) {
		this.name = name;
		this.isFork = isFork;
	}

	public CapabilityDTO(String name, boolean isFork, String description, String requiredParams, String optionalParams) {
		this.name = name;
		this.isFork = isFork;
		this.description = description;
		this.requiredParams = requiredParams;
		this.optionalParams = optionalParams;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IS_FORK", nullable = false)
	public boolean isIsFork() {
		return this.isFork;
	}

	public void setIsFork(boolean isFork) {
		this.isFork = isFork;
	}

	@Column(name = "DESCRIPTION", length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "REQUIRED_PARAMS", length = 128)
	public String getRequiredParams() {
		return this.requiredParams;
	}

	public void setRequiredParams(String requiredParams) {
		this.requiredParams = requiredParams;
	}

	@Column(name = "OPTIONAL_PARAMS", length = 128)
	public String getOptionalParams() {
		return this.optionalParams;
	}

	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}

	@ManyToMany(mappedBy="capabilities")
	public List<AgentDTO> getAgents() {
		return this.agents;
	}

	public void setAgents(List<AgentDTO> agents) {
		this.agents = agents;
	}
	
}
