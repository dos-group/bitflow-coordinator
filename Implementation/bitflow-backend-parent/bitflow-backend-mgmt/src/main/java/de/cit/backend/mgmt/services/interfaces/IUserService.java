package de.cit.backend.mgmt.services.interfaces;

import javax.ejb.Local;

import de.cit.backend.persistence.model.Userdata;

@Local
public interface IUserService {

	Userdata loadUser(int userId);
}
