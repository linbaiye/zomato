package org.nalby.zomato.controller;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.nalby.zomato.entity.ReviewToSave;
import org.nalby.zomato.exception.BadParameterException;
import org.nalby.zomato.form.PublishReviewForm;
import org.nalby.zomato.response.ErrorCode;
import org.nalby.zomato.response.Response;
import org.nalby.zomato.service.RestaurantService;
import org.nalby.zomato.service.ReviewService;
import org.nalby.zomato.service.SearchService;
import org.nalby.zomato.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ZomatoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ZomatoController.class);
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private SearchService searchService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;
	
	@SuppressWarnings("unused")
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@RequestMapping(value = "/api/v1/restaurant/{id}", method = RequestMethod.GET)
	public String getRestaurant(@PathVariable("id") int id) {
		return restaurantService.getRestaurant(id);
	}
	
	@RequestMapping(value = "/api/v1/restaurant/category/{categoryId}/{page}", method = RequestMethod.GET)
	public Response getRestaurants(@PathVariable("categoryId") int categoryId, @PathVariable("page")int page) {
		return restaurantService.getRestauransByCategory(categoryId, page);
	}

	@RequestMapping(value = "/api/v1/restaurant/feature/{feature}", method = RequestMethod.GET)
	public Response getRestaurantsByFeature(@PathVariable("feature") Integer featureId) {
		return restaurantService.getRestauransByFeatureId(featureId);
	}

	@RequestMapping(value = "/api/v1/restaurant/stats", method = RequestMethod.GET)
	public Response getRestaurantStats() {
		return restaurantService.getRestauransStats();
	}
	
	@RequestMapping(value = "/api/v1/feature/{type}", method = RequestMethod.GET)
	public Response getFeatures(@PathVariable("type") String type) {
		if ("main_page".equals(type)) {
			return restaurantService.getMainPageFeaturedCollections();
		} else if ("all".equals(type)) {
			return restaurantService.getAllFeaturedCollections();
		}
		throw new BadParameterException("Unknown collection type:" + type);
	}

	@RequestMapping(value = "/api/v1/category", method = RequestMethod.GET)
	public Response getCategories() {
		return restaurantService.getCategories();
	}
	
	@RequestMapping(value = "/api/v1/search/components")
	public Response getSearchComponents() {
		logger.info("/api/v1/search/components is requested.");
		return searchService.getSearchComponents();
	}
	
	@RequestMapping(value = "/api/v1/search/recommend")
	public Response getSearchRecommend() {
		logger.info("/api/v1/search/recommend is requested.");
		return restaurantService.getRecommandedRestaurants();
	}

	@RequestMapping(value = "/api/v1/search/category/{categoryId}/{page}", method = RequestMethod.GET)
	public Response getSearchCriteria(@PathVariable("categoryId") int categoryId, @PathVariable("page") int page) {
		logger.info("/api/v1/search/category/ is requested with [{}, {}].", categoryId, page);
		return searchService.getCategoriedRestaurants(categoryId, page);
	}
	
	@RequestMapping(value = "/api/v1/search/compound/{type}", method = RequestMethod.POST)
	public String compoundSearch(@PathVariable("type") @NotNull String type, @RequestBody @NotNull Map<String, Object> request) {
		return searchService.compoundSearch(type, request);
	}
	
	@RequestMapping(value = "/api/v1/user/list", method = RequestMethod.POST)
	public Response getUsers(@RequestBody @NotNull List<Long> ids) {
		logger.info("trying to list users.");
		return userService.getUsersByIds(ids);
	}
	
	@RequestMapping(value = "/api/v1/review/{restaurantId}", method = RequestMethod.POST)
	public Response publisReview(@PathVariable("restaurantId") Integer restaurantId, @RequestBody @Valid PublishReviewForm form) {
		logger.info("Got review from {}.", userService.getThisUserId());
		Long userid = userService.getThisUserId();
		if (userid == null) {
			throw new AccessDeniedException("Not authed.");
		}
		ReviewToSave reviewToSave = new ReviewToSave(form, userid, restaurantId);
		reviewService.saveReview(reviewToSave);
		logger.info("new review is saved at {}, id:{}.", reviewToSave.getReviewTime().toString(), reviewToSave.getId());
		return searchService.indexReview(reviewToSave);
	}
	
	@RequestMapping(value = "/api/v1/test", method = RequestMethod.GET)
	public Response test() {
		return searchService.test();
	}
}