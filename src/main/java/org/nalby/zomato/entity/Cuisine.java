package org.nalby.zomato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;

@Entity
@Table(name = "cuisines")
@NamedQuery(name = QueryName.FIND_CUISINES, query = "SELECT c FROM Cuisine c")
public class Cuisine {
	@Id @Column(name = "cuisine_id")
	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
