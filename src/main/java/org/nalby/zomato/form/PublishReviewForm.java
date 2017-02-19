package org.nalby.zomato.form;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PublishReviewForm {
	@NotNull @Size(min = 140, max = 4096)
	private String text;
	
	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "5")
	private float rate;

	public String getText() {
		return text;
	}

	public float getRate() {
		return rate;
	}

}
