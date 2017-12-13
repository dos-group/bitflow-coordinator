package de.cit.backend.mgmt.services;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

	private static final Logger log = Logger.getLogger(UserService.class);
	
	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}
	
	@Override
	public UserDTO loadUser(int userId) {
		return persistence.findUser(userId);
	}

	@Override
	public void createUser(UserDTO user) {
		persistence.createUser(user);
	}

	
	@Override
	public void updateUser(int userId, UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}
}
