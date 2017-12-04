package de.cit.backend.mgmt.services;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.services.interfaces.IUserService;
import de.cit.backend.persistence.model.Userdata;

@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

	@EJB
	private PersistenceService persistence;
	//FIXME persistence access not working yet
	
	@PostConstruct
	public void init(){
		System.out.println("PostConstruct - UserService");
	}
	
	public void testCall(){
		System.out.println("UserService is up and running");
	}
	
	@Override
	public Userdata loadUser(int userId) {
		return null;
	}
	//TODO
	

}
