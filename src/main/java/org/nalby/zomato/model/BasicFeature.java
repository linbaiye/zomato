package org.nalby.zomato.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasicFeature extends AbstractFeature {
	@Column(name="featured_desc")
	private String description;
	
	@Column(name="featured_img_url")
	private String imageUrl;
	
	@Column(name="featured_thumb_img_url")
	private String thumbUrl;
	
	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}
}
