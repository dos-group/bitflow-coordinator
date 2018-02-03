package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;

public class PortRangeValidator extends PositiveNumberValidator {

	public PortRangeValidator(Integer obj, String messge) {
		super(obj, messge);
	}

	@Override
	public void validate() throws BitflowException {
		super.validate();
		if((Integer) this.objectToValidate > 65535){
			throw new BitflowException(ExceptionConstants.VALIDATION_ERROR, this.message);
		}
	}

}
