package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;

import javax.swing.JPanel;

public class Mine extends JPanel {
    private Point[][] mine;
    private int numRows;
    private int numCols;
    
    public Mine(Point[][] mine, int numRows, int numCols) {
        this.mine = mine;
        this.numRows = numRows;
        this.numCols = numCols;
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.decode("#D2492A"));
        g.drawRect(0, 0, 20 * numRows, 20 * numCols);
        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numCols; col++) {
            	g.drawRect(col * 20, row * 20, 20, 20);
                if (mine[row][col].type == PointType.WALL)
                    g.fillRect(col * 20, row * 20, 20, 20);
            }
                
        g.setColor(Color.cyan);
        for(int row = 0; row < numRows; row++)
        	for(int col = 0; col < numCols; col++)
        		if (mine[row][col].type == PointType.CAVERN)
        			g.fillRect(col * 20, row * 20, 20, 20);
    }
}
