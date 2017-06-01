package com.boxever.air.passenger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test suites for PaxGroup Class
 * 
 * @author dkumar
 *
 */
public class PaxGroupTest {

	@Test
	public void testPaxGroupMethods() throws IOException {
		PaxGroup paxGroup = new PaxGroup.PaxGroupBuilder(_createDummyPassengerList()).build();
		
		Assert.assertNotNull(paxGroup.getPaxList());
		Assert.assertEquals(5, paxGroup.getTotalPax());
		Assert.assertEquals(2, paxGroup.getWindowSeatPrefPaxesCount());
		Assert.assertTrue(paxGroup.hasWindowSeatPrefPaxes());
		
		List<Passenger> sortedPaxList =  paxGroup.getSortedPaxListByWindowPref();
		Assert.assertNotNull(sortedPaxList);
		Assert.assertEquals(5, sortedPaxList.size());
		Assert.assertEquals(3, sortedPaxList.get(0).getPaxId());
		Assert.assertEquals(5, sortedPaxList.get(1).getPaxId());
		Assert.assertEquals(1, sortedPaxList.get(2).getPaxId());
		Assert.assertEquals(2, sortedPaxList.get(3).getPaxId());
		Assert.assertEquals(4, sortedPaxList.get(4).getPaxId());
	}
	
	private List<Passenger> _createDummyPassengerList(){
		List<Passenger> paxList = new ArrayList<>();
		paxList.add(new Passenger.PaxBuilder(1).hasWindowSeatPreference(false).build());
		paxList.add(new Passenger.PaxBuilder(2).hasWindowSeatPreference(false).build());
		paxList.add(new Passenger.PaxBuilder(3).hasWindowSeatPreference(true).build());
		paxList.add(new Passenger.PaxBuilder(4).hasWindowSeatPreference(false).build());
		paxList.add(new Passenger.PaxBuilder(5).hasWindowSeatPreference(true).build());
		return paxList;
	}
}
