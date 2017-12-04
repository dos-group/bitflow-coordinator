package de.cit.backend.mgmt.services.interfaces;

import de.cit.backend.persistence.model.Userdata;

public interface IUserService {

	Userdata loadUser(int userId);
}
