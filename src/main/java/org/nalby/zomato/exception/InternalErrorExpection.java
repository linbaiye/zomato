package org.nalby.zomato.exception;

public class InternalErrorExpection extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InternalErrorExpection(String msg) {
		super(msg);
	}
}
