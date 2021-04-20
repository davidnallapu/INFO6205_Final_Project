

package edu.neu.csye6200.covid19;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * NOTE: I have fixed the amount of oil that spills to 10000. I'm Assuming the leaking pipe has
 * been fixed after this.
 * Also,  
 * Land Mass = -1
 * Land Mass with Oil Spilled = -2(Total Oil decreased by 20)
 * This has been done to ease programming in the canvas class.
 */
public class PeopleGrid implements Runnable {
    public int R;
    public int C;
    public int infectionSpread;
    boolean vaccinated;
    public static boolean done = false;
    public static int totalVirusParticles = 80*400;
    public static int totalInfected = 0;
    public static int infected_ts =0;
    public static int timeStep = 0;
    public static JSONObject jsonObject = new JSONObject();
    public static JSONArray jsonArr = new JSONArray();
    /**
	 * Default constructor 
	 */
    public PeopleGrid() {
    	//Creating a JSONObject object
    }

    
    /**
	 * Parameterized constructor 
	 */
    public PeopleGrid(int R, int C, int infectionSpread) {
        this.R = R;
        this.C = C;
        this.infectionSpread = infectionSpread;
        this.vaccinated =false;
    }

    
    public static int gridWidth = 20;
    public static int gridHeight = 20;
    public static PeopleGrid gridData[][] = new PeopleGrid[gridHeight][gridWidth];//2D array of OceanGrid objects 
    public static ArrayList < PeopleGrid > infectedPeople = new ArrayList < PeopleGrid > ();//Keeps track of all the active objects which can spread Oil

    
    /**
	 * Method to initialise the grid with all 0 values in oilSpread variable
	 */
    public void makeGrid() {
    	
        for (int i = 0; i < gridHeight; i++) {
            PeopleGrid gridRow[] = new PeopleGrid[gridHeight];
            for (int j = 0; j < gridWidth; j++) {
                gridRow[j] = new PeopleGrid(i, j, 0);
            }
            gridData[i] = gridRow;
        }
    }

    
    /**
	 * Method with the logic to spread the virus
	 */
    public void spreadInfection() {
        if (done = false) 
        	{

        	
        	return;}

        if (totalVirusParticles <= 0) return;
        
        for (PeopleGrid gb: infectedPeople) {
        	
        	if(gb.vaccinated==false) {
            if (gb.infectionSpread == -3) {// Checks if a land mass and updates to -2 for graphics and decreases by 20
                {gb.infectionSpread += 80;
                totalVirusParticles -= 80;  
                if (gb.infectionSpread == 77) {
                    updateGrid(gb);
                    totalInfected+=1;
                    infectedPeople.remove(gb);// Removes if Spread is 100%
                    break;
                }
                }
            } else if (gb.infectionSpread < 77 && gb.infectionSpread >= 0 && totalVirusParticles >= 20) {
                gb.infectionSpread += 80;
                totalVirusParticles -= 80;
                if (gb.infectionSpread == 80) {
                    updateGrid(gb);
                    totalInfected+=1;
                    infectedPeople.remove(gb);// Removes if infected
                    break;
                }
            }
        }
        	
        	
        	}
        try {
            Thread.sleep(100);
        } catch (Exception e) {};
    }


    /**
	 * Method to update the Border Oil ArrayList and adding points for next iteration 
	 */
    public static int rfactor=4;//Covid default
    public void updateGrid(PeopleGrid gb) {
//    	System.out.println("Time Step : "+timeStep+" Infected: "+infected_ts);
    	System.out.println(rfactor);
    	JSONObject record = new JSONObject();
    	record.put("time", Integer.toString(timeStep));
    	record.put("value", Integer.toString(infected_ts));
    	jsonArr.put(record);
        timeStep++;
    	int rfactor_f = -1;
    	if(gridData[gb.R][gb.C].infectionSpread == 77) {//This is someone with a mask. Rfactor decresases due to mask to 3
    		rfactor_f = rfactor/2;
    		
    	}
    	else if(gridData[gb.R][gb.C].infectionSpread == 80){
    		rfactor_f = rfactor;//This is someone without a mask. Rfactor increases due to mask to 5
    		 
    	}
    	else {
    		rfactor_f = 0; //This is someone quarantining. Rfactor is 0
    	}
    		ArrayList<Integer> temp_list = new ArrayList<Integer>();
    		
    		while(rfactor_f>0) {

    			Random rand = new Random(); 
    			int temp = rand.nextInt(8);
    			if(!temp_list.contains(temp)) {    				
    				if(temp == 0) {
    					//Check W and infectiousSpread=0 and not Quarantining
    					if (gb.R <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 )
    					{
    						infectedPeople.add(gridData[gb.R][gb.C - 1]);
  
    			            temp_list.add(temp);
    			            rfactor_f--;
    			            infected_ts++;
    	    			
    					}
    				}
    				if(temp == 1) {
    					//Check NW and infectiousSpread=0 and not Quarantining
    			        if (gb.R - 1 <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 )
    			            {infectedPeople.add(gridData[gb.R - 1][gb.C - 1]);
    			           
	    			        temp_list.add(temp);
	    			        rfactor_f--;
	    			        infected_ts++;}
    			        
    					
    				}
    				if(temp == 2) {
    					//Check N and infectiousSpread=0 and not Quarantining
    					if ( gb.R - 1 <= gridHeight - 1 && gb.C <= gridWidth - 1 )
    			            {infectedPeople.add(gridData[gb.R - 1][gb.C]);
    			            
	    					temp_list.add(temp);
	    					rfactor_f--;
	    					infected_ts++;}
    					
    				}
    				if(temp == 3) {
    					//Check NE  and infectiousSpread=0 and not Quarantining
    			        if ( gb.R - 1 <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1)
    			            {infectedPeople.add(gridData[gb.R - 1][gb.C + 1]);
    			            
	    			        temp_list.add(temp);
	    			        rfactor_f--;
	    			        infected_ts++;}
    					
    				}
    				if(temp == 4) {
    					//Check E and infectiousSpread=0 and not Quarantining
    			        if (gb.R <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1)
    			            {infectedPeople.add(gridData[gb.R][gb.C + 1]);
    			            
	    			        temp_list.add(temp);
	    			        rfactor_f--;
	    			        infected_ts++;}
    					
    				}
    				if(temp == 5) {
    					//Check SE and infectiousSpread=0 and not Quarantining
    					if ( gb.R + 1 <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1)
    			            {infectedPeople.add(gridData[gb.R + 1][gb.C + 1]);
    			            
	    					temp_list.add(temp);
	    					rfactor_f--;
	    					infected_ts++;}
    					
    				}
    				if(temp == 6) {
    					//Check S and infectiousSpread=0 and not Quarantining
    			        if ( gb.R + 1 <= gridHeight - 1 && gb.C <= gridWidth - 1)
    			            {infectedPeople.add(gridData[gb.R + 1][gb.C]);
    			            
	    			        temp_list.add(temp);
	    			        rfactor_f--;
	    			        infected_ts++;}
    					
    				}
    				if(temp == 7) {
    					//Check and infectiousSpread=0 and not Quarantining
    			        if ( gb.R + 1 <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1)
    			            {infectedPeople.add(gridData[gb.R + 1][gb.C - 1]);
    			            
	    			        temp_list.add(temp);
	    			        rfactor_f--;
	    			        infected_ts++;}
    				}
//    				if(gridData[gb.R][gb.C].infectionSpread == 77)
//    				{System.out.println("rfactor "+rfactor);
//    				System.out.println("temp "+ temp);
//    				System.out.println();}

    			
    			
    			
    		}

    	}

    }

    /**
	 * Method to return amount of  that has spilled 
	 */
    public static int getSpread() {
        return totalInfected;
    }

    @Override
    public void run() {
        while (!done) {
            if (MySimulation.paused)
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            else {
            	spreadInfection();

            }
        }
    }


}