package edu.neu.csye6200.covid19_test;


import org.junit.Test;

import edu.neu.csye6200.covid19.Vaccine;

import static org.junit.Assert.assertEquals;

public class VaccineTest {
	
	
	@Test
    public void testGetStatus() {
		Vaccine v = new Vaccine("READY", 0, 0, 90, "READY", 20, 0, 100, 0);
		assertEquals(v.getStatus(),"READY");
	}
}
