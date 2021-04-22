

package edu.neu.csye6200.covid19;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    int sequence;
    public static boolean done = false;
    public static int totalVirusParticles = 80*50*50;
    public static int totalInfected = 0;
    public static int infected_ts =0;
    public static int timeStep = 0;
    public static JSONObject jsonObject = new JSONObject();
    public static JSONArray jsonArr = new JSONArray();
    public static int sequence_count = 0;
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

    
    public static int gridWidth = 50;
    public static int gridHeight = 50;
    public static PeopleGrid gridData[][] = new PeopleGrid[gridHeight][gridWidth];//2D array of  objects 
    public static ArrayList < PeopleGrid > infectedPeople = new ArrayList < PeopleGrid > ();//Keeps track of all the active objects which can spread

    
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

//        System.out.println("before size"+infectedPeople.size());
        int sizer = infectedPeople.size();
        for(int i =0; i<sizer ;i++) {
//        	System.out.println("i"+ i+" "+sizer);
        	if(infectedPeople.get(i).vaccinated==false && infectedPeople.get(i).infectionSpread!=80 && infectedPeople.get(i).infectionSpread!=77) {
                if (infectedPeople.get(i).infectionSpread == -3) {// Checks if a land mass and updates to -2 for graphics and decreases by 20
                    {infectedPeople.get(i).infectionSpread += 80;
                    totalVirusParticles -= 80;  
                    totalInfected+=updateGrid(infectedPeople.get(i));
                			}
                    }
                    
                 else if (infectedPeople.get(i).infectionSpread ==0) {
                	infectedPeople.get(i).infectionSpread += 80;
                    totalVirusParticles -= 80;
                    totalInfected+=updateGrid(infectedPeople.get(i));
                    }
                }
        	try {
                Thread.sleep(1);
            } catch (Exception e) {};
        }
        addToJson();
    }


    static int before;
    public static void addToJson() {
    	if(totalInfected==before) return;
    	JSONObject record = new JSONObject();
    	record.put("time", Integer.toString(timeStep));
    	record.put("value", Integer.toString(totalInfected));
    	jsonArr.put(record);
        timeStep++;
        before=totalInfected;
        System.out.println("time "+Integer.toString(timeStep)+ " value"+Integer.toString(totalInfected));
    }

    /**
	 * Method to update the Border Oil ArrayList and adding points for next iteration 
	 */
    public static int rfactor=4;//Covid default
    public int updateGrid(PeopleGrid gb) {
    	
    	int rfactor_f = -1;
    	Random rand2 = new Random();
    	if(gridData[gb.R][gb.C].infectionSpread == 77) {//This is someone with a mask. Rfactor decresases due to mask to 3
    		rfactor_f = 1;
    		
    		
    		
    	}
    	else if(gridData[gb.R][gb.C].infectionSpread == 80){
    		rfactor_f = 2;//This is someone without a mask. Rfactor increases due to mask to 5
    		 
    	}
    	else {
    		rfactor_f = 0; //This is someone quarantining. Rfactor is 0
    	}
    	
    		ArrayList<Integer> temp_list = new ArrayList<Integer>();
//    		System.out.println(rfactor_f);
    		int final_r = 0;
    		while(rfactor_f>0 && gridData[gb.R][gb.C].infectionSpread!=-1) {

    			Random rand = new Random(); 
    			int temp = rand.nextInt(8);
    			if(!temp_list.contains(temp)) {
    				temp_list.add(temp);
    				rfactor_f--;
    				if(temp == 0) {
    					
    					//Check W and infectiousSpread=0 and not Quarantining
    					if (gb.R <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 && gb.R >-1 && gb.C - 1>-1 && gridData[gb.R][gb.C - 1].infectionSpread==0|| gridData[gb.R][gb.C - 1].infectionSpread==-3)
    					{
    						
    						infectedPeople.add(gridData[gb.R][gb.C - 1]);
    			            infected_ts++;
    			            final_r++;
    	    			
    					}
    				}
    				if(temp == 1) {
    					//Check NW and infectiousSpread=0 and not Quarantining
    			        if (gb.R - 1 <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 && gb.R + 1>-1 && gb.C - 1>-1 && gridData[gb.R - 1][gb.C - 1].infectionSpread==0|| gridData[gb.R - 1][gb.C - 1].infectionSpread==-3)
    			            {
    			        	infectedPeople.add(gridData[gb.R - 1][gb.C - 1]);

	    			        infected_ts++;final_r++;}
    			        
    					
    				}
    				if(temp == 2) {
    					//Check N and infectiousSpread=0 and not Quarantining
    					if ( gb.R - 1 <= gridHeight - 1 && gb.C <= gridWidth - 1 && gb.R - 1>-1 && gb.C >-1 && gridData[gb.R - 1][gb.C].infectionSpread==0|| gridData[gb.R - 1][gb.C].infectionSpread==-3 )
    			            {
    						infectedPeople.add(gridData[gb.R - 1][gb.C]);

	    					infected_ts++;final_r++;}
    					
    				}
    				if(temp == 3) {
    					//Check NE  and infectiousSpread=0 and not Quarantining
    			        if ( gb.R - 1 <= gridHeight - 1 && gb.C + 1 <= gridWidth && gb.R - 1>-1 && gb.C + 1>-1 && gridData[gb.R - 1][gb.C + 1].infectionSpread==0|| gridData[gb.R - 1][gb.C + 1].infectionSpread==-3)
    			            {

    			        	infectedPeople.add(gridData[gb.R - 1][gb.C + 1]);
	    			        infected_ts++;final_r++;}
    					
    				}
    				if(temp == 4) {
    					//Check E and infectiousSpread=0 and not Quarantining

    			        if (gb.R <= gridHeight - 1 && gb.C + 1 <= gridWidth -1 && gb.R >-1 && gb.C + 1>-1 && gridData[gb.R][gb.C + 1].infectionSpread==0|| gridData[gb.R][gb.C + 1].infectionSpread==-3)
    			            {
    			        	infectedPeople.add(gridData[gb.R][gb.C + 1]);
	    			        infected_ts++;final_r++;}
    					
    				}
    				if(temp == 5) {
    					//Check SE and infectiousSpread=0 and not Quarantining
    					if ( gb.R + 1 <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1 && gb.R + 1>-1 && gb.C + 1>-1 && gridData[gb.R + 1][gb.C + 1].infectionSpread==0|| gridData[gb.R + 1][gb.C + 1].infectionSpread==-3)
    			            {
    						infectedPeople.add(gridData[gb.R + 1][gb.C + 1]);
	    					infected_ts++;final_r++;}
    					
    				}
    				if(temp == 6) {
    					//Check S and infectiousSpread=0 and not Quarantining
    			        if ( gb.R + 1 <= gridHeight - 1 && gb.C <= gridWidth - 1 && gb.R + 1>-1 && gb.C >-1 && gridData[gb.R + 1][gb.C].infectionSpread==0|| gridData[gb.R + 1][gb.C].infectionSpread==-3)
    			            {
    			        	infectedPeople.add(gridData[gb.R + 1][gb.C]);
	    			        infected_ts++;final_r++;}
    					
    				}
    				if(temp == 7) {
    					//Check and infectiousSpread=0 and not Quarantining
    			        if ( gb.R + 1 <= gridHeight - 2 && gb.C - 1 <= gridWidth - 1 && gb.R + 1>-1 && gb.C - 1>-1 && gridData[gb.R + 1][gb.C - 1].infectionSpread==0|| gridData[gb.R + 1][gb.C - 1].infectionSpread==-3)
    			            {
    			        	infectedPeople.add(gridData[gb.R + 1][gb.C - 1]);
	    			        infected_ts++;final_r++;}
    				}
    			
    		}

    	}
//    		for (PeopleGrid gb2: infectedPeople) {
//    			System.out.println(gb2.R+ " "+ gb2.C+" "+gb2.sequence+" "+gb2.infectionSpread);
//    		}
    		
    		
    		
//    		System.out.println("sorted");
//    		for (PeopleGrid gb2: infectedPeople) {
//    			System.out.println(gb2.R+ " "+ gb2.C+" "+gb2.sequence+" "+gb2.infectionSpread);
//    		}
    		
//    		Collections.sort(infectedPeople_1, PeopleGrid.StuRollno);
    		
    		return final_r;

    }
    
 // Comparator for sorting the list by roll no
    public static Comparator<PeopleGrid> StuRollno = new Comparator<PeopleGrid>() {
              public int compare(PeopleGrid s1, PeopleGrid s2)
              {
  
                  int rollno1 = 25-s1.R;
                  int rollno2 = 25-s2.R;
                  
                  int rollno3 = 25-s1.C;
                  int rollno4 = 25-s2.C;
  
                  int value1 = rollno1*rollno3;
                  int value2 = rollno2*rollno4;
                  
                  
                  
                  if(value1 <0) {
                	  value1=value1-value1*2;
                	  
                  }
                  
                  if(value2 <0) {
                	  value2=value2-value2*2;
                	  
                  }
                  // For ascending order
                  return value1 - value2;
  
                  // For descending order
                  // rollno2-rollno1;
              }
          };

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