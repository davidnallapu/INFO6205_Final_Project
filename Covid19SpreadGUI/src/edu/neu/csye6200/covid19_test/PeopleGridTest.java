package edu.neu.csye6200.covid19_test;


import org.junit.Test;


import static org.junit.Assert.assertEquals;

import edu.neu.csye6200.covid19.PeopleGrid;

public class PeopleGridTest {
	
	@Test
    public void testUpdateGrid() {
		PeopleGrid g = new PeopleGrid();
		g.makeGrid();
		int before1 = g.infectedPeople.size();
		PeopleGrid pg1 = new PeopleGrid();
		pg1.R =10;
		pg1.C = 10;
		
		g.gridData[pg1.R][pg1.C].infectionSpread =80; 
		
		g.updateGrid(pg1);
		
		assertEquals(g.infectedPeople.size(),before1+4);//Person not wearing a mask. Hence infected People should increase by 4
		
		int before2 = g.infectedPeople.size();
		PeopleGrid pg2 = new PeopleGrid();
		pg2.R =10;
		pg2.C = 12;
		
		g.gridData[pg2.R][pg2.C].infectionSpread =77;
		g.updateGrid(pg2);
		assertEquals(g.infectedPeople.size(),before2+2);//Person wearing a mask. Hence infected People should increase by 2 i.e 4+2=6
	}
	
	
	@Test
    public void testMakeGrid() {
		PeopleGrid pg = new PeopleGrid(10,10,80);
		pg.makeGrid();
		
	}
	
	
	@Test
    public void testGetSpread() {
		PeopleGrid g = new PeopleGrid();
		
		
		PeopleGrid pg1 = new PeopleGrid();
		pg1.R =12;
		pg1.C = 12;
		
		g.gridData[pg1.R][pg1.C].infectionSpread = 80; 
		
		g.updateGrid(pg1);
		
		PeopleGrid pg_q = new PeopleGrid();
		pg_q.R =13;
		pg_q.C = 13;
		
		g.gridData[pg_q.R][pg_q.C].infectionSpread = -1; //Quarantining.
		g.updateGrid(pg_q);
		g.spreadInfection();
		
		assertEquals(g.getSpread(),1);//Only 1 as the pg_q is quarantining.
}
	
}
