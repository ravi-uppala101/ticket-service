package com.walmartlabs.ticketbooking.exception;

public class TicketBookingException extends RuntimeException {

	private static final long serialVersionUID = 7474074226856223362L;

	public TicketBookingException(String message) {
        super(message);
    }
}
