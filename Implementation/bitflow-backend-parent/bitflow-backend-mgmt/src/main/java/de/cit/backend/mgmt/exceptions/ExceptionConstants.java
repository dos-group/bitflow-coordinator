package de.cit.backend.mgmt.exceptions;

public enum ExceptionConstants {

	UNIMPLEMENTED_ERROR(1, "API-method not yet implemented.", 501),
	USER_NOT_FOUND_ERROR(2, "Couldn't find specified user.", 404),
	PROJEKT_NOT_FOUND_ERROR(3, "Couldn't find specified project.", 404),
	AGENT_NOT_FOUND_ERROR(4,"Couldn't find specified agent.", 404),
	OBJECT_NOT_FOUND_ERROR(5, "The %s you requested does not exist or could not be found.", 404),
    VALIDATION_ERROR(10,"Validation failed: %s", 400),
    UNAUTHORIZED_ERROR(21, "You are not authorized to execute the requested action.", 403),
    PIPELINE_VALIDATION_ERROR(31, "The pipeline you provided seems to have some errors: %s", 400);

	
	private int code;
	private String message;
	private int httpResponseCode;
	
	private ExceptionConstants(int code, String message){
		this(code, message, 400);
	}
	
	private ExceptionConstants(int code, String message, int httpResponseCode){
		this.code = code;
		this.message = message;
		this.httpResponseCode = httpResponseCode;
	}
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHttpResponseCode() {
		return httpResponseCode;
	}
}
