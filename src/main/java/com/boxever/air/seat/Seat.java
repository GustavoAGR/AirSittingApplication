package com.boxever.air.seat;

import com.boxever.air.passenger.Passenger;

/**
 * Class to contain the information for Seat
 * 
 * @author dkumar
 *
 */
public class Seat {

	private int m_seatNumInTheRow;
	private int m_rowNum;
	private boolean m_isWindowSeat;
	private Passenger m_assignedPax;

	public Seat(int seatNumInTheRow, int rowNum, boolean isWindowSeat) {
		m_seatNumInTheRow = seatNumInTheRow;
		m_rowNum = rowNum;
		m_isWindowSeat = isWindowSeat;
	}

	public Passenger getAssignedPax() {
		return m_assignedPax;
	}

	public int getSeatNumInTheRow() {
		return m_seatNumInTheRow;
	}

	public void setAssignedPax(Passenger pax) {
		m_assignedPax = pax;
	}

	public String getSeatId() {
		return Integer.toString(m_rowNum) + Integer.toString(m_seatNumInTheRow);
	}

	public boolean isWindowSeat() {
		return m_isWindowSeat;
	}
}
