package edu.neu.csye6200.covid19_test;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import edu.neu.csye6200.covid19.MyCanvas;
import edu.neu.csye6200.covid19.PeopleGrid;

public class MyCanvasTest {
	
	@Test
    public void testConstructor() {
		MyCanvas mc = new MyCanvas();
		assertEquals(PeopleGrid.gridData[13][3].infectionSpread,-3);//Grid created with correct data.
		
	}
}
