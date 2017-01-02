package org.nalby.zomato.model;

import java.util.List;

public class Restaurant {
	private String name;
	private String district;
	private String address;
	private String phone;
	private double rate;
	private String imageUrl;
	private String knownFor;
	private String approxPrice;
	private String thumbImageUrl;
	private List<String> keywords;
	private List<String> highlights;
	private List<String> cuisines;
	public String getName() {
		return name;
	}
	public String getDistrict() {
		return district;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	public double getRate() {
		return rate;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getKnownFor() {
		return knownFor;
	}
	public String getApproxPrice() {
		return approxPrice;
	}
	public String getThumbImageUrl() {
		return thumbImageUrl;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public List<String> getHighlights() {
		return highlights;
	}
	public List<String> getCuisines() {
		return cuisines;
	}
}
