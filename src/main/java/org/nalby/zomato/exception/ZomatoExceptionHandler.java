package org.nalby.zomato.exception;

import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ZomatoExceptionHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ZomatoExceptionHandler.class);
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Response badParameterExcpetionHandler(BadParameterException exception) {
		LOGGER.error("Got an exception:", exception);
		return new Response(ErrorCode.EBADPARAM, exception.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response defaultExceptionHandler(Exception exception) {
		LOGGER.error("Got an exception:", exception);
		return new Response(ErrorCode.EINTERNAL, exception.getMessage());
	}

}
