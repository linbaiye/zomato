package org.nalby.zomato.util;


public class QueryName {
	public static final String FIND_FEATURED_RESTAURANTS_BY_ID = "Restaurant.listFeaturedById";
	public static final String FIND_ALL_FEATUREDS = "Featured.findAll";
	public static final String FIND_FEATUREDS_IN = "Featured.findIn";
	public static final String RESTAURANT_STATS_SQL = "select district, count(*) as count from addresses a left join restaurants r "
			+ "on r.address_id = a.address_id group by district order by count desc limit 40";
}
