package org.nalby.zomato.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
