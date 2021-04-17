

package edu.neu.csye6200.covid19;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.java.dev.designgridlayout.DesignGridLayout;

public class AppUI extends Covid19App{

	private Logger log = Logger.getLogger(AppUI.class.getName());
	private JPanel northPanel;//Panel with Buttons and Combo Box
	public static JPanel southPanel; //Panel with Statistics
	private JButton startBtn;
	private JButton stopBtn;
	private JButton pauseBtn;
	
	
	private JComboBox<String> comboBox;
	public static MyCanvas canvas;
	public static SouthPanel sp;
	private MySimulation mySim = null;
	
	/**
	 * Constructor
	 */
	public AppUI() {
		log.info("Simulation  started");
		
		frame.setSize(600,600);
		frame.setTitle("Covid 19 Spread Simulaqtion");
		
		
		initSim(); // Initialize the simulation

		showUI(); // Initialize the GUI
		
	}
	
	
	/**
	 * Initialise the simulation
	 */
	private void initSim() {
		mySim = new MySimulation();
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	  new AppUI();
      System.out.println("MyAppIO is exiting !!!!!!!!!!!!!!");
	}

	@Override
	public JPanel getNorthPanel() {
		northPanel = new JPanel();
		//Custom Lbrary for a better view 
		DesignGridLayout pLayout = new DesignGridLayout(northPanel);
		
		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mySim.startSim();
			}
		});
		
		pauseBtn = new JButton("Pause");
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mySim.pauseSim();
			}
		});
		
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mySim.stopSim();
			}
		});
		
//		comboBox = new JComboBox<String>();
//		comboBox.addItem("Rule 1");
//		comboBox.addItem("Rule 2");
//		comboBox.addItem("Rule 3");
//		
//		comboBox.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			if(comboBox.getSelectedItem() == "Rule 1")
//				Vaccination.boatRule=1;
//			else if(comboBox.getSelectedItem() == "Rule 2") {
//				Vaccination.boatRule=2;
//			}
//			else if(comboBox.getSelectedItem() == "Rule 3") {
//				Vaccination.boatRule=3;
//			}
//		}
		
//	});
//		pLayout.row().grid(new JLabel("Rule:")).add(comboBox);
		pLayout.emptyRow();
		pLayout.row().center().add(startBtn, pauseBtn, stopBtn);
		northPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
		northPanel.setBackground(new Color(76, 119, 153));
		
		return northPanel;
	}


	@Override
	public JPanel getCenterPanel() {
		canvas = new MyCanvas();
		return canvas;
	}
	
	@Override
	public JPanel getSouthPanel() {
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		sp = new SouthPanel();
		southPanel.add(sp);
		southPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
		southPanel.setBackground(new Color(76, 119, 153));
		return southPanel;// Returns the Panel with all Oil and Boat Statistics 
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	/**
	 * Inner Class for the South Panel
	 */
	public class SouthPanel extends JComponent implements Observer {

		private Vaccination myABRule;

		/**
		 * Constructor forStats Graphics displayed on southPanel
		 */
		SouthPanel() {
			setPreferredSize(new Dimension(600,50));
        }

        @Override
        public void paintComponent(Graphics g) {//Gets the statistics from ABRule as an Observable and repaints 
            super.paintComponent(g);
            //Statistics include Oil Spread, Oil Cleanup, Boat status, speed, Load, battery
            g.setColor(Color.YELLOW);
            g.drawString("Total Infected before vaccination: "+Double.toString(PeopleGrid.getSpread()),10, 10);
            g.drawString("Total Vaccinated: "+Double.toString(Vaccination.totalVaccinated),310, 10);
            g.setColor(Color.white);
            g.drawString("Vaccine Details: ",10, 27);
            g.setColor(Color.YELLOW);
            g.drawString("Status: "+Vaccination.bt.getStatus(),10, 45);
 
        }
        
        @Override
    	public void update(Observable o, Object arg) {
    		
    		if(arg instanceof MySimulation) {
    			myABRule = (Vaccination) arg;
    		}
    		repaint(); // Tell the GUI thread that it should schedule a paint() call
    	}
	}

	
}





