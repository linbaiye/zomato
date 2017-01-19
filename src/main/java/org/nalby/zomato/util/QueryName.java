package org.nalby.zomato.util;


public final class QueryName {
	public static final String FIND_FEATURED_RESTAURANTS_BY_ID = "Restaurant.listFeaturedById";
	public static final String FIND_ALL_FEATUREDS = "Featured.findAll";
	public static final String FIND_FEATUREDS_IN = "Featured.findIn";
	public static final String RESTAURANT_STATS_SQL = "select district, count(*) as count from addresses a left join restaurants r "
			+ "on r.address_id = a.address_id group by district order by count desc limit 40";
	public static final String FIND_CATEGORIES = "Category.findAll";
	public static final String FIND_CUISINES = "Cuisine.findAll";
	public static final String FIND_BASIC_CATEGORIES = "BasicCategory.findAll";
	public static final String FIND_CATEGORIED_RESTAURANTS_IN = "CategoriedRestaurant.findInIds";
	public static final String GET_TOTAL_RESTAURANT_IN_CATEGORY = "CategoriedRestaurant.countAll";
}
