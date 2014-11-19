package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;

import javax.swing.JPanel;

public class Mine extends JPanel {
    private Point[][] mine;
    private int numRows;
    private int numCols;
    public static final int CELL_SIZE = 20;
    
    public Mine(Point[][] mine, int numRows, int numCols) {
        this.mine = mine;
        this.numRows = numRows;
        this.numCols = numCols;
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.decode("#D2492A"));
        g.drawRect(0, 0, CELL_SIZE * numRows, CELL_SIZE * numCols);
        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numCols; col++) {
            	g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (mine[row][col].type == PointType.WALL)
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
                
        g.setColor(Color.cyan);
        for(int row = 0; row < numRows; row++)
        	for(int col = 0; col < numCols; col++)
        		if (mine[row][col].type == PointType.CAVERN)
        			g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
}
