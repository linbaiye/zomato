package org.nalby.zomato.service;

import java.util.List;

import javax.transaction.Transactional;

import org.nalby.zomato.dao.UserDao;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserServiceAdapter implements UserService, UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceAdapter.class);

	@Transactional
	public Response getUsersByIds(List<Long> ids) {
		return new Response(ErrorCode.EOK, userDao.getUsersByIds(ids));
	}

	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.loadUserByName(username);
	}
}
