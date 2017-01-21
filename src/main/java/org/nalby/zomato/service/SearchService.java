package org.nalby.zomato.service;

import org.nalby.zomato.response.Response;

public interface SearchService {
	
	public Response getCategoriedRestaurants(int categoryId, int page);
	
	public Response getSearchComponents();
	
	public Response test();

}
