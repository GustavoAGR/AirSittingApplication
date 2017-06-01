package com.boxever.air.seat;

import java.util.List;

/**
 * SeatRow class to contains all the seat row information and list of seats
 * 
 * @author dkumar
 *
 */
public class SeatRow {

	private int m_seatRowIndex;
	private int m_remCapacity;
	private int m_totalCapacity;
	private int m_availableWindowSeatsCount;
	private List<Seat> m_seats;

	public int getSeatRowIndex() {
		return m_seatRowIndex;
	}

	public int getRemCapacity() {
		return m_remCapacity;
	}

	public void setRemCapacity(int capacity) {
		m_remCapacity = capacity;
	}

	public int getTotalCapacity() {
		return m_totalCapacity;
	}

	public int getAvailableWindowSeatsCount() {
		return m_availableWindowSeatsCount;
	}

	public void decreaseAvailableWindowSeatsCount() {
		m_availableWindowSeatsCount--;
	}

	public List<Seat> getSeats() {
		return m_seats;
	}

	public void setSeats(List<Seat> seats) {
		m_seats = seats;
	}

	public SeatRow(int seatRowIndex, int totalCapacity,
			int availableWindowSeats, List<Seat> seats) {
		m_seatRowIndex = seatRowIndex;
		m_remCapacity = totalCapacity;
		m_totalCapacity = totalCapacity;
		m_availableWindowSeatsCount = availableWindowSeats;
		m_seats = seats;
	}

	/**
	 * Return the view of the seat row
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Seat seat : m_seats) {
			sb.append(
					seat.getAssignedPax() != null ? seat.getAssignedPax()
							.getPaxId() : 0).append(" ");
		}
		return sb.toString();
	}
}
