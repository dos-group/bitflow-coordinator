package de.cit.backend.mgmt.exceptions;

public class BitflowException extends Exception{
	
	private int errorCode;
	
	public BitflowException(int errorCode, Exception ex) {
		super(ex.getMessage());
		this.errorCode = errorCode;
	}
	
	@Deprecated
	public BitflowException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public BitflowException(ExceptionConstants error) {
		super(error.getMessage());
		this.errorCode = error.getCode();
	}
	
	public BitflowFrontendError toFrontendFormat(){
		return new BitflowFrontendError(this);
	}
	
	public int getErrorCode(){
		return this.errorCode;
	}
}
