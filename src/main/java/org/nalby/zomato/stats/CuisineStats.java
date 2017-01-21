package org.nalby.zomato.stats;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;

import org.nalby.zomato.util.QueryName;

@NamedNativeQuery(name = QueryName.GET_CUISINE_STATS, query = "SELECT cuisines.cuisine_id as id, cuisines.name, count(*) as count FROM restaurant_cuisines INNER"
		+ " JOIN cuisines ON restaurant_cuisines.cuisine_id = cuisines.cuisine_id GROUP BY restaurant_cuisines.cuisine_id"
		+ " ORDER BY count DESC", resultClass = CuisineStats.class)
@Entity
public class CuisineStats extends AbstractStats {

}
