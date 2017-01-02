package org.nalby.zomato.response;

public class ResponseWrapper {
	private ErrorCode error;
	private Object data;
	
	public ResponseWrapper(ErrorCode errorCode) {
		error = errorCode;
	}
	
	public ResponseWrapper(ErrorCode errorCode, Object data) {
		error = errorCode;
		this.data = data;
	}

	public ErrorCode getError() {
		return error;
	}
	public Object getData() {
		return data;
	}
}
