package org.nalby.zomato.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
	
	@Id @Column(name = "address_id")
	private Integer id;

	@Column(name = "address")
	private String textAddress;
	
	private BigDecimal longitude;
	
	private BigDecimal latitude;

	public Integer getId() {
		return id;
	}

	public String getTextAddress() {
		return textAddress;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}
}
