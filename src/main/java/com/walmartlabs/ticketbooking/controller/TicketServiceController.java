package com.walmartlabs.ticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.walmartlabs.ticketbooking.TicketService;
import com.walmartlabs.ticketbooking.config.TicketBookingConfig;
import com.walmartlabs.ticketbooking.domain.SeatHold;

@RestController
@EnableAutoConfiguration
@Import(TicketBookingConfig.class)
@RequestMapping("/ticket-service")
public class TicketServiceController {
	
	@Autowired
	TicketService ticketService;

    @RequestMapping("/available")
    @ResponseBody
    int available() {
        return ticketService.numSeatsAvailable();
    }
    
    @RequestMapping(method = RequestMethod.POST, value ="/holdSeats")
    @ResponseBody
    SeatHold findAndHoldSeats(Integer numSeats, String customerEmail) {
    	System.out.println("numSeats = "+numSeats);
    	System.out.println("customerEmail = "+customerEmail);
        return ticketService.findAndHoldSeats(numSeats, customerEmail);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/reserveSeats")
    @ResponseBody
    String reserveSeats(Integer seatHoldId, String customerEmail) {
        return ticketService.reserveSeats(seatHoldId, customerEmail);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TicketServiceController.class, args);
    }
}
