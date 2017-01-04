package org.nalby.zomato.response;

public class Response {
	private ErrorCode error;
	private Object data;
	
	public Response(ErrorCode errorCode) {
		error = errorCode;
	}
	
	public Response(ErrorCode errorCode, Object data) {
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
