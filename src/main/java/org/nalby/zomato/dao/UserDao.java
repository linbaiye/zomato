package org.nalby.zomato.dao;

import java.util.List;

import org.nalby.zomato.entity.User;

public interface UserDao {
	
	public List<User> getUsersByIds(List<Long> ids);
	
	public User loadUserByName(String name);

}
