package de.cit.backend.mgmt.business;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.UserService;

public class UserBusinessTest extends AbstractTestProvider {

	@Inject
	private UserService userService;
	
	@Test
	public void loadUserTest() throws BitflowException{
		UserDTO user = userService.loadUser(ADMIN_NAME);
		
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getEmail().equals("cit@test.de"));
	}
	
	@Test
	public void loadAllUsersTest() throws BitflowException{
		List<UserDTO> users = userService.loadUsers();
		
		Assert.assertTrue(users.size() == 1);
	}
	
	@Test
	public void createUserTest() throws BitflowException{
		UserDTO user = createTestUser("createUserTest");
		
		user = userService.createUser(user);
		
		Assert.assertTrue(user.getId() > 0);
	}
}
