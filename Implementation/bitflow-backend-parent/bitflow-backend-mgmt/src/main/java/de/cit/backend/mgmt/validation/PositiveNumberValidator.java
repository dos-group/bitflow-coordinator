package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;

public class PositiveNumberValidator extends Validator {

	public PositiveNumberValidator(Integer obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws BitflowException {
		if((Integer) this.objectToValidate < 0){
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR, this.message);
		}
	}

}
