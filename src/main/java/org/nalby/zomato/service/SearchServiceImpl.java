package org.nalby.zomato.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nalby.zomato.dao.RestaurantDao;
import org.nalby.zomato.dao.SearchDao;
import org.nalby.zomato.entity.CategoriedRestaurant;
import org.nalby.zomato.exception.BadParameterException;
import org.nalby.zomato.exception.InternalErrorExpection;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private RestaurantDao restaurantDao;
	
	@Autowired
	private SearchDao searchDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Transactional
	public Response getSearchComponents() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("categoryList", restaurantDao.getCategoryStats());
		result.put("cuisineList", restaurantDao.getCuisineStats());
		result.put("placeList", restaurantDao.getPlaceStats());
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
		Set<CategoriedRestaurant> set = restaurantDao.getCategoriedRestaurants(restauranIds);	
		if (set.isEmpty()) {
			return new Response(ErrorCode.ENOREST);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("currentPage", page);
		result.put("restaurantList", set);
		BigInteger total = restaurantDao.getRestaurantCountInCategory(categoryId);
		result.put("total", total);
		return new Response(ErrorCode.EOK, result);
	}
	
	public String compoundSearch(String type, Map<String, Object> searchBody) {
		Integer from = (Integer) searchBody.get("from");
		Integer size = (Integer) searchBody.get("size");
		if (size > 10 || from < 0) {
			throw new BadParameterException("Unexpected range, from:" + from + " size " + size);
		}
		try {
			String tmp = mapper.writeValueAsString(searchBody);
			return searchDao.proxyToES(type,tmp);
		} catch (JsonProcessingException e) {
			LOGGER.error("Got exception:", e);
			throw new InternalErrorExpection("Failed to search documents.");
		}
	}


	@Transactional
	public Response test() {
		/* Cafe */
		List<Integer> ids = restaurantDao.getRestaurantIdsByKeywordId(1, 6);
		return new Response(ErrorCode.EOK, restaurantDao.getRecommandRestaurants(ids));
	}

}
