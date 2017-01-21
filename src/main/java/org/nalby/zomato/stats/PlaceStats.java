package org.nalby.zomato.stats;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

import org.nalby.zomato.util.QueryName;

@NamedNativeQuery(name = QueryName.GET_PLACE_STATS, resultClass = PlaceStats.class, 
	query = "SELECT district, count(*) AS count FROM addresses GROUP BY district ORDER BY count DESC")
@Entity
public class PlaceStats {

	@Id
	private String district;
	
	private Long count;

	public String getDistrict() {
		return district;
	}

	public Long getCount() {
		return count;
	}
}
