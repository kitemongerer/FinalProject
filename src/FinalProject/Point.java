package FinalProject;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Point extends JPanel{
	public int row;
	public int col;
	public boolean isEntrance = false;
	public PointType type = PointType.NONE;

	public Point(int row, int col, PointType type) {
		this.row = row;
		this.col = col;
		this.type = type;
	}

//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.setColor(Color.BLACK);
////		g.drawRect(row, col, row + Manager.POINT_SIZE, col + Manager.POINT_SIZE);
////		g.drawRect(row, col, row + 20, col + 20);
//		g.drawString("wow", 60, 60);
//		if (this.type == PointType.CAVERN) {
////		    g.drawString(Integer.toString(((CavernPoint) this).getCavernNumber()), row, col);
////		    g.drawString("wow", row, col);
//		}
//	}
}
