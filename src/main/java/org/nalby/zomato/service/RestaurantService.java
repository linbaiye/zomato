package org.nalby.zomato.service;

import java.util.List;
import java.util.Map;

import org.nalby.zomato.response.Response;
import org.nalby.zomato.response.ResponseWrapper;

public interface RestaurantService {
	public List<Map<String, String>> getRecommendationUrls();
	public List<Map<String, Object>> getRestaurantNumberOfPlaces();
	public ResponseWrapper getRestaurant(int id);
	public ResponseWrapper getOnePageRestaurantsByType(String type);
	public Response getAllCollections();
	public Response getMainPageCollections();
}
