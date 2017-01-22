package org.nalby.zomato.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.standard.RequestingUserName;
import javax.validation.constraints.Null;

import org.nalby.zomato.dao.RestaurantDao;
import org.nalby.zomato.entity.FeaturedCollection;
import org.nalby.zomato.entity.RecommandRestaurant;
import org.nalby.zomato.entity.Restaurant;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	private RestaurantDao restaurantDao;
	
	public final static int RESTAURANT_NUMBER_PER_PAGE = 10;

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	@Transactional
	public Response getRestaurant(int id) {
		Restaurant restaurant = restaurantDao.getRestaurant(id);
		if (restaurant == null){
			LOGGER.info("Failed to find restaurant with id:{}.", id);
			return new Response(ErrorCode.ENOREST);
		}
		return new Response(ErrorCode.EOK, restaurant);
	}

	@Transactional
	public Response getAllFeaturedCollections() {
		return new Response(ErrorCode.EOK, restaurantDao.getCollections(null));
	}

	@Transactional
	public Response getMainPageFeaturedCollections() {
		List<FeaturedCollection> list = restaurantDao.getCollections(
				Arrays.asList("Cocktail bars", "Newly opened", "Function venues", "Lunar New Year"));
		return new Response(ErrorCode.EOK, list);
	}

	@Transactional
	public Response getRestauransByCategory(int category, int page) {
		return new Response(ErrorCode.EOK, 
				restaurantDao.getListByCategory(category, page * RESTAURANT_NUMBER_PER_PAGE, RESTAURANT_NUMBER_PER_PAGE));
	}

	@Transactional
	public Response getRestauransByFeatureId(int featureId) {
		return new Response(ErrorCode.EOK, restaurantDao.getRestaurantsByFeatureId(featureId));
	}

	@Transactional
	public Response getRestauransStats() {
		return new Response(ErrorCode.EOK, restaurantDao.getRestauratStats());
	}

	@Transactional
	public Response getCategories() {
		return new Response(ErrorCode.EOK, restaurantDao.getCategories());
	}

	@Transactional
	public Response getCategoriedRestaurants(int categoryId, int page) {
		return new Response(ErrorCode.EOK,
				restaurantDao.getListByCategory(categoryId, page * RESTAURANT_NUMBER_PER_PAGE, RESTAURANT_NUMBER_PER_PAGE));
	}

	private static final Map<String, Integer> RECOMMAND_KEYWORD_MAP = new HashMap<String, Integer>();
	static {
		RECOMMAND_KEYWORD_MAP.put("cafe", 1);
		RECOMMAND_KEYWORD_MAP.put("dinner", 4);
		RECOMMAND_KEYWORD_MAP.put("take-away", 194);
	}
	@Transactional
	public Response getRecommandedRestaurants() {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (String key : RECOMMAND_KEYWORD_MAP.keySet()) {
			List<Integer> ids = restaurantDao.getRestaurantIdsByKeywordId(RECOMMAND_KEYWORD_MAP.get(key), 6);
			ret.put(key, ids == null || ids.isEmpty() ? null : restaurantDao.getRecommandRestaurants(ids));
		}
		return new Response(ErrorCode.EOK, ret);
	}

}
