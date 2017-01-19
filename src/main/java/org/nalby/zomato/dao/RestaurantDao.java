package org.nalby.zomato.dao;

import java.math.BigInteger;
import java.util.List;

import org.nalby.zomato.model.CategoriedRestaurant;
import org.nalby.zomato.model.Category;
import org.nalby.zomato.model.Cuisine;
import org.nalby.zomato.model.FeaturedCollection;
import org.nalby.zomato.model.FeaturedRestaurant;
import org.nalby.zomato.model.FeaturedRestaurants;
import org.nalby.zomato.model.Restaurant;
import org.nalby.zomato.model.RestaurantStats;

public interface RestaurantDao {
	public Restaurant getRestaurant(int id);

	public List<FeaturedCollection> getCollections(List<String> typeList);

	public List<Restaurant> getListByCategory(int categoryId, int offset, int limit);
	
	public FeaturedRestaurants getRestaurantsByFeatureId(int featureId);

	public List<RestaurantStats> getRestauratStats();
	
	public List<FeaturedRestaurant> getFeaturedRestaurantsByFeatureId(int id);
	
	public List<Category> getCategories();
	
	public List<Cuisine> getCuisines();
	
	public List<Integer> getRestaurantIdsByCategoryId(int categoryId, int limit, int offset);
	
	public List<CategoriedRestaurant> getCategoriedRestaurants(List<Integer> ids);
	
	public BigInteger getRestaurantCountInCategory(int categoryId);
}
