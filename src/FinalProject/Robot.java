package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Robot {
	
	//For all robots, they cannot share their data of routes for the others. 
	private HashMap<Integer, ArrayList<Point>> routes;
	private Stack<Point> currentPath;
	private String name;
	private String color;
	private Point[][] mine;
	private boolean[][] ifVisited;
	private boolean[] cavernVisited;
	private boolean inQueue;
	//FOR DEV ONLY
	int numVisited = 0;
	int numberOfCaverns = 4;
	
	private boolean found;
	
	// numOf Col and row for mine;
	private int numCols;
	private int numRows;
	
	// current location
	private int curCol = 0;
	private int curRow = 0;
	
	// Entrance location
	private int entranceCol;
	private int entranceRow;
	
	private int queuePosition;
	
	private Mine m;
	
	public Robot(String name, String color, Point[][] mine, Mine m, int numRows, int numCols) {
		this.name = name;
		this.color = color;
		routes = new HashMap<Integer, ArrayList<Point>>();
		this.mine = mine;
		this.numRows = numRows;
		this.numCols = numCols; 
		this.m = m;
		inQueue = true;
		
		ifVisited = new boolean[numRows][numCols];
		cavernVisited = new boolean[numberOfCaverns];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (mine[row][col].isEntrance){
					entranceCol = col;
					entranceRow = row;
					break;
				}
			}
		}
	}
	
	public void findCavern(int cavernNumber) {
		inQueue = false;
		boolean alreadyFound = false;
		for (Object k : routes.keySet()) {
			if ((Integer) k == cavernNumber) { 
				alreadyFound = true;
				navigateKnownPath(routes.get(k));
			}
		}

		if (!alreadyFound) {					
			//found is for if this cavern is found or not in the recursive function
			found = false;
			currentPath = new Stack<Point>();
			
			//Start traverse at entrance
			traverse(cavernNumber, entranceRow, entranceCol);
		}
		inQueue = true;
	}
	
	private void navigateKnownPath(ArrayList<Point> route) {
		for(Point point : route) {
			curRow = point.row;
			curCol = point.col;
			//TODO redraw robot
			//TODO add pause so user can see changes
		}
		
	}

	//the recursive function
	private void traverse(int cavernNumber, int row, int col) {
		//if the cavern is not found, then do the recursive part
		if (!found){
			//first, we need set the current point to be visited. That means ifVistied is true
			ifVisited[row][col] = true;
			
			//Set robot location for the GUI
			curRow = row;
			curCol = col;
			//TODO add GUI functionality to update GUI each time the robot moves
			m.repaint();
			//TODO add pause so user can see robot's movement
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			currentPath.add(mine[row][col]);

			// Check up, down, left, and right
			for (int r = -1; r < 2; r++){
				for (int c = -1; c < 2; c++){

					// Don't check diagonals
					if (Math.abs(r) != Math.abs(c)){

						//Check that the space is in the mine
						if (row + r >= 0 && row + r < numRows && col + c >=0 && col + c < numCols){

							//Check that we haven't already been to this space
							if (!ifVisited[row + r][col + c]){

								//Check that it is a Path
								if (mine[row + r][col + c].type.equals(PointType.PATH)){
									//If it is a path, traverse it
									traverse(cavernNumber, row + r, col + c);
									
								//Or check that it is a cavern
								} else if (mine[row + r][col + c].type.equals(PointType.CAVERN)){
									//Check that we haven't already been to the cavern
									if (!cavernVisited[((CavernPoint) mine[row + r][col + c]).getCavernNumber() - 1]){
										cavernVisited[((CavernPoint) mine[row + r][col + c]).getCavernNumber() - 1] = true;
										currentPath.add(mine[row + r][col + c]);
										
										//Build path to cavern into an ArrayList and add to routes
										ArrayList<Point> temp = new ArrayList<Point>();
										for(Point p : currentPath) {
											temp.add(p);
										}
										routes.put(((CavernPoint) mine[row + r][col + c]).getCavernNumber(), temp);

										//If we found the cavern we were sent to set found to true
										if (((CavernPoint) mine[row + r][col + c]).getCavernNumber() == cavernNumber) 
											found = true;
										currentPath.pop();
										
									}
								}
							}
						}
					}
				}
			}

			//We need set the current point is not visited and pop the last point out of the Currentpath if the
			//cavern is not found. 
			ifVisited[row][col] = false;
			currentPath.pop();
			
			if (!currentPath.empty()) {
				curRow = currentPath.peek().row;
				curCol = currentPath.peek().col;
				m.repaint();
				//TODO add pause so user can see robot's movement
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
	}
	
	public void draw(Graphics g) {
		if (inQueue) {
			drawInQueue(g);
		} else {
			g.setColor(Color.decode(this.color));
			Graphics2D g2d = (Graphics2D) g;
			// Assume x, y, and diameter are instance variables.
			Ellipse2D.Double circle = new Ellipse2D.Double(Manager.POINT_SIZE * curCol, Manager.POINT_SIZE * curRow, Manager.POINT_SIZE, Manager.POINT_SIZE);
			g2d.fill(circle);
			g.setColor(Color.BLACK);
			g.drawOval(Manager.POINT_SIZE * curCol, Manager.POINT_SIZE * curRow, Manager.POINT_SIZE, Manager.POINT_SIZE);
		}
	}
	
	private void drawInQueue(Graphics g) {
		g.setColor(Color.decode(this.color));
		Graphics2D g2d = (Graphics2D) g;
		// Assume x, y, and diameter are instance variables.
		Ellipse2D.Double circle = new Ellipse2D.Double(Manager.QUEUE_HORIZONTAL + queuePosition * 2 * Manager.POINT_SIZE, Manager.QUEUE_VERTICAL, Manager.POINT_SIZE, Manager.POINT_SIZE);
		g2d.fill(circle);
		g.setColor(Color.BLACK);
		g.drawOval(Manager.QUEUE_HORIZONTAL + queuePosition * 2 * Manager.POINT_SIZE, Manager.QUEUE_VERTICAL, Manager.POINT_SIZE, Manager.POINT_SIZE);
	}

	public ArrayList<Point> getRoute(int cavernNumber) {
		return routes.get(cavernNumber);
	}
	
	//FOR DEV ONLY
	public HashMap<Integer, ArrayList<Point>> getRoutes() {
		return routes;
	}
	
	//FOR DEV ONLY
	public int getCol() {
		return curCol;
	}
	
	public int getRow() {
	   return curRow;
	}

	public void setQueuePosition(int queuePosition) {
		this.queuePosition = queuePosition;
	}
	
	public void moveUp() {
		this.queuePosition -= 1;
		if (queuePosition < 0) {
			queuePosition = Manager.NUM_ROBOTS - 1;
		}
	}
}
