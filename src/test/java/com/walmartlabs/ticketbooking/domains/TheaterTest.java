package com.walmartlabs.ticketbooking.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.SortedMap;

import org.junit.Test;

import com.walmartlabs.ticketbooking.domain.CustomerKey;
import com.walmartlabs.ticketbooking.domain.Theater;
import com.walmartlabs.ticketbooking.domain.TheaterRow;

public class TheaterTest {
	
	private Theater theater;
	
	public TheaterTest() {
		theater = new Theater(4000, 1, 10, 30);
	}
	
	@Test
	public void testTheaterMatrix() {
		SortedMap<Integer, TheaterRow> theaterMatrix = theater.getTheaterMatrix();
		
		assertNotNull(theaterMatrix);
		assertEquals(10, theaterMatrix.size());
		for(int i = 1; i <= theaterMatrix.size(); i++) {
			assertEquals(30, theaterMatrix.get(i).getSeats().size());
			assertEquals(30, theaterMatrix.get(i).getNumOfSeatsAvailable());
		}
		assertEquals(300, theater.getNumOfSeatsAvailable());
		
	}
	
	@Test
	public void testFindAndHoldSeats() {
		
		theater.findAndHoldSeats(30, "ravinderuppala@gmail.com");
		assertEquals(270, theater.getNumOfSeatsAvailable());
		
		assertEquals(0, theater.getTheaterMatrix().get(10).getSeats().size());
		assertEquals(0, theater.getTheaterMatrix().get(10).getNumOfSeatsAvailable());
		
	}
	
	@Test
	public void testStartTimerforCustomer() throws InterruptedException {
		
		CustomerKey customerKey = theater.findAndHoldSeats(30, "ravinderuppala@gmail.com");
		assertEquals(270, theater.getNumOfSeatsAvailable());
		
		assertEquals(0, theater.getTheaterMatrix().get(10).getSeats().size());
		assertEquals(0, theater.getTheaterMatrix().get(10).getNumOfSeatsAvailable());
		
		theater.startTimerforCustomer(customerKey);
		
	}

}
