package org.nalby.zomato.service;

import javax.transaction.Transactional;

import org.nalby.zomato.dao.ReviewDao;
import org.nalby.zomato.entity.ReviewToSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;

	@Transactional
	public void saveReview(ReviewToSave reviewToSave) {
		reviewDao.saveReview(reviewToSave);
	}

}
