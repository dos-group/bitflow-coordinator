package de.cit.backend.mgmt.exceptions;

import java.io.Serializable;

public class BitflowFrontendError implements Serializable {

	private int errorCode;
	private String errorMessage;
	
	BitflowFrontendError(BitflowException exception){
		this.errorCode = exception.getErrorCode();
		this.errorMessage = exception.getMessage();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
