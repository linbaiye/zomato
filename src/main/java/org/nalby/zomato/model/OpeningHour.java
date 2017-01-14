package org.nalby.zomato.model;

import java.io.Serializable;
import java.sql.Date;

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
		private Date start;
		private Date end;
	}
	@Id @Column(name = "restaurant_id")
	private Integer restaurantId; 

	@Id @Column(name ="weekday")
	private String day;

	@Id
	private Date start;

	@Id
	private Date end;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDay() {
		return day;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
}
