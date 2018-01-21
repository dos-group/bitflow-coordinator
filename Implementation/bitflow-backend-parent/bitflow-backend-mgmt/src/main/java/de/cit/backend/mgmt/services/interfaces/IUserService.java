package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.UserDTO;


@Local
public interface IUserService {

	UserDTO loadUser(int userId);

	UserDTO loadUser(String username);
	
	List<UserDTO> loadUsers();
	
	void createUser(UserDTO user);
	
	void updateUser(int userId, UserDTO user);
	
	void deleteUser(int userId);
}
