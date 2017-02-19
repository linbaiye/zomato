package org.nalby.zomato.service;

import org.nalby.zomato.entity.ReviewToSave;

public interface ReviewService {
	
	public void saveReview(ReviewToSave reviewToSave);

}
