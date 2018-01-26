package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.UserDTO;


@Local
public interface IUserService {

	UserDTO loadUser(int userId) throws BitflowException;

	UserDTO loadUser(String username) throws BitflowException;
	
	List<UserDTO> loadUsers();
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(int userId, UserDTO user) throws BitflowException;
	
	void deleteUser(int userId) throws BitflowException;
	
	void changePassword(int userId, String oldPw, String newPw) throws BitflowException;
}
