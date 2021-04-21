

package edu.neu.csye6200.covid19;

import java.util.ArrayList;

/**
 * NOTE: MySimulation is a Runnable class that starts a "thread". This parent "thread" in turn starts "threadVaccination" and "threadInfection".
 * This is done to make the oil and boat movement completely independent. 
 */
public class MySimulation implements Runnable{

	public static boolean paused = false;
	public static boolean done = false; // set true to end the simulation loop
	
	//Initialising the three threads with parent thread
	private Thread thread = null;
	private Thread threadInfection= null;
	private Thread threadVaccination= null;
	private long simDelay = 500L; // time adjustment to slow down the simulation loop
	private boolean running = false; // set true if the simulation is running
	
	private PeopleGrid og = new PeopleGrid();// Instantiating an object of Grid and ABARule
	private Vaccination abr = new Vaccination();

	public void tester() {
		setDone(true);
	}
	/**
	 * Method to start simulation
	 */
	public void startSim() {
		// Adding Observers to the canvas and South Panel objects.
		
		abr.addObserver(AppUI.canvas);
		abr.addObserver(AppUI.sp);
		
		if (thread != null) {
			if(paused) setPaused(false);
			System.out.println(paused);
			return;
		} // A thread is already running
		thread = new Thread(this); // Create a worker thread
		running = true;
		paused = false;
		done = false; // reset the done flag.

		thread.start();
		
	}
	
	/**
	 *Method to pause simulation
	 */
	public void pauseSim() {
			setPaused(!paused);
		}
	
	public boolean isPaused() {
		return MySimulation.paused;
	}
	
	public boolean isPausable() {
		if (!running) return false;
		if (done) return false;
		return true;
	}
	
	/**
	 * Is this simulation currently running?
	 * @return true if the simulation is active
	 */
	public boolean isRunning() {
		return running;
	}
	
	public void stopSim() {	
		if (thread == null) return; // defensive coding in case the thread is null

		setDone(true);
	}
	
	/**
	 * The main run method for this simulation.
	 */
	@Override
	public void run() {
		runSimLoop();
		thread = null; // flag that the simulation thread is finished
		threadVaccination = null;
		threadInfection = null;
	}
	
	/**
	 * A simulation loop that continuously runs
	 */
    private void runSimLoop() {
    	running = true;
    	while(!done) {
    		// do some simulation work
    		
    		if (!paused) updateSim();
    		sleep(simDelay); // A half second sleep is the default
    	}
    	running = false;
    }
	
    private void sleep(long millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
    /**
	 * Method to start threads and initialize the conditions 
	 */
	private void updateSim() {
		Vaccination.done=true;
		PeopleGrid.done=false;
		if(threadInfection == null && threadVaccination == null) {
			threadInfection = new Thread(og);
			threadVaccination = new Thread(abr);
			//Setting initial conditions again if the STOP button was clicked
			Vaccination.bt = new Vaccine("Cleaner", 0, 0, 90, "READY", 20, 0, 100, 0);

		}
		if(!threadInfection.isAlive() && !threadVaccination.isAlive()) {// Starts the two threads for Oil Spill and Boat movement
			threadInfection.start();
			threadVaccination.start();
		}
		}
	
	/**
	 * Method invoked when Stop is clicked 
	 */
	public void setDone(boolean done) {
		Vaccination.bt.setStatus("STOPPED");// Setting status to stopped
		MySimulation.done = done;
		Vaccination.done=false;
		PeopleGrid.done=true;
		
		//Setting initial conditions to RESTART the threads and Simulation
		PeopleGrid.infectedPeople = new ArrayList < PeopleGrid > (); 
		PeopleGrid.totalVirusParticles=80*400;
		Vaccination.flagLoop=1;
		PeopleGrid.totalInfected=0;
		Vaccination.totalVaccinated=0;
//		MyCanvas.masked =  Integer.parseInt(AppUI.masked.getText());
//		MyCanvas.quarantined =  Integer.parseInt(AppUI.quarantined.getText());
		new MyCanvas();
	}
	
	public void setPaused(boolean paused) {
		MySimulation.paused=paused;
	}
}
