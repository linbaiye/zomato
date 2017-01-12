package org.nalby.zomato.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasicReview {
	@Id @Column(name = "review_id")
	private Integer id;
	
	private Double rate;

	public Integer getId() {
		return id;
	}

	public Double getRate() {
		return rate;
	}
}
