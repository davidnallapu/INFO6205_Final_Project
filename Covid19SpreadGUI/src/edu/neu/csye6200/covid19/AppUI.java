

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
import javax.swing.JTextField;

import net.java.dev.designgridlayout.DesignGridLayout;

public class AppUI extends Covid19App{

	private Logger log = Logger.getLogger(AppUI.class.getName());
	private JPanel northPanel;//Panel with Buttons and Combo Box
	public static JPanel southPanel; //Panel with Statistics
	private JButton startBtn;
	private JButton stopBtn;
	private JButton pauseBtn;
	public static JTextField quarantined;
	public static JTextField masked;
	
	private JComboBox<String> comboBox;
	public static MyCanvas canvas;
	public static SouthPanel sp;
	public MySimulation mySim = null;
	
	
	/**
	 * Constructor
	 */
	public AppUI() {
		log.info("Simulation  started");
		
		frame.setSize(600,600);
		frame.setTitle("Covid 19 Spread Simulation");
		
		
		initSim(); // Initialize the simulation

		showUI(); // Initialize the GUI
		
	}
	
	
	/**
	 * Initialise the simulation
	 */
	private void initSim() {
		MyCanvas.masked = Integer.parseInt(AppUI.masked.getText());
		MyCanvas.quarantined = Integer.parseInt(AppUI.quarantined.getText());
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
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("Covid-19(R0 = 4)");
		comboBox.addItem("SARS(R0 = 2)");
		
		comboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(comboBox.getSelectedItem() == "Covid-19(R0 = 4)")
			PeopleGrid.rfactor=4;
			else if(comboBox.getSelectedItem() == "SARS(R0 = 2)") {
				PeopleGrid.rfactor=2;
			}
		}
	});
		quarantined = new JTextField("5");
		quarantined.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				MyCanvas.quarantined =  Integer.parseInt(quarantined.getText());
				MySimulation.done=true;
				
			}});
		
		masked = new JTextField("5");
		masked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				MyCanvas.masked =  Integer.parseInt(masked.getText());
				MySimulation.done=true;
				
			}});
		
		pLayout.row().grid(new JLabel("Rule:")).add(comboBox);
		pLayout.row().grid(new JLabel("Quarantined:")).add(quarantined).grid(new JLabel("Masked:")).add(masked);

		//		pLayout.emptyRow();
//		pLayout.row().group(grid(new JLabel("Quarantined:")).add(quarantined));
//		pLayout.row().grid(new JLabel("Masked:")).add(masked);
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
            
            g.setColor(Color.yellow);
            g.drawString("Legend:",10, 27);
            
            g.setColor(Color.white);
            g.drawString("NOT INFECTED",80, 27);
            
            g.setColor(Color.red);
            g.drawString("INFECTED: ",200, 27);
            
            g.setColor(Color.blue);
            g.drawString("MASKED",280, 27);
            
            g.setColor(Color.orange);
            g.drawString("QUARANTINED",360, 27);
            
            g.setColor(Color.green);
            g.drawString("VACCINATED",480, 27);
            
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





