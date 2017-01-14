package org.nalby.zomato.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/* Used to list featured restaurants, some fields of a restaurant are omitted. */
@Entity
@Table(name = "restaurants")
public class FeaturedRestaurant extends BasicRestraurant {
	@Column(name = "img_url")
	private String imageUrl;
	


	public String getImageUrl() {
		return imageUrl;
	}

}
