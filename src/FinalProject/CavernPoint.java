package FinalProject;

import java.awt.Graphics;

import javax.swing.JPanel;

public class CavernPoint extends Point {
	private int cavernNumber;

	public CavernPoint(int row, int col, PointType type, String id) {
		super(row, col, type);
		cavernNumber = Integer.valueOf(id.substring(1));
	}
	
	public int getCavernNumber() {
		return cavernNumber;
	}
	

}
