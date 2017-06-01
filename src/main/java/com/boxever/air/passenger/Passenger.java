package com.boxever.air.passenger;

/**
 * Passenger class to contain the pax info.
 * 
 * @author dkumar
 *
 */
public class Passenger {

	private int m_paxId;
	private int m_score;
	private int m_seatNum;
	private boolean m_isWithGroup;
	private boolean m_hasWindowSeatPreference;

	private Passenger(PaxBuilder builder) {
		m_paxId = builder.paxId;
		m_score = builder.score;
		m_seatNum = builder.seatNum;
		m_isWithGroup = builder.isWithGroup;
		m_hasWindowSeatPreference = builder.hasWindowSeatPreference;
	}

	public int getPaxId() {
		return m_paxId;
	}

	public int getScore() {
		return m_score;
	}

	public int getSeatNum() {
		return m_seatNum;
	}

	public boolean isWithGroup() {
		return m_isWithGroup;
	}

	public boolean hasWindowSeatPreference() {
		return m_hasWindowSeatPreference;
	}

	/**
	 * Builder class for Passenger
	 * 
	 * @author dkumar
	 *
	 */
	public static class PaxBuilder {
		private int paxId;
		private int score;
		private int seatNum;
		private boolean isWithGroup;
		private boolean hasWindowSeatPreference;

		public PaxBuilder(int paxId) {
			this.paxId = paxId;
		}

		public PaxBuilder score(int score) {
			this.score = score;
			return this;
		}

		public PaxBuilder seatNum(int seatNum) {
			this.seatNum = seatNum;
			return this;
		}

		public PaxBuilder isWithGroup(boolean isWithGroup) {
			this.isWithGroup = isWithGroup;
			return this;
		}

		public PaxBuilder hasWindowSeatPreference(
				boolean hasWindowSeatPreference) {
			this.hasWindowSeatPreference = hasWindowSeatPreference;
			return this;
		}

		public Passenger build() {
			return new Passenger(this);
		}

	}

}
