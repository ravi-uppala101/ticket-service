package com.walmartlabs.ticketbooking.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.walmartlabs.ticketbooking.TicketService;
import com.walmartlabs.ticketbooking.TicketServiceImpl;
import com.walmartlabs.ticketbooking.domain.Theater;
import com.walmartlabs.ticketbooking.utils.TicketServiceValidator;

@Configuration
@EnableAsync
@PropertySource("classpath:application.properties")
public class TicketBookingConfig {
	
	@Value( "${holdTimer}" )
	private Long holdTimer;
	
	@Value( "${minRowOrSeat}" )
	private Integer minRowOrSeat;
	
	@Value( "${noOfRowsInTheater}" )
	private Integer noOfRowsInTheater;
	
	@Value( "${noOfSeatsInEachRow}" )
	private Integer noOfSeatsInEachRow;
	
	@Bean
	public TicketService ticketService() {
		return new TicketServiceImpl();
	}

	@Bean
	public Theater theater() {
		return new Theater(holdTimer, minRowOrSeat, noOfRowsInTheater, noOfSeatsInEachRow);
	}

	@Bean
	public TicketServiceValidator TicketServiceValidator() {
		return new TicketServiceValidator();
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("TicketBookingService-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
