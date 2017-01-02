package org.nalby.zomato.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.nalby.zomato.dao.RestaurantDao;
import org.nalby.zomato.exception.BadParameterException;
import org.nalby.zomato.model.Restaurant;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	private RestaurantDao restaurantDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	public List<Map<String, String>> getRecommendationUrls() {
		Map<String, Long> map = restaurantDao.getCategoryCountMap();
		Random randomGenerator = new Random();
		for (String key: map.keySet()) {
			int offset = (map.get(key)).intValue();
			map.put(key, (long)randomGenerator.nextInt(offset));
			LOGGER.debug("key:{}, value:{}.", key, map.get(key));
		}
		List<Map<String, String>> recommendation = restaurantDao.getUrlMapList(map);
		for (Map<String, String> tmp: recommendation) {
			for (String key: tmp.keySet()) {
				LOGGER.debug("key:{}, value:{}.", key, tmp.get(key));
			}
		}
		return recommendation;
	}

	public List<Map<String, Object>> getRestaurantNumberOfPlaces() {
		return restaurantDao.getRestaurantStatistic();
	}

	public ResponseWrapper getRestaurant(int id) {
		Restaurant restaurant = restaurantDao.getRestaurant(id);
		if (restaurant == null){
			return new ResponseWrapper(ErrorCode.ENOREST);
		}
		return new ResponseWrapper(ErrorCode.EOK, restaurant);
	}

	public ResponseWrapper getOnePageRestaurantsByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, String>> getCollectins(String type) {
		List<String> array = null;
		if ("mainpage".equals(type)) {
			array = Arrays.asList(new String[]{"Trending this week", "Newly opened", "Function venues", "Lunar New Year"});
		} else if (!"all".equals(type)){
			throw new BadParameterException("Unrecognizable parameter " +type);
		}
		return restaurantDao.getCollections(array);
	}

}
