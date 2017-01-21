package org.nalby.zomato.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.nalby.zomato.util.QueryName;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name = QueryName.GET_FEATURED_DETAILS,
query = "SELECT fr FROM FeaturedRestaurants fr WHERE fr.featuredId = :id")
@Table(name = "featured_types")
public class FeaturedRestaurants extends BasicFeature {
	@Transient
	private Set<FeaturedRestaurant> restaurantSet;
	

	public Set<FeaturedRestaurant> getRestaurantSet() {
		return restaurantSet;
	}

	public void setRestaurantSet(Set<FeaturedRestaurant> restaurantSet) {
		this.restaurantSet = restaurantSet;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "restaurant_featured_types", joinColumns = @JoinColumn(name = "featured_type_id"))
	@Column(name = "restaurant_id")
	private List<Integer> restaurantIds = new LinkedList<Integer>();

	@JsonIgnore
	public List<Integer> getRestaurantIds() {
		return restaurantIds;
	}

}
