package de.cit.backend.mgmt.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.UserRoleEnum;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

	private static final Logger log = Logger.getLogger(UserService.class);
	public static final String USER_ERROR_OBJECT = "User";
	
	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}

	@Override
	public UserDTO loadUser(int userId) throws BitflowException {
		UserDTO user = persistence.findUser(userId);
		if(user == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		return user;
	}

	@Override
	public UserDTO loadUser(String username) throws BitflowException {
		UserDTO user = persistence.findUser(username);
		if(user == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		return user;
	}

	public List<UserDTO> loadUsers() {
		return persistence.findUsers();
	}
	
	@Override
	public UserDTO createUser(UserDTO user) {
		user.setRole(UserRoleEnum.STANDARD);
		persistence.createUser(user);
		return user;
	}

	@Override
	public UserDTO updateUser(int userId, UserDTO user) throws BitflowException {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		// TODO E-Mail and Name Validation 
		dbuser.setEmail(user.getEmail());
		dbuser.setName(user.getName());
		return dbuser;
	}

	@Override
	public void deleteUser(int userId) throws BitflowException {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		persistence.deleteUser(userId);
	}

	@Override
	public void changePassword(int userId, String oldPw, String newPw) throws BitflowException {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		if (dbuser.getPassword() == oldPw) {
			dbuser.setPassword(newPw);			
		} else {
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR, "Old password does not match the new one.");
		}

	}
}
