package edu.neu.csye6200.covid19_test;

import org.junit.Test;

import edu.neu.csye6200.covid19.AppUI;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AppUITest {
	@Test
    public void testSimulation() {
		AppUI app = new AppUI();
		
		assertNotNull(app.mySim);
	}
}
