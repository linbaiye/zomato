package org.nalby.zomato.dao;

import java.util.List;
import java.util.Map;

import org.nalby.zomato.model.Restaurant;

public interface RestaurantDao {
	public Map<String, Long> getCategoryCountMap();
	public List<Map<String, String>> getUrlMapList(Map<String, Long> offsets);
	public List<Map<String, Object>> getRestaurantStatistic();
	public Restaurant getRestaurant(int id);
	public List<Restaurant> getSpecifiedNumberRestaurantByType(String type, int number, int offset);
	public List<Map<String, String>> getCollections(List<String> typeList);
	
}
