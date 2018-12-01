package de.cit.backend.mgmt.persistence.model;

public abstract class BaseIdEntity {

	
	public abstract Integer getId();
	
	public abstract void setId(Integer id);
	
	@Override
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(!(o instanceof BaseIdEntity)){
			return false;
		}
		return ((BaseIdEntity)o).getId() == this.getId();
	}
}
