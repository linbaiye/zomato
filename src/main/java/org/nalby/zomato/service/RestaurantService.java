package org.nalby.zomato.service;


import org.nalby.zomato.response.Response;

public interface RestaurantService {

	/* Get by id. */
	public Response getRestaurant(int id);
	public Response getAllFeaturedCollections();

	public Response getMainPageFeaturedCollections();

	public Response getRestauransByCategory(int category, int page);

	public Response getRestauransByCollection(int collection);
	
	public Response getRestauransStats();
	
}
