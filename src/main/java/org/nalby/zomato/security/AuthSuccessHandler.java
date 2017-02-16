package org.nalby.zomato.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("authSuccessHandler")
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements LogoutSuccessHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final static Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	
	private void writeResponse(ErrorCode code, HttpServletResponse response, int authCookieMaxAge) throws JsonGenerationException, JsonMappingException, IOException {
		Response response2 = new Response(ErrorCode.EOK);
		response.setStatus(HttpServletResponse.SC_OK);
		Cookie cookie = new Cookie("isAuthed", "true");
		cookie.setMaxAge(authCookieMaxAge);
		response.addCookie(cookie);
		PrintWriter writer = response.getWriter();
		objectMapper.writeValue(writer, response2);
		writer.flush();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		logger.info("{} logged in.", authentication.getName());
		writeResponse(ErrorCode.EOK, response, (2 * 24 + 23) * 3600);
	}
	
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws JsonGenerationException, JsonMappingException, IOException, ServletException {
		if (authentication != null) {
			logger.info("{} loged out.", authentication.getName());
		}
		writeResponse(ErrorCode.EOK, response, 0);
	}
}

