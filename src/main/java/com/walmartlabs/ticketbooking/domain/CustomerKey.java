package com.walmartlabs.ticketbooking.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CustomerKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String customerEmail;

	private final int random;

	public CustomerKey(final String customerEmail, final int random) {
		this.customerEmail = customerEmail;
		this.random = random;
	}
	
	public int getRandom() {
		return random;
	}

	public String getcustomerEmail() {
		return customerEmail;
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
