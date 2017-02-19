package org.nalby.zomato.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nalby.zomato.entity.ReviewToSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void saveReview(ReviewToSave reviewToSave) {
		Session session = sessionFactory.getCurrentSession();
		session.save(reviewToSave);
	}
	
}
