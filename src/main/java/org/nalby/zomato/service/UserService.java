package org.nalby.zomato.service;

import java.util.List;

import org.nalby.zomato.response.Response;

public interface UserService {
	
	public Response getUsersByIds(List<Long> ids);
	
	public Long getThisUserId();

}
