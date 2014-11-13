package FinalProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Robot {
	private HashMap<Integer, Stack<Point>> routes;
	private Stack<Point> currentPath;
	private String name;
	
	public Robot(String name) {
		this.name = name;
		currentPath = new Stack<Point>();
		routes = new HashMap<Integer, Stack<Point>>();
	}
	
	public void findCavern(int cavernNumber) {
		
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
}
