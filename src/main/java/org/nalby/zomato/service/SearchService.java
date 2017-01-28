package org.nalby.zomato.service;

import java.util.Map;

import org.nalby.zomato.response.Response;

public interface SearchService {
	
	public Response getCategoriedRestaurants(int categoryId, int page);
	
	public Response getSearchComponents();

	public String compoundSearch(Map<String, Object> searchBody);
	
	public Response test();

}
