package de.cit.backend.mgmt.exceptions;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

public class BitflowFrontendError implements Serializable {

	private static final Logger log = Logger.getLogger(BitflowFrontendError.class);
	
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
	
	public static Response handleException(Exception ex){
		if(ex instanceof BitflowException){
			BitflowException be = (BitflowException)ex;
			return Response.status(be.getHttpStatus()).entity(be.toFrontendFormat()).build();
		}else{
			log.error(ex);
			return Response.status(500).entity(new BitflowException(ex).toFrontendFormat()).build();
		}
	}
}
