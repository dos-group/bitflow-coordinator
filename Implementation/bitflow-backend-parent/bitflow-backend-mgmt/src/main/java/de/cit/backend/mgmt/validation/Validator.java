package de.cit.backend.mgmt.validation;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public abstract class Validator {

	protected Object objectToValidate;
	protected String message;
	
	public Validator(Object obj, String message){
		this.message = message;
		this.objectToValidate = obj;
	}
	
	public abstract void validate() throws BitflowException;
	
	public static List<Validator> getUserValidators(UserDTO user){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotEmptyValidator(user.getEmail(), "Email must be provided."));
		validators.add(new NotEmptyValidator(user.getName(), "Name must be provided."));
		validators.add(new NotEmptyValidator(user.getPassword(), "Password must be provided."));
		
		validators.add(new StringLengthValidator(user.getEmail(), "Limit for email is 128 characters.", 128));
		validators.add(new StringLengthValidator(user.getName(), "Limit for name is 128 characters.", 128));
		validators.add(new StringLengthValidator(user.getPassword(), "Limit for password is 128 characters.", 128));
		
		return validators;
	}
	
	public static List<BitflowFrontendError> validate(List<Validator> validators){
		List<BitflowFrontendError> errors = new ArrayList<>();
		for(Validator val : validators){
			try {
				val.validate();
			} catch (BitflowException e) {
				errors.add(e.toFrontendFormat());
			}
		}
		return errors;
	}
}
