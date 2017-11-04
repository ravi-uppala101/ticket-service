package com.walmartlabs.ticketbooking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.walmartlabs.ticketbooking.domain.CustomerKey;
import com.walmartlabs.ticketbooking.domain.Seat;
import com.walmartlabs.ticketbooking.domain.SeatHold;
import com.walmartlabs.ticketbooking.domain.Status;
import com.walmartlabs.ticketbooking.domain.Theater;
import com.walmartlabs.ticketbooking.exception.TicketBookingException;
import com.walmartlabs.ticketbooking.utils.TicketServiceValidator;

public class TicketServiceImpl implements TicketService {

	
	@Autowired
	Theater theater;
	
	@Autowired
	TicketServiceValidator ticketServiceValidator;
	
	@Override
	public int numSeatsAvailable() {
		int available = theater.getNumOfSeatsAvailable();
		return available;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		if(!ticketServiceValidator.validate(numSeats, customerEmail)) {
			throw new TicketBookingException("Input validation failed");
		}
		CustomerKey customerKey;
		if(theater.getNumOfSeatsAvailable() >= numSeats) {
			customerKey = theater.findAndHoldSeats(numSeats, customerEmail);
		} else {
			throw new TicketBookingException("Seats are not available as requested by the customer. No of seats available at "
					+ "this time are "+theater.getNumOfSeatsAvailable());
			
		}
		try {
			theater.startTimerforCustomer(customerKey);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SeatHold seatHold = new SeatHold(customerKey.getRandom(), customerKey.getcustomerEmail());
		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		
		CustomerKey customerKey = new CustomerKey(customerEmail, seatHoldId);
		if(!theater.getCustomerHoldMap().containsKey(customerKey)) {
			throw new TicketBookingException("There is no existing hold with this customer email Id "+customerEmail+
					 ". Please hold the seats before reserving it.");
		}
			// start reserving the seats.
		List<Seat> seats = theater.getCustomerHoldMap().get(customerKey);
		List<Seat> reservedSeats = new ArrayList<>();
		for(Seat seat : seats) {
			seat.setSeatStatus(Status.RESERVED);
			reservedSeats.add(seat);
		}
		UUID uuid = UUID.randomUUID();
		theater.getCustomerReservationMap().put(UUID.randomUUID(), reservedSeats);
		if(theater.getCustomerHoldMap().containsKey(customerKey)) {
			theater.getCustomerHoldMap().remove(customerKey);
		}
		return uuid.toString();
	}

}
