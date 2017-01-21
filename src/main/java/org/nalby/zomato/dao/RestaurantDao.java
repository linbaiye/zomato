package org.nalby.zomato.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.nalby.zomato.entity.CategoriedRestaurant;
import org.nalby.zomato.entity.Category;
import org.nalby.zomato.entity.Cuisine;
import org.nalby.zomato.entity.FeaturedCollection;
import org.nalby.zomato.entity.FeaturedRestaurant;
import org.nalby.zomato.entity.FeaturedRestaurants;
import org.nalby.zomato.entity.RecommandRestaurant;
import org.nalby.zomato.entity.Restaurant;
import org.nalby.zomato.entity.RestaurantStats;
import org.nalby.zomato.stats.CategoryStats;
import org.nalby.zomato.stats.CuisineStats;
import org.nalby.zomato.stats.PlaceStats;

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
	
	public Set<CategoriedRestaurant> getCategoriedRestaurants(List<Integer> ids);
	
	public BigInteger getRestaurantCountInCategory(int categoryId);

	public List<CategoryStats> getCategoryStats();

	public List<CuisineStats> getCuisineStats();

	public List<PlaceStats> getPlaceStats();
	
	public List<Integer> getRestaurantIdsByKeywordId(int kid, int number);

	public Set<RecommandRestaurant> getRecommandRestaurants(List<Integer> ids);
	
	public Object test();
}
