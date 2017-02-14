package org.nalby.zomato.service;



import org.nalby.zomato.response.Response;

public interface RestaurantService {

	public String getRestaurant(int id);

	public Response getAllFeaturedCollections();

	public Response getMainPageFeaturedCollections();

	public Response getRestauransByCategory(int category, int page);

	public Response getRestauransByFeatureId(int featureId);
	
	public Response getRestauransStats();
	
	public Response getCategories();
	
	public Response getCategoriedRestaurants(int categoryId, int page);
	
	public Response getRecommandedRestaurants();
	
	
}
