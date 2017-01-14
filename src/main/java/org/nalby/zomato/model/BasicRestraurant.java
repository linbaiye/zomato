package org.nalby.zomato.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class BasicRestraurant extends AbstractRestaurant {
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
	private List<BasicReview> reviewList = new LinkedList<BasicReview>();
	
	@Transient
	private Double rate;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"), 
		inverseJoinColumns = @JoinColumn(name = "cuisine_id"))
	private List<Cuisine> cuisineList = new LinkedList<Cuisine>();

	public List<Cuisine> getCuisineList() {
		return cuisineList;
	}

	public Double getRate() {
		if (reviewList.isEmpty()) {
			return rate;
		}
		rate = new Double(0);
		for (BasicReview review: reviewList) {
			rate += review.getRate();
		}
		return (rate / reviewList.size());
	}

}
