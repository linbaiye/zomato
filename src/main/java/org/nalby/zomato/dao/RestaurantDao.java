package org.nalby.zomato.dao;

import java.util.List;
import org.nalby.zomato.model.FeaturedCollection;
import org.nalby.zomato.model.FeaturedRestaurants;
import org.nalby.zomato.model.Restaurant;
import org.nalby.zomato.model.RestaurantStats;

public interface RestaurantDao {
	public Restaurant getRestaurant(int id);
	public List<FeaturedCollection> getCollections(List<String> typeList);
	public List<Restaurant> getListByCategory(int categoryId, int offset, int limit);
	public FeaturedRestaurants getRestaurantsByCollection(int collection);
	public List<RestaurantStats> getRestauratStats();
}
