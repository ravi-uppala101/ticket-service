package com.walmartlabs.ticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.walmartlabs.ticketbooking.TicketService;
import com.walmartlabs.ticketbooking.config.TicketBookingConfig;
import com.walmartlabs.ticketbooking.domain.SeatHold;

@Controller
@EnableAutoConfiguration
@Import(TicketBookingConfig.class)
public class TicketServiceController {
	
	@Autowired
	TicketService ticketService;

    @RequestMapping("/available")
    @ResponseBody
    int available() {
        return ticketService.numSeatsAvailable();
    }
    
    @RequestMapping("/holdSeats")
    @ResponseBody
    SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        return ticketService.findAndHoldSeats(numSeats, customerEmail);
    }
    
    @RequestMapping("/reserveSeats")
    @ResponseBody
    String reserveSeats(int numSeats, String customerEmail) {
        return ticketService.reserveSeats(numSeats, customerEmail);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TicketServiceController.class, args);
    }
}
