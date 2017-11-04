package com.walmartlabs.ticketbooking;

import com.walmartlabs.ticketbooking.domain.SeatHold;

/**
 * @author qow028
 *
 */
public interface TicketService {
	
	/** The number of seats available in the venue that are neither held nor reserved
	 * @return the number of tickets availale in the venue
	 */
	int numSeatsAvailable();
	
	/** Find and hold the best available seats for a customer
	 * @param numSeats - no of seat to find and hold for a customer
	 * @param customerEmail - unique identifier for the customer
	 * @return - seatHold object identifying the specific seats and related information
	 */
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);
	
	/** commit seats held for a specific customer
	 * @param seatHoldId - the seat hold identofier
	 * @param customerEmail - the email address of the customer which seat hold is assigned
	 * @return reservation confirmation code
	 */
	String reserveSeats(int seatHoldId, String customerEmail);

}
