package org.nalby.zomato.exception;

public class BadParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BadParameterException(String msg) {
		super(msg);
	}

}
