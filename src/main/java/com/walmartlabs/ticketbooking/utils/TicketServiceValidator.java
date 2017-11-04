package com.walmartlabs.ticketbooking.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketServiceValidator {

	private Pattern pattern;
	private Matcher matcher;

	public TicketServiceValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	private static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	
	public boolean validate(int numSeats, String customerEmail) {
		if(numSeats < 1 || !validateEmail(customerEmail)) {
			return false; 
		} 
		return true;
		
	}
	
	private boolean validateEmail(String customerEmail) {
		matcher = pattern.matcher(customerEmail);
		return matcher.matches();

	}
}
