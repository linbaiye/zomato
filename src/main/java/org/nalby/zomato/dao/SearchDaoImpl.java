package org.nalby.zomato.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.nalby.zomato.exception.InternalErrorExpection;
import org.nalby.zomato.form.SearchBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDaoImpl implements SearchDao {

	private HttpClient client = HttpClientBuilder.create().build();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchDaoImpl.class);
	
	private String readResponse(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		LOGGER.info("Got status :{}.", response.getStatusLine().getStatusCode());
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	public String proxyToES(String searchBody) {
		HttpPost post = new HttpPost("http://localhost:9200/zomato/restaurant/_search");
		try {
			LOGGER.info("POST body:{}.", searchBody);
			post.setEntity(new StringEntity(searchBody));
			post.setHeader("Content-Type", "Application/json");
			HttpResponse response = client.execute(post);
			return readResponse(response);
		} catch (Exception e) {
			LOGGER.error("Got an expception while trying to search documents:", e);
			throw new InternalErrorExpection("Failed to search documents.");
		} finally {
			try {
				post.releaseConnection();
			} catch (Exception e) {
			}
		}
	}


	public String getRestaurant(Integer id) {
		HttpGet get = new HttpGet("http://localhost:9200/zomato/restaurant/" + id);
		try {
			HttpResponse response = client.execute(get);
			return readResponse(response);
		} catch (Exception e) {
			LOGGER.error("Got an expception while trying to search documents:", e);
			throw new InternalErrorExpection("Failed to search documents.");
		} finally {
			try {
				get.releaseConnection();
			} catch (Exception e) {}
		}
	}

}
