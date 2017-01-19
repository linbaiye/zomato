package org.nalby.zomato.service;

import java.math.BigInteger;
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
import org.springframework.web.servlet.RequestToViewNameTranslator;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private RestaurantDao restaurantDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

	
	@Transactional
	public Response getSearchComponents() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Category> categorieList = restaurantDao.getCategories();
		List<Cuisine> cuisineList = restaurantDao.getCuisines();
		result.put("categoryList", categorieList);
		result.put("cuisineList", cuisineList);
		return new Response(ErrorCode.EOK, result);
	}


	@Transactional
	public Response getCategoriedRestaurants(int categoryId, int page) {
		List<Integer> restauranIds = restaurantDao.getRestaurantIdsByCategoryId(categoryId, RestaurantServiceImpl.RESTAURANT_NUMBER_PER_PAGE
				, page * RestaurantServiceImpl.RESTAURANT_NUMBER_PER_PAGE);
		if (restauranIds.isEmpty()) {
			LOGGER.info("No restaurant id selected.");
			return new Response(ErrorCode.ENOREST);
		}
		List<CategoriedRestaurant> list = restaurantDao.getCategoriedRestaurants(restauranIds);	
		if (list.isEmpty()) {
			return new Response(ErrorCode.ENOREST);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("currentPage", page);
		result.put("restaurantList", list);
		BigInteger total = restaurantDao.getRestaurantCountInCategory(categoryId);
		result.put("total", total);
		return new Response(ErrorCode.EOK, result);
	}

}
