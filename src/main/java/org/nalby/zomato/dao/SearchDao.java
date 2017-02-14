package org.nalby.zomato.dao;


public interface SearchDao {
	
	public String proxyToES(String body);
	
	public String getRestaurant(Integer id);

}
