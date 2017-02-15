package org.nalby.zomato.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("authFailureHandler")
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final static Logger logger = LoggerFactory.getLogger(AuthFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("exception:", exception);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Response response2 = new Response(ErrorCode.EBADCREDENTIAL);
		PrintWriter writer = response.getWriter();
		objectMapper.writeValue(writer, response2);
		writer.flush();
	}

}