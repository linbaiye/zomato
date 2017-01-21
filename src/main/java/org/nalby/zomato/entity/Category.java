package org.nalby.zomato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;

@Entity
@Table(name = "categories")
@NamedQuery(name = QueryName.FIND_CATEGORIES, query = "SELECT c FROM Category c")
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

	@Column(name = "img_url")
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

}
