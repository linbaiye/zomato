package org.nalby.zomato.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractFeature {
	@Id @Column(name="featured_type_id")
	private Integer featuredId;
	
	@Column(name="featured_type")
	private String type;

	public Integer getFeaturedId() {
		return featuredId;
	}

	public String getType() {
		return type;
	}

}
