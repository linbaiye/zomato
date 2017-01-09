package org.nalby.zomato.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nalby.zomato.model.FeaturedCollection;
import org.nalby.zomato.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Restaurant getRestaurant(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("Restaurant.findById");
		return (Restaurant)query.setParameter("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<FeaturedCollection> getCollections(List<String> typeList) {
		Session session = sessionFactory.getCurrentSession();
		List<FeaturedCollection> list = null;
		if (typeList == null) {
			list = session.getNamedQuery("FeaturedCollection.findAll").list();
			LOGGER.info("Listed all collections.");
		} else {
			Query query = session.getNamedQuery("FeaturedCollection.findByTypes");
			list = query.setParameterList("types", typeList).list();
			LOGGER.info("Listed main page collections.");
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Restaurant> getListByCategory(int categoryId, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("Restaurant.findByCategory");
		query.setParameter("categoryId", categoryId);
		query.setMaxResults(limit).setFirstResult(offset);
		return query.list();
	}

}
