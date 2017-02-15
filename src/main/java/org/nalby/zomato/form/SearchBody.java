package org.nalby.zomato.form;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.nalby.zomato.exception.BadParameterException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchBody {

	@JsonProperty
	@NotNull
	private Map<String, Object> query;
	
	public void validate() {
		Integer from = (Integer) query.get("from");
		Integer size = (Integer) query.get("size");
		if (size != 10 || from < 0) {
			throw new BadParameterException("Unexpected range, from:" + from + " size " + size);
		}
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new BadParameterException("Invalid json format.");
		}
	}
}
