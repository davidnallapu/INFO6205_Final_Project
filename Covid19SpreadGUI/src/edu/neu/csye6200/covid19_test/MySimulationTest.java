package edu.neu.csye6200.covid19_test;


import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.neu.csye6200.covid19.MySimulation;
import edu.neu.csye6200.covid19.PeopleGrid;
import edu.neu.csye6200.covid19.Vaccination;

public class MySimulationTest {

	
	@Test
    public void testPauseSim() {
		MySimulation ms = new MySimulation();
		
		ms.pauseSim();
		assertEquals(ms.paused,true);
	}
	
	@Test
    public void testStopSim() {
		MySimulation ms = new MySimulation();
		ms.tester();
		ms.stopSim();
		assertEquals(ms.done,true);
	}
	
	
}
