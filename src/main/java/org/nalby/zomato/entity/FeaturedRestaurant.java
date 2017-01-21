package org.nalby.zomato.entity;

import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

/* Used to list featured restaurants, some fields of a restaurant are omitted. */
@Entity
@Table(name = "restaurants")
@NamedQuery(name = QueryName.FIND_FEATURED_RESTAURANTS, query = "SELECT r FROM FeaturedRestaurant r "
		+ "INNER JOIN FETCH r.address "
		+ "INNER JOIN FETCH r.reviewSet "
		+ "INNER JOIN FETCH r.cuisineSet WHERE r.id IN :ids AND r.imageUrl != ''")
public class FeaturedRestaurant extends BasicRestraurant {
	@Column(name = "img_url")
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

}
