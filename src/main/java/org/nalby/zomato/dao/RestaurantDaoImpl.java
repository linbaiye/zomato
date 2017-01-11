package org.nalby.zomato.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nalby.zomato.model.FeaturedCollection;
import org.nalby.zomato.model.FeaturedRestaurants;
import org.nalby.zomato.model.Restaurant;
import org.nalby.zomato.model.RestaurantStats;
import org.nalby.zomato.util.QueryName;
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
			list = session.getNamedQuery(QueryName.FIND_ALL_FEATUREDS).list();
			LOGGER.info("Listed all collections.");
		} else {
			Query query = session.getNamedQuery(QueryName.FIND_FEATUREDS_IN);
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

	
	public FeaturedRestaurants getRestaurantsByCollection(int collection) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery(QueryName.FIND_FEATURED_RESTAURANTS_BY_ID);
		query.setParameter("id", collection);
		return (FeaturedRestaurants) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<RestaurantStats> getRestauratStats() {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(QueryName.RESTAURANT_STATS_SQL);
		query.addEntity(RestaurantStats.class);
		return query.list();
	}

}
