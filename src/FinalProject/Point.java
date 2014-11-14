package FinalProject;

public class Point {
	public int row;
	public int col;
	public boolean isEntrance = false;
	public PointType type = PointType.NONE;
	
	public Point(int row, int col, PointType type) {
		this.row = row;
		this.col = col;
		this.type = type;
	}
}
