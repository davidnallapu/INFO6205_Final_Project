

package edu.neu.csye6200.covid19;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private PeopleGrid og = new PeopleGrid();
	private PeopleGrid[][] grid;
	private BufferedImage img;
	private Vaccination myABRule;

	/**
	 * Constructor to add Oil and Land Mass
	 */
	public MyCanvas() {
		try {
			img = ImageIO.read(getClass().getResource("vaccine.jpeg"));// Loading image of the Vaccine
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Making the People Grid
		og.makeGrid();

		// Adding the first infected person
		PeopleGrid.gridData[PeopleGrid.gridWidth / 2 - 1][PeopleGrid.gridHeight / 2 - 1].infectionSpread = 0;
		PeopleGrid.totalVirusParticles -= 0;
		PeopleGrid.infectedPeople.add(PeopleGrid.gridData[PeopleGrid.gridWidth / 2 - 1][PeopleGrid.gridHeight / 2 - 1]);

		// Adding people who are quarantining 
		PeopleGrid.gridData[5][8].infectionSpread = -1;
		PeopleGrid.gridData[8][5].infectionSpread = -1;
		PeopleGrid.gridData[12][3].infectionSpread = -1;
		PeopleGrid.gridData[11][17].infectionSpread = -1;
		PeopleGrid.gridData[16][11].infectionSpread = -1;
		PeopleGrid.gridData[3][13].infectionSpread = -1;
		PeopleGrid.gridData[17][12].infectionSpread = -1;
		PeopleGrid.gridData[13][5].infectionSpread = -1;
		
		//Adding people with masks
		PeopleGrid.gridData[12][12].infectionSpread = -3;
		PeopleGrid.gridData[13][3].infectionSpread = -3;

		grid = PeopleGrid.gridData;
	}

	// Swing calls when a redraw is needed
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		drawCanvas(g);
	}

	// Draw the contents of the panel
	private void drawCanvas(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		g2d.fillRect(0, 0, size.width, size.height);
		int height = size.width / 20;
		int width = size.height / 20;
		drawOceanGrid(g2d, height, width);
	}

	/**
	 * Method to draw ocean grid.
	 */
	private void drawOceanGrid(Graphics2D g2d, int height, int width) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int startx = j * height;
				if (grid[i][j].R == Vaccination.bt.getPosX() && grid[i][j].C == Vaccination.bt.getPosY()
						&& PeopleGrid.infectedPeople.size() > 0) {
					if (img != null) {// In case image doesnt load due to directory change.
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.drawImage(img, startx, i * width, height - 2, width - 2, this);
					} else {
						g2d.setColor(Color.GREEN);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
					}
				} else {
					if (grid[i][j].infectionSpread == 80) { // If person is infected
						g2d.setColor(Color.BLACK);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.RED);
						g2d.fillOval(startx, i * width, height, width);
					} 
					 else if (grid[i][j].infectionSpread == 0) { // If person is not infected or wearing mask or quarantining
						 g2d.setColor(Color.BLACK);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.WHITE);
						g2d.fillOval(startx, i * width, height, width);
					} 
					 else if (grid[i][j].infectionSpread == -1) { // If quarantining person
						g2d.setColor(Color.BLACK);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.orange);
						g2d.fillOval(startx, i * width, height - 1, width - 1);
					}
					else if (grid[i][j].infectionSpread == -3) { // If person is wearing a mask
						g2d.setColor(Color.BLACK);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillOval(startx, i * width, height - 1, width - 1);
						
					}
					else if (grid[i][j].infectionSpread == 77) {//Someone with a mask getting infected
						g2d.setColor(Color.RED);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillOval(startx, i * width, height - 1, width - 1);	
					}
					
					else if (grid[i][j].infectionSpread == -10) {//Someone with a mask getting infected
						g2d.setColor(Color.BLACK);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.GREEN);
						g2d.fillOval(startx, i * width, height - 1, width - 1);	
					}
	
				}

			}

		}

	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof MySimulation) {
			myABRule = (Vaccination) arg;
		}

		repaint(); // Tell the GUI thread that it should schedule a paint() call
	}

}
