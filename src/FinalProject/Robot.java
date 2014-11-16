package FinalProject;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Robot {
	
	//For all robots, they cannot share their data of routes for the others. 
	private HashMap<Integer, Stack<Point>> routes;
	private Stack<Point> currentPath;
	private String name;
	private Point[][] mine;
	private boolean[][] ifVisited;
	private boolean[] carvenVisited;
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
		routes = new HashMap<Integer, Stack<Point>>();
		this.mine = mine;
		this.numRows = numRows;
		this.numCols = numCols; 
		
		ifVisited = new boolean[numRows][numCols];
		carvenVisited = new boolean[numberOfCaverns];
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
	//			System.out.println("AlreadyFound this Cavern before");
				/*for (Point p : routes.get(k)) {
					numVisited++;
					this.col = p.col;
					this.row = p.row;
				}*/
		 }
		if (!alreadyFound){ 
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
				  if (mine[row][col].isEntrance){
					 //found is for if this cavern is found or not in the recursive funtion
					 found = false;
					 currentPath = new Stack<Point>();
					 traverse(cavernNumber, row, col);
					 break;
					
				  }
				}
			}
		}
	}
	
	private void traverse(int cavernNumber, int row, int col) {
		//if the cavern is found found, then do the recursive part
		if (!found){

			//first, we need set the current point to be visited. That means ifVistied is true
			ifVisited[row][col] = true;
			currentPath.add(mine[row][col]);

			for (int r = -1; r < 2; r++){
				for (int c = -1; c < 2; c++){
					if (Math.abs(r) != Math.abs(c)){
						if (row + r >= 0 && row + r < numRows){
							if (col + c >=0 && col + c < numCols){
								if (!ifVisited[row + r][col + c]){
									if (mine[row + r][col + c].type.equals(PointType.PATH)){
										traverse(cavernNumber, row + r, col + c);
									} else if (mine[row + r][col + c].type.equals(PointType.CAVERN)){
										if (!carvenVisited[((CavernPoint) mine[row + r][col + c]).getCavernNumber()]){
											carvenVisited[((CavernPoint) mine[row + r][col + c]).getCavernNumber()] = true;
											currentPath.add(mine[row + r][col + c]);
											routes.put(((CavernPoint) mine[row + r][col + c]).getCavernNumber(), currentPath);
											System.out.println(routes.get((((CavernPoint) mine[row + r][col + c]).getCavernNumber())).size());
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
			}

			//We need set the current point is not visited and pop the last point out of the Currentpath if the
			//cavern is not found. 
			ifVisited[row][col] = false;
			currentPath.pop();
		} 
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
		return curCol;
	}
	
	public int getRow() {
	   return curRow;
	}
}
