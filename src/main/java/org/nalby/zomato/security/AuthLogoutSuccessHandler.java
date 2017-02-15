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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("logoutHandler")
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final static Logger logger = LoggerFactory.getLogger(AuthLogoutSuccessHandler.class);

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication != null) {
			logger.info("{} loged out.", authentication.getName());
		}
		response.setStatus(HttpServletResponse.SC_OK);
		Response wrapper = new Response(ErrorCode.EOK);
		PrintWriter writer = response.getWriter();
		objectMapper.writeValue(writer, wrapper);
		writer.flush();
	}

}

