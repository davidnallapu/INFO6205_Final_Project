

package edu.neu.csye6200.covid19;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONObject;


public class Vaccination extends Observable implements Runnable {
    public static boolean done = false;
    public static Vaccine bt = new Vaccine("READY", 0, 0, 90, "READY", 20, 0, 100, 0);// Vaccine instance to track movement
    public static int flag = 0;
    public static int totalVaccinated = 0;

    /**
	 * Method to implement vaccination
	 */
    public void vaccination() {
        if (done) {
                int oldPosX = bt.getPosX();
                int oldPosY = bt.getPosY();
                int gridH = PeopleGrid.gridHeight - bt.getPosX();
                int gridW = PeopleGrid.gridWidth - bt.getPosY();
                
                //Moving East
                while (bt.getPosY() < gridH - 1 && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately//Pauses the Vaccination immediately
                    	pausedVaccination();
                    else {
                    	clean(bt.getPosX(), bt.getPosY());//Method call to check and vaccinate a person
                        bt.setPosY(bt.getPosY() + 1);
                    }
                }
                //Moving South
                while (bt.getPosX() < gridW - 1 && done) {
                    if (MySimulation.paused)//Pauses the Vaccination immediately
                    	pausedVaccination();
                    else {
                    	clean(bt.getPosX(), bt.getPosY());//Method call to check and vaccinate a person
                        bt.setPosX(bt.getPosX() + 1);
                    }
                }
                //Moving West
                while (bt.getPosY() > oldPosX && done) {
                    if (MySimulation.paused)//Pauses the Vaccination immediately
                    	pausedVaccination();
                    else {
                    	clean(bt.getPosX(), bt.getPosY());//Method call to check and vaccinate a person
                        bt.setPosY(bt.getPosY() - 1);
                    }
                }
                //Moving North
                while (bt.getPosX() > oldPosY && done) {
                    if (MySimulation.paused)//Pauses the Vaccination immediately
                    	pausedVaccination();
                    else {
                    	clean(bt.getPosX(), bt.getPosY());//Method call to check and vaccinate a person
                        bt.setPosX(bt.getPosX() - 1);
                    }
                }

                bt.moveTo(bt.getPosX() + 1, bt.getPosY() + 1);//Method call to update the Vaccination postion for the next loop
            }
        
    }
    
    public static int flagLoop = 1;

    /**
	 * Method to remove Oil from a OceanGrid box 
	 */
    public void clean(int newPosX, int newPosY) {
        bt.setStatus("VACCINATE");// Updates status  to VACCINATING. Displays on South Panel. 
        bt.setTotalOil(bt.getTotalOil() + PeopleGrid.gridData[newPosX][newPosY].infectionSpread);//Updates the variable that tracks Vaccination
        PeopleGrid.gridData[newPosX][newPosY].infectionSpread = -10;//Vaccinates them
        PeopleGrid.gridData[newPosX][newPosY].vaccinated = true; //Marked as vaccinaated
        PeopleGrid.infectedPeople.remove(PeopleGrid.gridData[newPosX][newPosY]);     
        totalVaccinated+=1;
        if(totalVaccinated==400) {
        	try {
        		JSONObject json = new JSONObject();
        		JSONObject summary = new JSONObject();
        		summary.put("population",400);
        		summary.put("rfactor",PeopleGrid.rfactor);
        		summary.put("totalInfected", PeopleGrid.totalInfected);
        		summary.put("quarantined", Integer.parseInt(AppUI.quarantined.getText()));
        		summary.put("masked", Integer.parseInt(AppUI.masked.getText()));
        		json.put("summary", summary);
        		json.put("data", PeopleGrid.jsonArr);
        		FileWriter file = new FileWriter("data/sars_3.json");                
        		file.write(json.toString());
        		file.close();
             } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
             }
        	
        }
        updateCanvas();//Updates the canvas for smooth movement of Boat. 
        timeSpeedDelay();
    }

    /**
	 * Method to sleep based on Boat speed.
	 */
    public void timeSpeedDelay() {
        try {
            Thread.sleep((int)(3000 / bt.getSpeed()));
        } catch (Exception e) {};
    }

    /**
	 * Method to pause the boat and change STATUS in GUI
	 */
    public void pausedVaccination() {
        bt.setStatus("PAUSED");  // Updates status of Vaccination to PAUSED. Displays on South Panel.  
        updateCanvas();//Updates the canvas for smooth movement of Vaccination
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Perform an update on the simulation
     */
    private void updateCanvas(){
    	setChanged();
    	notifyObservers(this); // Sends a copy of the simulation
    }
    
    /**
	 * run method for Thread
	 */
    public void run() {
        while (done) {
            if (MySimulation.paused)//Pauses  immediately
            	pausedVaccination();
            else {
            	vaccination();
            }
        }
        
    }
}