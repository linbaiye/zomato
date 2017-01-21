package org.nalby.zomato.stats;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class AbstractStats {
	
	@Id
	private Long id;

	private String name;
	
	private Long count;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getCount() {
		return count;
	}

}
