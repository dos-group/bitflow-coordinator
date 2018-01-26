package de.cit.backend.mgmt.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.ValidationException;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;
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
	public UserDTO loadUser(String username) {
		return persistence.findUser(username);
	}

	public List<UserDTO> loadUsers() {
		return persistence.findUsers();
	}
	
	@Override
	public void createUser(UserDTO user) {
		user.setRole(UserRoleEnum.STANDARD);
		persistence.createUser(user);
	}

	@Override
	public UserDTO updateUser(int userId, UserDTO user) {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null)
		{
			throw new IllegalArgumentException("Provided user id incorrect!");
		}
		// TODO E-Mail and Name Validation 
		dbuser.setEmail(user.getEmail());
		dbuser.setName(user.getName());
		return dbuser;
	}

	@Override
	public void deleteUser(int userId) {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null)
		{
			throw new IllegalArgumentException("Provided user id incorrect!");
		}
		persistence.deleteUser(userId);
	}

	@Override
	public void changePassword(int userId, String oldPw, String newPw) {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null)
		{
			throw new IllegalArgumentException("Provided user id incorrect!");
		}
		if (dbuser.getPassword() == oldPw) {
			dbuser.setPassword(newPw);			
		} else {
			throw new ValidationException("The password is incorrect!");
		}

	}
}
