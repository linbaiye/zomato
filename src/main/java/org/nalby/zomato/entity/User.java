package org.nalby.zomato.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.nalby.zomato.util.QueryName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@NamedQuery(name = QueryName.GET_USERS_BY_IDS, query = "SELECT u FROM User u WHERE id IN :ids")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id @Column(name = "user_id")
	private Long id;
	
	@Column(name = "user_icon_url")
	private String iconUrl;
	
	@Column(name = "user_tag")
	private String tag;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_password")
	private String password;
	
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

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return name;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
