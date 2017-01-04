package org.nalby.zomato.dao;

import java.util.List;
import java.util.Map;

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

	public Map<String, Long> getCategoryCountMap() {
		return null;
	}

	public List<Map<String, String>> getUrlMapList(Map<String, Long> offsets) {
		return null;
	}

	public List<Map<String, Object>> getRestaurantStatistic() {
		return null;
	}

	public Restaurant getRestaurant(int id) {
		return null;
	}

	public List<Restaurant> getSpecifiedNumberRestaurantByType(String type, int number, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<FeaturedCollection> getCollections(List<String> typeList) {
		Session session = sessionFactory.getCurrentSession();
		List<FeaturedCollection> list = null;
		if (typeList == null) {
			list = session.getNamedQuery("FeaturedCollection.findAll").list();
		} else {
			Query query = session.getNamedQuery("FeaturedCollection.findByTypes");
			list = query.setParameterList("types", typeList).list();
		}
		return list;
	}

}
