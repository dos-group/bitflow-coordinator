package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;

public class NotNullValidator extends Validator {

	public NotNullValidator(Object obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws ValidationException {
		if(this.objectToValidate == null){
			throw new ValidationException(this.message);
		}
	}

}
