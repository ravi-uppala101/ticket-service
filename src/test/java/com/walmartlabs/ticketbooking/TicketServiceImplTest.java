package com.walmartlabs.ticketbooking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.walmartlabs.ticketbooking.domain.CustomerKey;
import com.walmartlabs.ticketbooking.domain.Seat;
import com.walmartlabs.ticketbooking.domain.SeatHold;
import com.walmartlabs.ticketbooking.domain.Theater;
import com.walmartlabs.ticketbooking.exception.TicketBookingException;
import com.walmartlabs.ticketbooking.utils.TicketServiceValidator;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {
	
    @Mock
    private Theater theater;
    
    @Mock
	TicketServiceValidator ticketServiceValidator;
    
    @InjectMocks
	private TicketService ticketService;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	public TicketServiceImplTest() {
		ticketService = new TicketServiceImpl();
	}
	
	@Test
	public void testNumSeatsAvailable() {
		when(theater.getNumOfSeatsAvailable()).thenReturn(2);
		int noOfSeats = ticketService.numSeatsAvailable();
		assertEquals(2, noOfSeats);
	}
	
	@Test
	public void testFindAndHoldSeatsInputValidationFailed() {
		thrown.expect(TicketBookingException.class);
		thrown.expectMessage("Input validation failed");
		when(ticketServiceValidator.validate(0, "ravinderuppala@gmail.com")).thenReturn(false);
		ticketService.findAndHoldSeats(0, "ravinderuppala@gmail.com");
	}
	
	@Test
	public void testFindAndHoldSeats() throws InterruptedException {
		when(ticketServiceValidator.validate(2, "ravinderuppala@gmail.com")).thenReturn(true);
		when(theater.getNumOfSeatsAvailable()).thenReturn(300);
		Random random = new Random();
		int randomNumber = random.nextInt();
		CustomerKey customerKey = new CustomerKey("ravinderuppala@gmail.com", randomNumber);
		when(theater.findAndHoldSeats(2, "ravinderuppala@gmail.com")).thenReturn(customerKey);
		theater.startTimerforCustomer(customerKey);
		
		SeatHold seatHold = ticketService.findAndHoldSeats(2, "ravinderuppala@gmail.com");
		assertEquals(randomNumber, seatHold.getSeatHoldId());
		assertEquals("ravinderuppala@gmail.com", seatHold.getCustomerEmailId());
	}
	
	@Test
	public void testFindAndHoldSeatsNotAvailableEnoughSeats() throws InterruptedException {
		thrown.expect(TicketBookingException.class);
		thrown.expectMessage("Seats are not available as requested by the customer. No of seats available at "
				+ "this time are "+300);
		
		when(ticketServiceValidator.validate(302, "ravinderuppala@gmail.com")).thenReturn(true);
		when(theater.getNumOfSeatsAvailable()).thenReturn(300);
		ticketService.findAndHoldSeats(302, "ravinderuppala@gmail.com");
	}
	
	@Test
	public void testFindAndHoldSeatsValidateInterruptedException() throws InterruptedException {

		when(ticketServiceValidator.validate(2, "ravinderuppala@gmail.com")).thenReturn(true);
		when(theater.getNumOfSeatsAvailable()).thenReturn(300);
		Random random = new Random();
		int randomNumber = random.nextInt();
		CustomerKey customerKey = new CustomerKey("ravinderuppala@gmail.com", randomNumber);
		when(theater.findAndHoldSeats(2, "ravinderuppala@gmail.com")).thenReturn(customerKey);
		Mockito.doThrow(new InterruptedException()).when(theater).startTimerforCustomer(customerKey);
		
		ticketService.findAndHoldSeats(2, "ravinderuppala@gmail.com");
	}
	
	@Test
	public void testReserveSeats() {

		Random random = new Random();
		int randomNumber = random.nextInt();
		CustomerKey customerKey = new CustomerKey("ravinderuppala@gmail.com", randomNumber);
		Map<CustomerKey, List<Seat>> customerHoldMap = new ConcurrentHashMap<>();
		List<Seat> seats = new ArrayList<>();
		for(int i = 1;i <= 30; i++) {
			Seat seat = new Seat(1, i);
			seats.add(seat);
		}
		customerHoldMap.put(customerKey, seats);
		when(theater.getCustomerHoldMap()).thenReturn(customerHoldMap);
		Map<UUID, List<Seat>> reservationMap = new ConcurrentHashMap<>();
		when(theater.getCustomerReservationMap()).thenReturn(reservationMap);
		
		String randomUUID = ticketService.reserveSeats(randomNumber, "ravinderuppala@gmail.com");
		assertNotNull(randomUUID);
	}
	
	@Test
	public void testReserveSeatsNoExistingHold() {
		thrown.expect(TicketBookingException.class);
		thrown.expectMessage("There is no existing hold with this customer email Id "+"ravinderuppala@gmail.com"+
				 ". Please hold the seats before reserving it.");
		
		
		when(ticketServiceValidator.validate(2, "ravinderuppala@gmail.com")).thenReturn(true);
		when(theater.getNumOfSeatsAvailable()).thenReturn(300);
		
		
		Random random = new Random();
		int randomNumber = random.nextInt();
		Map<CustomerKey, List<Seat>> customerHoldMap = new ConcurrentHashMap<>();
		List<Seat> seats = new ArrayList<>();
		for(int i = 1;i <= 30; i++) {
			Seat seat = new Seat(1, i);
			seats.add(seat);
		}
		//customerHoldMap.put(customerKey, seats);
		when(theater.getCustomerHoldMap()).thenReturn(customerHoldMap);
		
		ticketService.reserveSeats(randomNumber, "ravinderuppala@gmail.com");
	}

}
