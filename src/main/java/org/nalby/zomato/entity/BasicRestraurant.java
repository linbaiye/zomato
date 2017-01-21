package org.nalby.zomato.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@MappedSuperclass
public abstract class BasicRestraurant extends AbstractRestaurant {

	@OneToMany
	@JoinColumn(name = "restaurant_id")
	private Set<BasicReview> reviewSet = new HashSet<BasicReview>();
	
	@Transient
	private Double rate;
	
	@OneToMany
	@JoinTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"), 
		inverseJoinColumns = @JoinColumn(name = "cuisine_id"))
	private Set<Cuisine> cuisineSet = new HashSet<Cuisine>();


	public Set<Cuisine> getCuisineSet() {
		return cuisineSet;
	}

	public Double getRate() {
		if (reviewSet.isEmpty()) {
			return rate;
		}
		rate = new Double(0);
		for (BasicReview review: reviewSet) {
			rate += review.getRate();
		}
		return (rate / reviewSet.size());
	}

}
