package com.walmartlabs.ticketbooking.domain;

import java.io.Serializable;

public class SeatHold implements Serializable {
	
	private static final long serialVersionUID = -8769910375725019768L;

	private final int seatHoldId;
	
	private final String customerEmailId;
	
	public SeatHold(int seatHoldId, String customerEmailId) {
		this.seatHoldId = seatHoldId;
		this.customerEmailId = customerEmailId;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public int getSeatHoldId() {
		return seatHoldId;
	}

}
