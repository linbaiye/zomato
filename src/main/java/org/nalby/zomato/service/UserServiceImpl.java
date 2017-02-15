package org.nalby.zomato.service;

import java.util.List;

import javax.transaction.Transactional;

import org.nalby.zomato.dao.UserDao;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Transactional
	public Response getUsersByIds(List<Long> ids) {
		return new Response(ErrorCode.EOK, userDao.getUsersByIds(ids));
	}

}
