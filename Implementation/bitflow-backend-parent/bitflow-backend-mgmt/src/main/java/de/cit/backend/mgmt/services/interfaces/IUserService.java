package de.cit.backend.mgmt.services.interfaces;

import javax.ejb.Local;

import de.cit.backend.mgmt.persistence.model.User;


@Local
public interface IUserService {

	User loadUser(int userId);
}
