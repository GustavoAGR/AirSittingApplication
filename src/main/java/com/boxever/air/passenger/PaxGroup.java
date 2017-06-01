package com.boxever.air.passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * PaxGroup class for the group of passengers
 * 
 * @author dkumar
 *
 */
public class PaxGroup {

	private List<Passenger> m_paxList;

	private PaxGroup(PaxGroupBuilder builder) {
		m_paxList = builder.paxList;
	}

	/**
	 * Get total number of passengers in the pax group
	 * 
	 * @return Total no. of paxes
	 */
	public int getTotalPax() {
		return m_paxList != null ? m_paxList.size() : 0;
	}

	/**
	 * Return count of passengers having window seat pref
	 * 
	 * @return Window seat pref pax count
	 */
	public int getWindowSeatPrefPaxesCount() {
		return (int) (getTotalPax() > 0 ? m_paxList.stream()
				.filter(p -> p.hasWindowSeatPreference()).count() : 0);
	}

	/**
	 * Return if PaxGroup contains WindowSeat Pref Pax
	 * 
	 * @return True If pax group contains the window seat preference pax
	 *         otherwise false
	 */
	public boolean hasWindowSeatPrefPaxes() {
		return getWindowSeatPrefPaxesCount() > 0 ? true : false;
	}

	public List<Passenger> getPaxList() {
		return m_paxList;
	}

	/**
	 * Sort the passenger list by Window Seat prefered pax first and then other
	 * pax
	 * 
	 * @return Sorted list of passengers
	 */
	public List<Passenger> getSortedPaxListByWindowPref() {
		List<Passenger> list = new ArrayList<>();
		List<Passenger> listWithOutWindowPref = new ArrayList<>();
		if (m_paxList != null && hasWindowSeatPrefPaxes()) {
			for (Passenger pax : m_paxList) {
				if (pax.hasWindowSeatPreference()) {
					list.add(pax);
				} else {
					listWithOutWindowPref.add(pax);
				}
			}
			list.addAll(listWithOutWindowPref);
			return list;
		}
		return m_paxList;
	}

	public static class PaxGroupBuilder {

		private List<Passenger> paxList;

		public PaxGroupBuilder(List<Passenger> paxList) {
			this.paxList = paxList;
		}

		public PaxGroupBuilder paxList(List<Passenger> paxList) {
			this.paxList = paxList;
			return this;
		}

		public PaxGroup build() {
			return new PaxGroup(this);
		}
	}

}
