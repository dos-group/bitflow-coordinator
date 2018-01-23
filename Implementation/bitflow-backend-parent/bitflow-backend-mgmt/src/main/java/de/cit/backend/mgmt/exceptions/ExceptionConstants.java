package de.cit.backend.mgmt.exceptions;

public enum ExceptionConstants {

	UNIMPLEMENTED_ERROR(1, "API-method not yet implemented."),
	UNKNOWN_ERROR(2, "Unknown error occured.");

	
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
