package de.cit.backend.mgmt.exceptions;

public enum ExceptionConstants {

	UNIMPLEMENTED_ERROR(1, "API-method not yet implemented."),
	USER_NOT_FOUND_ERROR(2, "Couldn't find specified user."),
	PROJEKT_NOT_FOUND_ERROR(3, "Couldn't find specified project."),
	AGENT_NOT_FOUND_ERROR(4,"Couldn't find specified agent."),
    VALIDATION_ERROR(10,"Validation failed."),
	UNKNOWN_ERROR(100, "Unknown error occured.");

	
	private int code;
	private String message;
	
	private ExceptionConstants(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
