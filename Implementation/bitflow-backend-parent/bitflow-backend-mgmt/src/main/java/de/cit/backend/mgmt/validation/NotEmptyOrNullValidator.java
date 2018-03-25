package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;

public class NotEmptyOrNullValidator extends Validator {

	public NotEmptyOrNullValidator(Object obj, String message) {
		super(obj, message);
	}

	@Override
	public void validate() throws ValidationException {
		if(this.objectToValidate != null &&
				this.objectToValidate instanceof String && ((String)this.objectToValidate).isEmpty()){
			throw new ValidationException(this.message);
		}
	}

}
