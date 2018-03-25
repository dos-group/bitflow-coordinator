package de.cit.backend.mgmt.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends BitflowException {

    private final String[] validationMessages;

    public ValidationException(final String message) {
        super(ExceptionConstants.VALIDATION_ERROR, message);
        this.validationMessages = new String[] { message };
    }

    public ValidationException(final List<ValidationException> exceptions) {
        super(ExceptionConstants.VALIDATION_ERROR, "Multiple validation exceptions got reported.");
        this. validationMessages = exceptions.stream().map(ValidationException::getMessage).collect(Collectors.toList()).toArray(new String[0]);
    }

    public String[] getValidationMessages() {
        return this.validationMessages;
    }

}
