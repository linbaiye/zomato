package org.nalby.zomato.entity;


import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;

@Entity
@NamedQueries({
	@NamedQuery(name = QueryName.FIND_ALL_FEATUREDS, query = "SELECT f FROM FeaturedCollection f"),
	@NamedQuery(name = QueryName.FIND_FEATUREDS_IN, query = "SELECT f FROM FeaturedCollection f WHERE f.type IN :types")
})
@Table(name = "featured_types")
public class FeaturedCollection extends BasicFeature{
}
