package FinalProject;

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
        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numCols; col++)
                g.drawRect(row, col, row + 20, col + 20);
    }
}
