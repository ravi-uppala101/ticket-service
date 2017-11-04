package com.walmartlabs.ticketbooking.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Seat implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final int rowNum;
	
	private final int colNum;
	
	private Status seatStatus = Status.AVAILABLE;
	
	public Seat(int rowNum, int colNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public Status getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(Status seatStatus) {
		this.seatStatus = seatStatus;
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
}
