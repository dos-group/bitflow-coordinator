package de.cit.backend.mgmt.persistence.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PIPELINE_STEP_PARAM", catalog = "citBitDB")
public class PipelineParameterDTO extends BaseIdEntity {

	private Integer id;
	private String paramName;
	private String paramValue;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "PARAM_NAME", nullable = false)
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	@Column(name = "PARAM_VALUE", nullable = false)
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	@Override
	public String toString(){
		if(paramValue.isEmpty()){
			return paramName + "=" + "''";
		}
		
		return paramName + "=" + paramValue;
	}
}
