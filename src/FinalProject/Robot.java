package FinalProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Robot {
	
	//For all robots, they cannot share their data of routes for the others. 
	private HashMap<Integer, ArrayList<Point>> routes;
	private Stack<Point> currentPath;
	private String name;
	private Point[][] mine;
	private boolean[][] ifVisited;
	private boolean[] cavernVisited;
	//FOR DEV ONLY
	int numVisited = 0;
	int numberOfCaverns = 4;
	
	private boolean found;
	
	//numOf Col and row for mine;
	private int numCols;
	private int numRows;
	
	// current location
	private int curCol = 0;
	private int curRow = 0;
	
	public Robot(String name, Point[][] mine, int numRows, int numCols) {
		this.name = name;
		routes = new HashMap<Integer, ArrayList<Point>>();
		this.mine = mine;
		this.numRows = numRows;
		this.numCols = numCols; 
		
		ifVisited = new boolean[numRows][numCols];
		cavernVisited = new boolean[numberOfCaverns];
		//For tests
//		for (int i = 0; i < 4; i++) {
//			routes.put(i + 1, new Stack<Point>());
//      }
	}
	
	public void findCavern(int cavernNumber) {
		Set keyset = routes.keySet();
		boolean alreadyFound = false;
		for (Object k : keyset) {
			if ((Integer) k == cavernNumber)
				alreadyFound = true;
		 }
		if (!alreadyFound){ 
			
			//Find the entrance
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					
					//Start traverse at entrance
					if (mine[row][col].isEntrance){
						//found is for if this cavern is found or not in the recursive function
						found = false;
						currentPath = new Stack<Point>();
						traverse(cavernNumber, row, col);
						break;
					}
				}
			}
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
		} 
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
}
