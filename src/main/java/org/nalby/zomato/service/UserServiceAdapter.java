package org.nalby.zomato.service;

import java.util.List;

import javax.transaction.Transactional;

import org.nalby.zomato.dao.UserDao;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserServiceAdapter implements UserService, UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Transactional
	public Response getUsersByIds(List<Long> ids) {
		return new Response(ErrorCode.EOK, userDao.getUsersByIds(ids));
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
