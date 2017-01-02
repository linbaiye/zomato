package org.nalby.zomato.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.nalby.zomato.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

	@Autowired
	private SqlSession sqlSession;

	public Map<String, Long> getCategoryCountMap() {
		return sqlSession.selectOne("Restaurant.selectCategoryCountMap");
	}

	public List<Map<String, String>> getUrlMapList(Map<String, Long> offsets) {
		return sqlSession.selectList("Restaurant.selectRecommendUrls", offsets);
	}

	public List<Map<String, Object>> getRestaurantStatistic() {
		return sqlSession.selectList("Restaurant.selectStatistic");
	}

	public Restaurant getRestaurant(int id) {
		return sqlSession.selectOne("Restaurant.selectRestaurant", id);
	}

	public List<Restaurant> getSpecifiedNumberRestaurantByType(String type, int number, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, String>> getCollections(List<String> typeList) {
		// TODO Auto-generated method stub
		return null;
	}

}
