package org.nalby.zomato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
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
