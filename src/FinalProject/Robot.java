package FinalProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Robot {
	private HashMap<Integer, ArrayList<Point>> routes;
	private Stack<Point> currentPath;
	private String name;
	
	public Robot(String name) {
		this.name = name;
		currentPath = new Stack<Point>();
		routes = new HashMap<Integer, ArrayList<Point>>();
	}
	
	public void findCavern(int cavernNumber) {
		
	}
	
	private int traverse(int cavernNumber) {
		return 0;
	}
}
