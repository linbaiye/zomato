package org.nalby.zomato.entity;


import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@MappedSuperclass
public abstract class AbstractRestaurant {
	@Id @Column(name = "restaurant_id")
	private Integer id;
	private String name;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Address address;
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

}
