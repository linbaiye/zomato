package org.nalby.zomato.service;

import java.util.Arrays;
import java.util.List;

import org.nalby.zomato.dao.RestaurantDao;
import org.nalby.zomato.model.FeaturedCollection;
import org.nalby.zomato.model.Restaurant;
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
	
	private static int RESTAURANT_NUMBER_PER_PAGE = 10;

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
	public Response getAllCollections() {
		return new Response(ErrorCode.EOK, restaurantDao.getCollections(null));
	}

	@Transactional
	public Response getMainPageCollections() {
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
	public Response getRestauransByCollection(int collection) {
		return new Response(ErrorCode.EOK, restaurantDao.getRestaurantsByCollection(collection));
	}

}
