package FinalProject;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Robot {
	private HashMap<Integer, Stack<Point>> routes;
	private Stack<Point> currentPath;
	private String name;
	// current location
	private int col = 0;
	private int row = 0;
	
	public Robot(String name) {
		this.name = name;
		currentPath = new Stack<Point>();
		routes = new HashMap<Integer, Stack<Point>>();
	}
	
	public void findCavern(int cavernNumber) {
		Set keyset = routes.keySet();
		for (Object k : keyset) {
			if ((Integer) k == cavernNumber)
				for (Point p : routes.get(k)) {
					this.col = p.x;
					this.row = p.y;
				}
			else
				traverse((Integer) k);
		}
	}
	
	private int traverse(int cavernNumber) {
		return 0;
	}
	
	public Stack<Point> getRoute(int cavernNumber) {
		return routes.get(cavernNumber);
	}
	
	//FOR DEV ONLY
	public HashMap<Integer, Stack<Point>> getRoutes() {
		return routes;
	}
	
	//FOR DEV ONLY
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
}
