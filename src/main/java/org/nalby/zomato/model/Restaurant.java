package org.nalby.zomato.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
@NamedQueries({
	@NamedQuery(name = "Restaurant.findById", query = "SELECT r FROM Restaurant r WHERE r.id = :id"),
	@NamedQuery(name = "Restaurant.findByCategory", query = "SELECT r FROM Restaurant r LEFT JOIN r.categoryList c WHERE c.id = :categoryId")
})
public class Restaurant {
	@Id @Column(name = "restaurant_id")
	private Integer id;
	
	private String name;
	
	private String phone;

	@Column(name = "img_url")
	private String imageUrl;
	
	@Column(name = "known_for")
	private String knownFor;
	
	@Column(name = "approx_price")
	private String approxPrice;
	
	@Column(name = "thumb_img_url")
	private String thumbImageUrl;
	
	
	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	private Address address;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurant_categories", joinColumns = @JoinColumn(name = "restaurant_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categoryList = new LinkedList<Category>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "cuisine_id"))
	private List<Cuisine> cuisineList = new LinkedList<Cuisine>();
	

	public List<Cuisine> getCuisineList() {
		return cuisineList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public Address getAddress() {
		return address;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getKnownFor() {
		return knownFor;
	}
	public String getApproxPrice() {
		return approxPrice;
	}
	public String getThumbImageUrl() {
		return thumbImageUrl;
	}
}
