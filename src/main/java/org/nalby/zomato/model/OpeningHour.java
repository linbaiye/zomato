package org.nalby.zomato.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "restaurant_opening_times")
@IdClass(OpeningHour.PrimaryKey.class)
public class OpeningHour implements Serializable {
	private static final long serialVersionUID = 1L;
	static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;
		private Integer restaurantId; 
		private String day;
		private Time start;
		private Time end;
	}
	@Id @Column(name = "restaurant_id")
	private Integer restaurantId; 

	@Id @Column(name ="weekday")
	private String day;

	@Id
	private Time start;

	@Id
	private Time end;

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDay() {
		return day;
	}

	public Time getStart() {
		return start;
	}

	public Time getEnd() {
		return end;
	}
	
}
