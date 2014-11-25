package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Mine extends JPanel {
    private Point[][] mine;
    private int numRows;
    private int numCols;
    private ArrayList<Robot> queue;
    
    public Mine(Point[][] mine, ArrayList<Robot> queue, int numRows, int numCols) {
        this.mine = mine;
        this.numRows = numRows;
        this.numCols = numCols;
        this.queue = queue;
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        g.setColor(Color.decode("#D2492A"));
        g.drawRect(0, 0, Manager.POINT_SIZE * numRows, Manager.POINT_SIZE * numCols);
        for (int row = 0; row < numRows; row++) {
        	for (int col = 0; col < numCols; col++) {
        		g.drawRect(col * Manager.POINT_SIZE, row * Manager.POINT_SIZE, Manager.POINT_SIZE, Manager.POINT_SIZE);
        		if (mine[row][col].type == PointType.WALL)
        			g.fillRect(col * Manager.POINT_SIZE, row * Manager.POINT_SIZE, Manager.POINT_SIZE, Manager.POINT_SIZE);
        	}
        } 
        
        for (int row = 0; row < numRows; row++)
        	for (int col = 0; col < numCols; col++)
        		if (mine[row][col].type == PointType.CAVERN) {
        		    g.setColor(Color.cyan);
        			g.fillRect(col * Manager.POINT_SIZE, row * Manager.POINT_SIZE, Manager.POINT_SIZE, Manager.POINT_SIZE);
        			g.setColor(Color.BLACK);
        			g.drawString(Integer.toString(((CavernPoint) mine[row][col]).getCavernNumber()), col * Manager.POINT_SIZE + 8, row * Manager.POINT_SIZE + Manager.POINT_SIZE - 8);
        		}
        
        for (Robot robot : queue) {
        	robot.draw(g);
        }
    }
}
