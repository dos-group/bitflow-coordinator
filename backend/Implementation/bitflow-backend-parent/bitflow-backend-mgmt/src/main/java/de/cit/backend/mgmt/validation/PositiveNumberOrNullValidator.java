package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;

public class PositiveNumberOrNullValidator extends Validator {

	public PositiveNumberOrNullValidator(Integer obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws ValidationException {
		if(this.objectToValidate != null && (Integer) this.objectToValidate < 0){
			throw new ValidationException(this.message);
		}
	}

}
