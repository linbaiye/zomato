package org.nalby.zomato.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nalby.zomato.dao.RestaurantDao;
import org.nalby.zomato.model.CategoriedRestaurant;
import org.nalby.zomato.model.Category;
import org.nalby.zomato.model.Cuisine;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private RestaurantDao restaurantDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

	
	@Transactional
	public Response getSearchCriteria(int categoryId, int page) {
		List<Category> categorieList = restaurantDao.getCategories();
		List<Cuisine> cuisineList = restaurantDao.getCuisines();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("categoryList", categorieList);
		result.put("cuisineList", cuisineList);
		List<Integer> restauranIds = restaurantDao.getRestaurantIdsByCategoryId(categoryId, RestaurantServiceImpl.RESTAURANT_NUMBER_PER_PAGE
				, page * RestaurantServiceImpl.RESTAURANT_NUMBER_PER_PAGE);
		if (restauranIds.isEmpty()) {
			LOGGER.info("No restaurant id selected.");
		}
		List<CategoriedRestaurant> restaurants = restaurantDao.getCategoriedRestaurants(restauranIds);
		if (restaurants.isEmpty()) {
			LOGGER.info("No restaurant selected.");
		}
		result.put("restaurantList", restaurants);
		return new Response(ErrorCode.EOK, result);
	}

}
