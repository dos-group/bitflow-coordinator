package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;

public class NotEmptyValidator extends Validator {

	public NotEmptyValidator(Object obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws ValidationException {
		if(this.objectToValidate == null ||
				this.objectToValidate instanceof String && ((String)this.objectToValidate).isEmpty()){
			throw new ValidationException(this.message);
		}
	}

}
