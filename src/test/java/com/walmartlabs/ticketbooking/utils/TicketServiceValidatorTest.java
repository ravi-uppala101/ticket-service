package com.walmartlabs.ticketbooking.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.walmartlabs.ticketbooking.utils.TicketServiceValidator;

public class TicketServiceValidatorTest {
	
	private TicketServiceValidator ticketServiceValidator;
	
	public TicketServiceValidatorTest() {
		ticketServiceValidator = new TicketServiceValidator();
	}
	
	@Test
	public void testValidate() {
		boolean result = ticketServiceValidator.validate(2, "ravinderuppala@gmail.com");
		assertTrue(result);
	}
	
	@Test
	public void testInvalidEmail() {
		boolean result = ticketServiceValidator.validate(2, "ravinderuppalagmail");
		assertFalse(result);
	}
	
	@Test
	public void testInvalidSeats() {
		boolean result = ticketServiceValidator.validate(0, "ravinderuppala@gmail.com");
		assertFalse(result);
	}

}
