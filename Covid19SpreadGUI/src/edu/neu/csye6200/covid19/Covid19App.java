
package edu.neu.csye6200.covid19;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;


public abstract class Covid19App implements ActionListener {

	protected JFrame frame;
	
	public Covid19App() {
		System.out.println("Covid19App constructor starting");
		initGUI();
	}
	

	/**
	 * Initialize the Graphical User Interface
	 */
    public void initGUI() {
    	frame = new JFrame();
		frame.setTitle("Covid19App");

		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)
		
		// Permit the app to hear about the window opening
		//frame.addWindowListener(this); 
		
		
		
		frame.setLayout(new BorderLayout());
		frame.add(getNorthPanel(), BorderLayout.NORTH);
		frame.add(getCenterPanel(), BorderLayout.CENTER);
		frame.add(getSouthPanel(), BorderLayout.SOUTH);
    }
 
    
    /**
     * Override this method to provide the control panel panel.
     * @return a JPanel, which contains the north content of of your application
     */
    public abstract JPanel getNorthPanel();
    
    /**
     * Override this method to provide the main content panel.
     * @return a JPanel, which contains the main content of of your application
     */
    public abstract JPanel getCenterPanel();

    /**
     * Override this method to provide the statistics content panel.
     * @return a JPanel, which contains the statistics content of of your application
     */
    public abstract JPanel getSouthPanel();

    /**
     * A convenience method that uses the Swing dispatch threat to show the UI.
     * This prevents concurrency problems during component initialization.
     */
    public void showUI() {
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	frame.setVisible(true); // The UI is built, so display it;
            }
        });
    	
    }
    
    /**
     * Shut down the application
     */
    public void exit() {
    	frame.dispose();
    	System.exit(0);
    }

    /**
     * Override this method to show an About Dialog
     */
    public void showHelp() {
    }
 
}

