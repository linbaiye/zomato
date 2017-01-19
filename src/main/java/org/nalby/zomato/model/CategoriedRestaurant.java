package org.nalby.zomato.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.nalby.zomato.util.QueryName;


@Entity
@Table(name = "restaurants")
@NamedQueries(
@NamedQuery(name = QueryName.FIND_CATEGORIED_RESTAURANTS_IN, query = "SELECT cr FROM CategoriedRestaurant cr WHERE cr.id IN :ids")
)
public class CategoriedRestaurant extends BasicRestraurant {
	
	
	public List<OpeningHour> getOpeningHours() {
		return openingHours;
	}
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurant_id")
	private List<OpeningHour> openingHours = new LinkedList<OpeningHour>();


	@Column(name = "thumb_img_url")
	private String thumbImageUrl;
	
	@Column(name = "approx_price")
	private String approximatePrice;
	
	@Column(name = "phone")
	private String phone;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurant_featured_types", joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "featured_type_id"))
	private List<FeatureType> featureList;
	
	@Transient
	private int totalPageNumber;

	public void setTotalPageNumber(int page) {
		totalPageNumber = page;
	}
	
	public List<FeatureType> getFeatureList() {
		return featureList;
	}

	public String getThumbImageUrl() {
		return thumbImageUrl;
	}

	public String getApproximatePrice() {
		return approximatePrice;
	}

	public String getPhone() {
		return phone;
	}
	
	
	
}
