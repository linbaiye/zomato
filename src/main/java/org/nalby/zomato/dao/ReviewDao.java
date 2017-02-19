package org.nalby.zomato.dao;

import org.nalby.zomato.entity.ReviewToSave;

public interface ReviewDao {
	
	public void saveReview(ReviewToSave reviewToSave);

}
