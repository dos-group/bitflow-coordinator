package de.cit.backend.mgmt.validation;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.ExceptionConstants;
import de.cit.backend.mgmt.exceptions.ValidationException;

public class StringLengthValidator extends Validator {

	private int maxLength;
	
	public StringLengthValidator(String obj, String message, int maxLength) {
		super(obj, message);
		this.maxLength = maxLength;
	}

	@Override
	public void validate() throws ValidationException {
		if(this.objectToValidate == null){
			return;
		}
		
		if(((String)this.objectToValidate).length() > this.maxLength){
			throw new ValidationException(this.message);
		}
	}

}
