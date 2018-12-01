package de.cit.backend.mgmt.exceptions;

import org.jboss.logging.Logger;

public class BitflowException extends Exception{

	private static final Logger log = Logger.getLogger(BitflowException.class);
	
	public static final int UNKNOWN_ERROR = 99;
	private int errorCode;
	private int httpResponseCode;
	
	public BitflowException(int errorCode, Exception ex) {
		super(ex.getMessage());
		this.errorCode = errorCode;
		this.httpResponseCode = 500;
	}
	
	public BitflowException(Exception ex) {
		this(UNKNOWN_ERROR, ex);
	}
	
	public BitflowException(ExceptionConstants error, String... messageParams) {
		super(String.format(error.getMessage(), (Object[]) messageParams));
		this.errorCode = error.getCode();
		this.httpResponseCode = error.getHttpResponseCode();
	}
	
	public BitflowFrontendError toFrontendFormat(){
		log.error(getLocalizedMessage());
		return new BitflowFrontendError(this);
	}
	
	public int getErrorCode(){
		return this.errorCode;
	}
	
	public int getHttpStatus(){
		return this.httpResponseCode;
	}
}
