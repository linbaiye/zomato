package org.nalby.zomato.dao;

import org.nalby.zomato.entity.ReviewToSave;

public interface SearchDao {
	
	public String proxyToES(String type, String body);
	
	public String getRestaurant(Integer id);
	
	public boolean indexReview(ReviewToSave reviewToSave);

	public Float getReviewAverageRate(Integer restaurantId);
	
	public boolean updateAverageRate(Integer restaruantId, Float newRate);

}
