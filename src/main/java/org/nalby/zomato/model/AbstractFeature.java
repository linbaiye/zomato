package org.nalby.zomato.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractFeature {
	@Id @Column(name="featured_type_id")
	private Integer featuredId;
	
	@Column(name="featured_type")
	private String type;
	
	@Column(name="featured_desc")
	private String description;
	
	@Column(name="featured_img_url")
	private String imageUrl;
	
	@Column(name="featured_thumb_img_url")
	private String thumbUrl;
	
	public void setFeaturedId(Integer featuredId) {
		this.featuredId = featuredId;
	}

	public Integer getFeaturedId() {
		return featuredId;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}


	public void setType(String type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}
}
