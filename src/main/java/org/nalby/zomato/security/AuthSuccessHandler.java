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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("authSuccessHandler")
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final static Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		Response response2 = new Response(ErrorCode.EOK);
		logger.info("{} logged in.", authentication.getName());
		PrintWriter writer = response.getWriter();
		objectMapper.writeValue(writer, response2);
		writer.flush();
	}
}

