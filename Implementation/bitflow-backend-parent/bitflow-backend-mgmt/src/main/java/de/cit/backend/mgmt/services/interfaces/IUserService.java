package de.cit.backend.mgmt.services.interfaces;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.UserDTO;


@Local
public interface IUserService {

	UserDTO loadUser(int userId);
}
