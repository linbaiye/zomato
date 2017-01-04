package org.nalby.zomato.controller;

import java.util.List;
import java.util.Map;

import org.nalby.zomato.exception.BadParameterException;
import org.nalby.zomato.response.Response;
import org.nalby.zomato.response.ResponseWrapper;
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

	@RequestMapping(value = "/api/v1/restaurant_statistic", method = RequestMethod.GET)
	public List<Map<String, Object>> restauranStatistics() {
		return restaurantService.getRestaurantNumberOfPlaces();
	}
	
	@RequestMapping(value = "/api/v1/restaurant/{id}", method = RequestMethod.GET)
	public ResponseWrapper getRestaurant(@PathVariable("id") int id) {
		return restaurantService.getRestaurant(id);
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