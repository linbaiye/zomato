package org.nalby.zomato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;

@Entity
@Table(name = "users")
@NamedQuery(name = QueryName.GET_USERS_BY_IDS, query = "SELECT u FROM User u WHERE id IN :ids")
public class User {

	@Id @Column(name = "user_id")
	private Long id;
	
	@Column(name = "user_icon_url")
	private String iconUrl;
	
	@Column(name = "user_tag")
	private String tag;
	
	@Column(name = "user_name")
	private String name;

	public Long getId() {
		return id;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getTag() {
		return tag;
	}

	public String getName() {
		return name;
	}
}
