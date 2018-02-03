package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;

public class NotEmptyOrNullValidator extends Validator {

	public NotEmptyOrNullValidator(Object obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws BitflowException {
		if(this.objectToValidate != null &&
				this.objectToValidate instanceof String && ((String)this.objectToValidate).isEmpty()){
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR, this.message);
		}
	}

}
