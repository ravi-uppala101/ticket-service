package com.walmartlabs.ticketbooking.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TheaterRow {
	
	private int numOfSeatsAvailable;
	
	//key is no of seats available in that row
	private LinkedList<Seat> seats = new LinkedList<>();
	
	private int minRowOrSeat;
	
	private int noOfSeatsInEachRow;
	
	public TheaterRow(int rowNum, final int minRowOrSeat, final int noOfSeatsInEachRow) {
		numOfSeatsAvailable = noOfSeatsInEachRow;
		this.minRowOrSeat = minRowOrSeat;
		this.noOfSeatsInEachRow = noOfSeatsInEachRow;
		buildTheaterRow(rowNum);
	}

	public LinkedList<Seat> getSeats() {
		return seats;
	}

	public int getNumOfSeatsAvailable() {
		return numOfSeatsAvailable;
	}

	private void buildTheaterRow(int rowNum) {
		for(int i= minRowOrSeat;i<=noOfSeatsInEachRow;i++) {
			Seat seat = new Seat(rowNum,i);
			seats.add(seat);
		}
	}
	
	public List<Seat> findAndHoldSeats(int numOfSeats) {
		int seatsHoldInThisRow = 0;
		List<Seat> seatsHold = new ArrayList<>();
		List<Seat> removableSeats = new ArrayList<>();
		if(numOfSeatsAvailable > 0) {
			for(Seat seat : seats) {
				if(seat.getSeatStatus() == Status.AVAILABLE) {
					seat.setSeatStatus(Status.HOLD);
					seatsHold.add(seat);
					seatsHoldInThisRow++;
					removableSeats.add(seat);
					if(numOfSeats == seatsHoldInThisRow)
						break;
				}
			}
		}
		if(removableSeats.size() > 1) {
			seats.removeAll(removableSeats);
		}
		numOfSeatsAvailable = numOfSeatsAvailable - seatsHoldInThisRow;
		return seatsHold;
	}
	
	public void addSeatsBackToRow(Seat seat) {
		seats.add(seat);
		numOfSeatsAvailable++;
	}
	
}
