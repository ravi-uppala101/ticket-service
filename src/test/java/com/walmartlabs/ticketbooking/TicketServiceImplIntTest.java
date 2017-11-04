package com.walmartlabs.ticketbooking;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmartlabs.ticketbooking.config.TicketBookingConfig;
import com.walmartlabs.ticketbooking.domain.SeatHold;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketBookingConfig.class) 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceImplIntTest {

	@Autowired
	TicketService ticketService;
	
	@Test
	public void  test01_NoOfSeatsAvailable() {
		
		int total = ticketService.numSeatsAvailable();
		assertEquals(300, total);
	}
	
	@Test 
	public void  test02_FindAndHoldSeats() {
		//hold firstCustomer seats
		SeatHold seatHold = ticketService.findAndHoldSeats(6, "ravinderuppala@gmail.com");
		assertEquals("ravinderuppala@gmail.com", seatHold.getCustomerEmailId());
		System.out.println("seatHoldId = "+seatHold.getSeatHoldId());
		//reserve first customer seats
		String reservationId1 = ticketService.reserveSeats(seatHold.getSeatHoldId(), "ravinderuppala@gmail.com");
		System.out.println("reservationId1 = "+reservationId1);
		int total = ticketService.numSeatsAvailable();
		System.out.println("No of Seats available after reserving for customer ravinderuppala@gmail.com => "+total);
		assertEquals(294, total);
		
		// second hold on different customer email
		SeatHold seatHold1 = ticketService.findAndHoldSeats(30, "ravi.uppala@gmail.com");
		assertEquals("ravi.uppala@gmail.com", seatHold1.getCustomerEmailId());
		System.out.println("seatHoldId1 = "+seatHold1.getSeatHoldId());
		//reserve second customer seats
		String reservationId2 = ticketService.reserveSeats(seatHold1.getSeatHoldId(), "ravi.uppala@gmail.com");
	    System.out.println("reservationId1 = "+reservationId2);
		int total1 = ticketService.numSeatsAvailable();
		System.out.println("No of Seats available after reserving for customer ravinderuppala@gmail.com => "+total1);
		assertEquals(264, total1);
		
		//checking hold condition
		
		SeatHold seatHold3 = ticketService.findAndHoldSeats(100, "ravinderuppala3@gmail.com");
		assertEquals("ravinderuppala3@gmail.com", seatHold3.getCustomerEmailId());
		System.out.println("seatHoldId3 = "+seatHold3.getSeatHoldId());
		try {
			Thread.sleep(6000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int total3 = ticketService.numSeatsAvailable();
		System.out.println("No of Seats available after reserving for customer ravinderuppala3@gmail.com => "+total3);
		assertEquals(264, total3);
		
	    
	}
	
	@Test 
	public void  test03_ReserveSeatsWithNonHoldCustomerEmail() throws InterruptedException {
		SeatHold seatHold = ticketService.findAndHoldSeats(6, "ravinderuppala1@gmail.com");
		assertEquals("ravinderuppala1@gmail.com", seatHold.getCustomerEmailId());
		System.out.println("seatHoldId = "+seatHold.getSeatHoldId());
		Thread.sleep(6000L);
		int total = ticketService.numSeatsAvailable();
		assertEquals(264, total);
		
		// second hold on different customer email
		SeatHold seatHold1 = ticketService.findAndHoldSeats(58, "ravi.uppala1@gmail.com");
		assertEquals("ravi.uppala1@gmail.com", seatHold1.getCustomerEmailId());
		System.out.println("seatHoldId = "+seatHold1.getSeatHoldId());
		Thread.sleep(6000L);
		int total1 = ticketService.numSeatsAvailable();
		assertEquals(264, total1);
		
	}	
}
