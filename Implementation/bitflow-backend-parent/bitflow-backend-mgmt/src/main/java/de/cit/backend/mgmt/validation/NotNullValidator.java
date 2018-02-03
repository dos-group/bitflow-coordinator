package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;

public class NotNullValidator extends Validator {

	public NotNullValidator(Object obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws BitflowException {
		if(this.objectToValidate == null){
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR, this.message);
		}
	}

}
