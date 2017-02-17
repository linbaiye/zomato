package org.nalby.zomato.dao;


public interface SearchDao {
	
	public String proxyToES(String type, String body);
	
	public String getRestaurant(Integer id);

}
