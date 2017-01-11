package org.nalby.zomato.service;


import org.nalby.zomato.response.Response;

public interface RestaurantService {

	/* Get by id. */
	public Response getRestaurant(int id);
	public Response getAllCollections();

	public Response getMainPageCollections();

	public Response getRestauransByCategory(int category, int page);

	public Response getRestauransByCollection(int collection);
	
}
