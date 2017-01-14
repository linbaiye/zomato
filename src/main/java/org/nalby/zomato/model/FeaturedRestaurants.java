package org.nalby.zomato.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.nalby.zomato.util.QueryName;

@Entity
@NamedQuery(name = QueryName.FIND_FEATURED_RESTAURANTS_BY_ID,
query = "SELECT fr FROM FeaturedRestaurants fr WHERE fr.featuredId = :id")
@Table(name = "featured_types")
public class FeaturedRestaurants extends BasicFeature {
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurant_featured_types", 
			joinColumns = @JoinColumn(name = "featured_type_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
	@Where(clause = "img_url != ''")
	private List<FeaturedRestaurant> restaurantList = new LinkedList<FeaturedRestaurant>();
	public List<FeaturedRestaurant> getRestaurantList() {
		return restaurantList;
	}
}
