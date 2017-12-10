package de.cit.backend.mgmt.services.interfaces;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.UserDTO;


@Local
public interface IUserService {

	UserDTO loadUser(int userId);
	
	void createUser(UserDTO user);
	
	void updateUser(int userId, UserDTO user);
	
	void deleteUser(int userId);
}
