package edu.neu.csye6200.covid19_test;

import org.junit.Test;

import edu.neu.csye6200.covid19.Vaccination;
import edu.neu.csye6200.covid19.Vaccine;

import static org.junit.Assert.assertEquals;


public class VaccinationTest {

	@Test
    public void testGetStatus() {
		Vaccination v = new Vaccination();
		
		v.pausedVaccination();
		assertEquals(v.bt.getStatus(),"PAUSED");
	}

	
	
}
