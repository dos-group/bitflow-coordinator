package de.cit.backend.mgmt.validation;

import java.util.List;

import de.cit.backend.mgmt.exceptions.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class ValidatorTest {

	private static final String LONG_STRING = "aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee"
			+ "aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee"
			+ "aaaaaaaaaa bbbbbbbbbb cccccccccc dddddddddd eeeeeeeeee";

	@Test
	public void testValidUser() throws ValidationException {
		final UserDTO user = createTestUser("TestAdmin", "test@test.com", "pwd");
		Validator.validate(Validator.getUserValidators(user, true));
	}

	@Test(expected = ValidationException.class)
	public void testUserWithEmptyUsername() throws ValidationException {
		final UserDTO user = createTestUser("", "test@test.com", "pwd");
		Validator.validate(Validator.getUserValidators(user, true));
	}

	@Test(expected = ValidationException.class)
	public void testUserWithEmptyPassword() throws ValidationException {
		final UserDTO user = createTestUser("TestAdmin", "test@test.com", "");
		Validator.validate(Validator.getUserValidators(user, true));
	}

	@Test
	public void testUserWithEmptyUsernameAndEmptyPassword() throws ValidationException {
		try {
			final UserDTO user = createTestUser("", "test@test.com", "");
			Validator.validate(Validator.getUserValidators(user, true));
		} catch (final ValidationException e) {
			Assert.assertTrue(e.getValidationMessages().length==2);
			return;
		}
		Assert.fail("No exception thrown!");
	}

	@Test
	public void testUserWithNullMainAndTooLongPassword() throws ValidationException {
		try {
			final UserDTO user = createTestUser("TestAdmin", null, LONG_STRING);
			Validator.validate(Validator.getUserValidators(user, true));
		} catch (final ValidationException e) {
			Assert.assertTrue(e.getValidationMessages().length==2);
			return;
		}
		Assert.fail("No exception thrown!");
	}

	private UserDTO createTestUser(String name, String email, String password) {
		UserDTO user = new UserDTO();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);

		return user;
	}
}
