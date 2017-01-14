package org.nalby.zomato.service;

import org.nalby.zomato.response.Response;

public interface SearchService {
	
	public Response getSearchCriteria(int categoryId, int page);

}
