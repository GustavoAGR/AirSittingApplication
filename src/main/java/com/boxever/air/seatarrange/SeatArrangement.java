package com.boxever.air.seatarrange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.boxever.air.passenger.Passenger;
import com.boxever.air.passenger.PaxGroup;
import com.boxever.air.seat.Seat;
import com.boxever.air.seat.SeatRow;

/**
 * Seat Arrangement job to arrange the seat for the passengers
 * 
 * @author dkumar
 *
 */
public class SeatArrangement {

	// Global Seat Map
	private Map<Integer, SeatRow> m_seatsMap = new HashMap<>();
	private int m_totalNoOfAvailableSeats;

	private int m_satisfiedPaxCount = 0;
	private int m_totalNoOfPax = 0;

	public static String SEPERATER = "\n=============\n";
	private static final int AVAILABLE_WINDOW_SEATS_IN_A_ROW = 2;

	/**
	 * Method to read the pax info from file and assign the seats
	 * 
	 * @param filePath
	 *            Path of the file containing pax info
	 * @return Seat map with assigned pax
	 * @throws IllegalArgumentException
	 */
	public String arrangeSeatForPaxFromFile(String filePath)
			throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			int lineNum = 1;
			String line;

			while ((line = br.readLine()) != null) {
				String[] paxes = line.split("[\\s+]");

				if (lineNum == 1) {
					_parseSeatMapDimensions(line, paxes);
				} else {
					m_totalNoOfPax = m_totalNoOfPax + paxes.length;
					try {
						if (paxes.length >= 1) {
							PaxGroup paxGroup = _createGroupForPax(paxes);
							int seatRowIndex = _findPossibleSeatRowForPaxGroup(paxGroup);
							if (seatRowIndex >= 0) {
								_assignSeatsForPaxGroup(seatRowIndex, paxGroup);
								m_totalNoOfAvailableSeats = m_totalNoOfAvailableSeats
										- paxes.length;
							} else if (m_totalNoOfAvailableSeats >= paxGroup
									.getTotalPax()) {
								// Didn't find the available seats in one row
								// for paxGroup but there is overall capacity in
								// seatmap
								_assignEmptySeatsForPaxGroup(paxGroup);
								m_totalNoOfAvailableSeats = m_totalNoOfAvailableSeats
										- paxes.length;
							} else {
								throw new RuntimeException(
										String.format(
												"No available seats for pax group. Available Seats: %s and Total Pax in the group: %s ",
												m_totalNoOfAvailableSeats,
												paxGroup.getTotalPax()));
							}
						}
					} catch (IllegalArgumentException e) {
						sb.append(SEPERATER)
								.append(" Step ")
								.append((lineNum - 1))
								.append(SEPERATER)
								.append("Error: Skipping the line as data format for pax was invalid. "
										+ line);
					} catch (RuntimeException e) {
						sb.append(SEPERATER)
								.append(" Step ")
								.append((lineNum - 1))
								.append(SEPERATER)
								.append("Error: Skipping the paxGroup as there is no available seat for pax group. "
										+ line);
					}
				}
				lineNum++;
				sb.append(SEPERATER).append(" Step ").append((lineNum - 1))
						.append(SEPERATER).append(_getSeatMap());
			}
			System.out.println(sb.toString());
			return _getSeatMap();

		} catch (IOException e) {
			System.out.println("Could not able to read the pax file . "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * Parse the dimensions for the seat map and initialise
	 * 
	 * @param line
	 *            The String containing dimension information
	 * @param dimensions
	 *            The dimensions
	 */
	private void _parseSeatMapDimensions(String line, String[] dimensions)
			throws IllegalArgumentException {
		if (dimensions.length < 2) {
			throw new IllegalArgumentException(
					"Dimensions can not be determinded with less than 2 digits in the first row. Expected two digits with space but found : "
							+ line);
		}
		try {
			Integer numOfSeatsInARow = Integer.parseInt(dimensions[0]);
			Integer numOfRowsInPlane = Integer.parseInt(dimensions[1]);
			m_totalNoOfAvailableSeats = numOfSeatsInARow * numOfRowsInPlane;
			if (m_totalNoOfAvailableSeats == 0) {
				throw new IllegalArgumentException();
			}

			_initialiseSeatMap(numOfRowsInPlane, numOfSeatsInARow);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Values for dimensions should be Integer from [1-9] but Found : "
							+ line);
		}
	}

	/**
	 * Initialise the seat map at the first time
	 * 
	 * @param numOfRowsInPlane
	 *            Number of the rows in the plane
	 * @param numOfSeatsInARow
	 *            Number of seats in one row
	 */
	private void _initialiseSeatMap(Integer numOfRowsInPlane,
			Integer numOfSeatsInARow) {
		for (int i = 0; i < numOfRowsInPlane.intValue(); i++) {
			List<Seat> seatList = new ArrayList<>();
			for (int j = 0; j < numOfSeatsInARow.intValue(); j++) {
				boolean isWindowSeat = j == 0
						|| j == numOfSeatsInARow.intValue() ? true : false;
				seatList.add(new Seat(j, i, isWindowSeat));
			}
			m_seatsMap.put(i, new SeatRow(i, numOfSeatsInARow,
					AVAILABLE_WINDOW_SEATS_IN_A_ROW, seatList));
		}
	}

	/**
	 * Assign seats for the group of passengers
	 * 
	 * @param seatRowIndex
	 *            SeatRow index which have capacity for the passengers from pax
	 *            group
	 * @param paxGroup
	 *            The group of passengers
	 */
	private void _assignSeatsForPaxGroup(int seatRowIndex, PaxGroup paxGroup) {
		SeatRow seatRow = m_seatsMap.get(seatRowIndex);
		List<Passenger> paxList = paxGroup.getSortedPaxListByWindowPref();

		int remainingPax = paxList.size();
		final int WINDOW_SEAT_1_INDEX = 0;
		final int WINDOW_SEAT_2_INDEX = seatRow.getTotalCapacity() - 1;

		List<Seat> seatList = seatRow.getSeats();
		int emptySeatIndex = _findFirstEmptySeatIndexInRow(seatList);
		Seat windowSeatLeft = seatList.get(WINDOW_SEAT_1_INDEX);
		Seat windowSeatRight = seatList.get(WINDOW_SEAT_2_INDEX);

		for (Passenger pax : paxList) {
			if (pax.hasWindowSeatPreference()) {
				if (windowSeatLeft.getAssignedPax() == null) {
					seatList.get(WINDOW_SEAT_1_INDEX).setAssignedPax(pax);
					emptySeatIndex++;
					remainingPax--;
					m_satisfiedPaxCount++;
					seatRow.decreaseAvailableWindowSeatsCount();
					continue;
				} else if (windowSeatRight.getAssignedPax() == null) {
					seatList.get(WINDOW_SEAT_2_INDEX).setAssignedPax(pax);
					remainingPax--;
					m_satisfiedPaxCount++;
					emptySeatIndex = seatRow.getTotalCapacity() - remainingPax
							- 1;
					seatRow.decreaseAvailableWindowSeatsCount();
					continue;
				}
			}
			seatList.get(emptySeatIndex++).setAssignedPax(pax);
			m_satisfiedPaxCount++;
		}
		seatRow.setRemCapacity(seatRow.getRemCapacity() - paxList.size());
		m_seatsMap.put(seatRowIndex, seatRow);
	}

	/**
	 * Find the index of first empty seat in a row
	 * 
	 * @param seatList
	 *            List of seats in the row
	 * @return Index of first empty seat in the row
	 */
	private int _findFirstEmptySeatIndexInRow(List<Seat> seatList) {
		for (Seat seat : seatList) {
			if (seat.getAssignedPax() == null) {
				return seat.getSeatNumInTheRow();
			}
		}
		return -1;
	}

	/**
	 * Assign remaining empty seats for the passengers
	 * 
	 * @param paxGroup
	 *            The group of passengers
	 * 
	 */
	private void _assignEmptySeatsForPaxGroup(PaxGroup paxGroup) {
		List<Passenger> paxList = paxGroup.getSortedPaxListByWindowPref();
		int i = 0;
		for (SeatRow seatRow : m_seatsMap.values()) {
			List<Seat> seatList = seatRow.getSeats();
			for (Seat seat : seatList) {
				if (i == paxList.size()) {
					return;
				} else if (seat.getAssignedPax() == null) {
					Passenger pax = paxList.get(i++);
					seatList.get(seat.getSeatNumInTheRow()).setAssignedPax(pax);
					if (seat.isWindowSeat() && pax.hasWindowSeatPreference()) {
						m_satisfiedPaxCount++;
					}
				}
			}
		}
	}

	/**
	 * Get the current status of the seat map
	 * 
	 * @return Seat Map view
	 */
	private String _getSeatMap() {
		StringBuilder sb = new StringBuilder();
		for (SeatRow seatRow : m_seatsMap.values()) {
			sb.append(seatRow.toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Create the PaxGroup for the passenger's info
	 * 
	 * @param paxes
	 *            pax strings
	 * @return PaxGroup instance
	 */
	private PaxGroup _createGroupForPax(String[] paxes) {
		List<Passenger> paxList = Arrays.stream(paxes).map(String::trim)
				.filter(s -> !s.isEmpty()).map(SeatArrangement::_createPax)
				.collect(Collectors.toList());
		return new PaxGroup.PaxGroupBuilder(paxList).build();
	}

	/**
	 * Create the passenger instance for pax info
	 * 
	 * @param pax
	 *            String
	 * @return Instance of Passenger
	 */
	private static Passenger _createPax(String pax) {
		return _createPax(pax, true);
	}

	/**
	 * Create Pax from pax string
	 * 
	 * @param pax
	 *            Pax string
	 * @param isWithGroup
	 *            If the pax is from group or not
	 * @return Passenger instance
	 */
	private static Passenger _createPax(String pax, boolean isWithGroup) {
		Matcher matcher = Pattern.compile("([0-9]*)(W)?").matcher(pax);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"Passenger data format is incorrect, expected [0-9]*W?, received "
							+ pax);
		}
		return new Passenger.PaxBuilder(Integer.valueOf(matcher.group(1)))
				.hasWindowSeatPreference(matcher.group(2) != null)
				.isWithGroup(isWithGroup).build();
	}

	/**
	 * Find possible seat row for a pax group
	 * 
	 * @param paxGroup
	 * @return Index of the seat row which have capacity to assign paxes from
	 *         PaxGroup
	 */
	private int _findPossibleSeatRowForPaxGroup(PaxGroup paxGroup) {
		int seatRowIndex = -1;
		for (SeatRow seatRow : m_seatsMap.values()) {
			if (seatRow.getRemCapacity() >= paxGroup.getTotalPax()) {
				if (paxGroup.hasWindowSeatPrefPaxes()
						&& seatRow.getAvailableWindowSeatsCount() > 0) {
					return seatRow.getSeatRowIndex();
				} else if (!paxGroup.hasWindowSeatPrefPaxes()) {
					return seatRow.getSeatRowIndex();
				}
			}
		}
		return seatRowIndex;
	}

	/**
	 * Return percentage of satisfied passengers
	 * 
	 * @return Percentage of satisfied paxes
	 */
	public int satisfiedPaxPercentage() {
		return m_satisfiedPaxCount * 100 / m_totalNoOfPax;
	}
}
