package org.nalby.zomato.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.nalby.zomato.entity.ReviewToSave;
import org.nalby.zomato.exception.InternalErrorExpection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDaoImpl implements SearchDao {

	private HttpClient client = HttpClientBuilder.create().build();

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchDaoImpl.class);

	private String readResponse(HttpResponse response) {
		BufferedReader rd = null;
		try {
			 rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			LOGGER.info("Got status :{}.", response.getStatusLine().getStatusCode());
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			String tmp = result.toString();
			LOGGER.debug("Got result:{}.", tmp);
			return tmp;
		} catch (IOException e) {
			LOGGER.error("Got an exception while reading response.", e);
			throw new InternalErrorExpection("Failed to read respose from es.");
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private HttpResponse postRequest(String url, String body) {
		HttpPost post = new HttpPost(url);
		try {
			LOGGER.debug("POST body:{}.", body);
			post.setEntity(new StringEntity(body));
			post.setHeader("Content-Type", "Application/json");
			return client.execute(post);
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

	public String proxyToES(String type, String searchBody) {
		HttpResponse response = postRequest("http://localhost:9200/zomato/" + type + "/_search", searchBody);
		return readResponse(response);
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
			} catch (Exception e) {
			}
		}
	}

	public Float getReviewAverageRate(Integer restaurantId) {
		String tmp = "{\"aggs\" : {\"reviews\": {\"children\": {\"type\": \"review\"}, \"aggs\": {\"avg_review\": {" +
				"\"avg\" : { \"field\" : \"rate\" }}}}},\"query\": {\"match\": {\"_id\":" + restaurantId + "}},\"size\":0}";
		HttpResponse response = postRequest("http://localhost:9200/zomato/restaurant/_search", tmp);
		if (response == null) {
			return null;
		}
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200 && statusCode != 201) {
			return null;
		}
		String ret = readResponse(response);
		if (ret == null || "".equals(ret)) {
			return null;
		}
		Pattern pattern = Pattern.compile("^.+\"avg_review\":\\{\"value\":([0-9\\.]+).*$");
		Matcher matcher = pattern.matcher(ret);
		if (matcher.find()) {
			LOGGER.debug("Match:{}.", matcher.group(1));
			return Float.valueOf(matcher.group(1));
		}
		return null;
	}

	public boolean indexReview(ReviewToSave reviewToSave) {
		HttpResponse response = postRequest("http://localhost:9200/zomato/review?parent=" + reviewToSave.getRestaurantId(),
				reviewToSave.toESIndexPayload());
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode == 200 || statusCode == 201;
	}

	public boolean updateAverageRate(Integer restaruantId, Float newRate) {
		String payload = "{\"script\": \"ctx._source.avg_rate=" + newRate.toString() + "\"}";
		HttpResponse response = postRequest("http://localhost:9200/zomato/restaurant/" + restaruantId + "/_update", payload);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode == 200 || statusCode == 201;
	}
	

}
