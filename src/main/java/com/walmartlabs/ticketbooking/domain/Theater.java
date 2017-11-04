package com.walmartlabs.ticketbooking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

public class Theater {

	private static final Logger logger = LogManager.getLogger(Theater.class);

	private int numOfSeatsAvailable;

	Random random = new Random();

	private final long holdTimer;

	private final  int minRowOrSeat;

	private final int noOfRowsInTheater;

	private final int noOfSeatsInEachRow;

	public Theater(final long holdTimer, final int minRowOrSeat, final int noOfRowsInTheater,
			final int noOfSeatsInEachRow) {
		this.holdTimer = holdTimer;
		this.minRowOrSeat = minRowOrSeat;
		this.noOfRowsInTheater = noOfRowsInTheater;
		this.noOfSeatsInEachRow = noOfSeatsInEachRow;
		buildTheaterMatrix();
	}

	private SortedMap<Integer, TheaterRow> theaterMatrix = new TreeMap<>((Integer o1, Integer o2) -> {
		return o2.compareTo(o1);
	});

	private Map<CustomerKey, List<Seat>> customerHoldMap = new ConcurrentHashMap<>();

	private Map<UUID, List<Seat>> customerReservationMap = new ConcurrentHashMap<>();

	ExecutorService executorService = Executors.newFixedThreadPool(10);

	public Map<UUID, List<Seat>> getCustomerReservationMap() {
		return customerReservationMap;
	}

	public void setCustomerReservationMap(Map<UUID, List<Seat>> customerReservationMap) {
		this.customerReservationMap = customerReservationMap;
	}

	public Map<CustomerKey, List<Seat>> getCustomerHoldMap() {
		return customerHoldMap;
	}

	public void setCustomerHoldMap(Map<CustomerKey, List<Seat>> customerHoldMap) {
		this.customerHoldMap = customerHoldMap;
	}

	public SortedMap<Integer, TheaterRow> getTheaterMatrix() {
		return theaterMatrix;
	}

	public int getNumOfSeatsAvailable() {
		return numOfSeatsAvailable;
	}

	public synchronized CustomerKey findAndHoldSeats(int numOfSeats, String customerEmail) {
		int noOfSeatsHold = 0;
		int remainingSeats = numOfSeats;
		List<Seat> totalSeatsHold = new ArrayList<>();
		for (Map.Entry<Integer, TheaterRow> entry : theaterMatrix.entrySet()) {
			if (entry.getValue().getNumOfSeatsAvailable() != 0) {
				List<Seat> seatsInThisRow = entry.getValue().findAndHoldSeats(remainingSeats);
				int seatsHoldInThisRow = seatsInThisRow.size();
				noOfSeatsHold = noOfSeatsHold + seatsHoldInThisRow;
				remainingSeats = remainingSeats - seatsHoldInThisRow;
				totalSeatsHold.addAll(seatsInThisRow);
			}
			if (noOfSeatsHold == numOfSeats)
				break;
		}
		numOfSeatsAvailable = numOfSeatsAvailable - noOfSeatsHold;
		int seatHoldId = random.nextInt();
		CustomerKey customerKey = new CustomerKey(customerEmail, seatHoldId);
		customerHoldMap.put(customerKey, totalSeatsHold);
		return customerKey;
	}

	private void buildTheaterMatrix() {
		for (int i = minRowOrSeat; i <= noOfRowsInTheater; i++) {
			TheaterRow theaterRow = new TheaterRow(i, minRowOrSeat, noOfSeatsInEachRow);
			theaterMatrix.put(i, theaterRow);
			numOfSeatsAvailable = numOfSeatsAvailable + noOfSeatsInEachRow;
		}
	}

	@Async
	public void startTimerforCustomer(CustomerKey customerKey) throws InterruptedException {
		// Artificial delay of 1s for demonstration purposes
		logger.debug("thead invocation started for customerKey " + customerKey);
		Thread.sleep(holdTimer);
		logger.debug("thead invocation re-started for customerKey " + customerKey);
		if (customerHoldMap.containsKey(customerKey)) {
			logger.debug("deleting from hold map " + customerKey);
			List<Seat> totalSeatsHold = customerHoldMap.get(customerKey);
			// give back seats to total from the hold map because time has
			// elapsed for the customer.
			for (Seat seat : totalSeatsHold) {
				TheaterRow theaterRow = theaterMatrix.get(seat.getRowNum());
				theaterRow.addSeatsBackToRow(seat);
				numOfSeatsAvailable++;
			}
			logger.debug("numOfSeatsAvailable in async method--->" + numOfSeatsAvailable);
		}
	}

}
