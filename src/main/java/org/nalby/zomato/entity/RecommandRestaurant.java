package org.nalby.zomato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.NamedQuery;

import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;

@Entity
@Table(name = "restaurants")
@NamedQuery(name = QueryName.GET_RECOMMANDED_CAFTE, query = "SELECT r FROM RecommandRestaurant r "
		+ "INNER JOIN FETCH r.address "
		+ "INNER JOIN FETCH r.reviewSet "
		+ "INNER JOIN FETCH r.cuisineSet "
		+ " WHERE r.id IN :ids")

public class RecommandRestaurant  extends BasicRestraurant {

	@Column(name = "thumb_img_url")
	private String thumbImageUrl;

	public String getThumbImageUrl() {
		return thumbImageUrl;
	}

}
