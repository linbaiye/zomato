package org.nalby.zomato.entity;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.nalby.zomato.form.PublishReviewForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "reviews")
public class ReviewToSave {
	@Id @Column(name = "review_id")
	@GeneratedValue
	@JsonIgnore
	private Integer id;
		
	@Column(name = "rate")
	@JsonProperty
	private float rate;

	@Column(name = "user_id")
	@JsonProperty("user_id")
	private Long userId;
	
	@Column(name = "review_text")
	@JsonProperty("review_text")
	private String reviewText;
	
	@Column(name = "restaurant_id")
	@JsonIgnore
	private Integer restaurantId;

	@Column(name = "review_time")
	@JsonIgnore
	private Date reviewTime;
	
	@JsonProperty("review_time")
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return sdf.format(reviewTime);
	}
	
	public ReviewToSave(PublishReviewForm form, Long userid, Integer rid) {
		userId = userid;
		reviewText = form.getText();
		rate = form.getRate();
		restaurantId = rid;
		reviewTime = new Date();
	}
	
	public Date getReviewTime() {
		return reviewTime;
	}

	public Integer getId() {
		return id;
	}

	public float getRate() {
		return rate;
	}

	public Long getUserId() {
		return userId;
	}

	public String getReviewText() {
		return reviewText;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}
	
	public String toESIndexPayload() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
		}
		return "";
	}
	
}
