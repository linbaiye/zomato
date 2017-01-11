package org.nalby.zomato.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cuisines")
public class Cuisine {
	@Id @Column(name = "cuisine_id")
	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
