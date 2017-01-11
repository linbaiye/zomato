package org.nalby.zomato.controller;

import org.nalby.zomato.exception.BadParameterException;
import org.nalby.zomato.response.Response;
import org.nalby.zomato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZomatoController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@RequestMapping(value = "/api/v1/restaurant/{id}", method = RequestMethod.GET)
	public Response getRestaurant(@PathVariable("id") int id) {
		return restaurantService.getRestaurant(id);
	}
	
	@RequestMapping(value = "/api/v1/restaurant/category/{categoryId}/{page}", method = RequestMethod.GET)
	public Response getRestaurants(@PathVariable("categoryId") int categoryId, @PathVariable("page")int page) {
		return restaurantService.getRestauransByCategory(categoryId, page);
	}

	@RequestMapping(value = "/api/v1/restaurant/collection/{collectionId}", method = RequestMethod.GET)
	public Response getRestaurantsByCollection(@PathVariable int collectionId) {
		return restaurantService.getRestauransByCollection(collectionId);
	}

	@RequestMapping(value = "/api/v1/restaurant/stats", method = RequestMethod.GET)
	public Response getRestaurantStats() {
		return restaurantService.getRestauransStats();
	}
	
	@RequestMapping(value = "/api/v1/collection/{type}", method = RequestMethod.GET)
	public Response getFeatruedCollections(@PathVariable("type") String type) {
		if ("main_page".equals(type)) {
			return restaurantService.getMainPageCollections();
		} else if ("all".equals(type)) {
			return restaurantService.getAllCollections();
		}
		throw new BadParameterException("Unknown collection type:" + type);
	}
	
}