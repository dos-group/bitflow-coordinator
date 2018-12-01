package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;

public class PortRangeValidator extends PositiveNumberOrNullValidator {

	public PortRangeValidator(Integer obj) {
		super(obj, "Port must be between 0 and 65535.");
	}

	@Override
	public void validate() throws ValidationException {
		super.validate();
		if((Integer) this.objectToValidate > 65535){
			throw new ValidationException(this.message);
		}
	}

}
