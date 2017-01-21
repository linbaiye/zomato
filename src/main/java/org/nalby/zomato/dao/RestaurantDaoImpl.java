package org.nalby.zomato.dao;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nalby.zomato.entity.CategoriedRestaurant;
import org.nalby.zomato.entity.Category;
import org.nalby.zomato.entity.Cuisine;
import org.nalby.zomato.entity.FeaturedCollection;
import org.nalby.zomato.entity.FeaturedRestaurant;
import org.nalby.zomato.entity.FeaturedRestaurants;
import org.nalby.zomato.entity.RecommandRestaurant;
import org.nalby.zomato.entity.Restaurant;
import org.nalby.zomato.entity.RestaurantStats;
import org.nalby.zomato.stats.CategoryStats;
import org.nalby.zomato.stats.CuisineStats;
import org.nalby.zomato.stats.PlaceStats;
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

	
	@SuppressWarnings("unchecked")
	public FeaturedRestaurants getRestaurantsByFeatureId(int featureId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery(QueryName.GET_FEATURED_DETAILS);
		query.setParameter("id", featureId);
		FeaturedRestaurants featuredRestaurants = (FeaturedRestaurants) query.uniqueResult();
		query = session.getNamedQuery(QueryName.FIND_FEATURED_RESTAURANTS);
		query.setParameterList("ids", featuredRestaurants.getRestaurantIds());
		Set<FeaturedRestaurant> set = new HashSet<FeaturedRestaurant>(query.list());
		featuredRestaurants.setRestaurantSet(set);
		return featuredRestaurants;
	}
	
	@SuppressWarnings("unchecked")
	public List<RestaurantStats> getRestauratStats() {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(QueryName.RESTAURANT_STATS_SQL);
		query.addEntity(RestaurantStats.class);
		return query.list();
	}

	public List<FeaturedRestaurant> getFeaturedRestaurantsByFeatureId(int id) {
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private List getList(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery(name);
		return  query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategories() {
		return getList(QueryName.FIND_CATEGORIES);
	}

	@SuppressWarnings("unchecked")
	public List<Cuisine> getCuisines() {
		return getList(QueryName.FIND_CUISINES);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRestaurantIdsByCategoryId(int categoryId, int limit, int offset) {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT restaurant_id FROM restaurant_categories WHERE "
				+ " category_id = :id LIMIT :limit OFFSET :offset");
		query.setParameter("id", categoryId)
			.setParameter("limit", limit)
			.setParameter("offset", offset);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Set<CategoriedRestaurant> getCategoriedRestaurants(List<Integer> ids) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery(QueryName.FIND_CATEGORIED_RESTAURANTS_IN);
		query.setParameterList("ids", ids);
		return new HashSet<CategoriedRestaurant>(query.list());
	}

	public BigInteger getRestaurantCountInCategory(int categoryId) {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("SELECT COUNT(*) FROM restaurant_categories WHERE category_id = :id");
		query.setParameter("id", categoryId);
		return (BigInteger) query.uniqueResult();
	}

	public Object test() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery(QueryName.GET_RECOMMANDED_CAFTE);
		query.setParameter("id", 63);
		return query.list();
	}
	

	@SuppressWarnings("unchecked")
	public List<CategoryStats> getCategoryStats() {
		return getList(QueryName.GET_CATEGORY_STATS);
	}

	@SuppressWarnings("unchecked")
	public List<CuisineStats> getCuisineStats() {
		return getList(QueryName.GET_CUISINE_STATS);
	}

	@SuppressWarnings("unchecked")
	public List<PlaceStats> getPlaceStats() {
		return getList(QueryName.GET_PLACE_STATS);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRestaurantIdsByKeywordId(int kid, int number) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("SELECT restaurant_id FROM restaurant_keywords WHERE keyword_id = :id LIMIT :limit");
		query.setParameter("id", kid);
		query.setParameter("limit", number);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Set<RecommandRestaurant> getRecommandRestaurants(List<Integer> ids) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery(QueryName.GET_RECOMMANDED_CAFTE);
		query.setParameterList("ids", ids);
		return new HashSet<RecommandRestaurant>(query.list());
	}
	
}
