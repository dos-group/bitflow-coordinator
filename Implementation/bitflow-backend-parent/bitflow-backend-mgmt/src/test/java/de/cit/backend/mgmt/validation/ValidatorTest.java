package de.cit.backend.mgmt.validation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class ValidatorTest {

	private static final String LONG_STRING = "aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee"
			+ "aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee"
			+ "aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee";
	@Test
	public void testUserValidation(){
		UserDTO user = createTestUser("TestAdmin", "test@test.com", "pwd");
		List<BitflowFrontendError> errors = Validator.validate(Validator.getUserValidators(user));
		Assert.assertTrue(errors.isEmpty());
		
		user = createTestUser("", "test@test.com", "pwd");
		errors = Validator.validate(Validator.getUserValidators(user));
		Assert.assertTrue(errors.size() == 1);
		
		user = createTestUser("", "test@test.com", "");
		errors = Validator.validate(Validator.getUserValidators(user));
		Assert.assertTrue(errors.size() == 2);
		
		user = createTestUser("TestAdmin", null, LONG_STRING);
		errors = Validator.validate(Validator.getUserValidators(user));
		Assert.assertTrue(errors.size() == 2);
	}

	private UserDTO createTestUser(String name, String email, String password) {
		UserDTO user = new UserDTO();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		
		return user;
	}
}
