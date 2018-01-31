package de.cit.backend.mgmt.services;

import java.util.Date;
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
import de.cit.backend.mgmt.persistence.model.enums.UserRoleEnum;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

	private static final Logger log = Logger.getLogger(UserService.class);
	public static final String USER_ERROR_OBJECT = "User";
	
	@EJB
	private PersistenceService persistence;
	
	@EJB
	private PipelineMonitoringService pipeMonitor;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}

	@Override
	public UserDTO loadUser(int userId) throws BitflowException {
		UserDTO user = persistence.findUser(userId);
		pipeMonitor.monitorPipeline(userId);
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
		user.setRegisteredSince(new Date());
		persistence.saveObject(user);
		return user;
	}

	@Override
	public UserDTO updateUser(int userId, UserDTO user) throws BitflowException {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		if(!dbuser.getName().equals(user.getName()))
		{
			throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
		}
		// TODO E-Mail Validation 
		dbuser.setEmail(user.getEmail());
		//dbuser.setName(user.getName());
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
	public void changePassword(int userId, String userName, String oldPw, String newPw) throws BitflowException {
		UserDTO dbuser = persistence.findUser(userId);
		if (dbuser == null){
			throw new BitflowException(ExceptionConstants.OBJECT_NOT_FOUND_ERROR, USER_ERROR_OBJECT);
		}
		if (!dbuser.getName().equals(userName)) {
			throw new BitflowException(ExceptionConstants.UNAUTHORIZED_ERROR);
		}
		if (dbuser.getPassword().equals(oldPw)) {
			dbuser.setPassword(newPw);			
		} else {
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR, "Old password does not match the stored one.");
		}

	}
}
