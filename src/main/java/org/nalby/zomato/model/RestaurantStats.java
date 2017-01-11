package org.nalby.zomato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RestaurantStats {
	
	@Id
	private String district;
	
	@Column(name = "count")
	private Integer number;

	public String getDistrict() {
		return district;
	}

	public Integer getNumber() {
		return number;
	}
}
