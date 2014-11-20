package FinalProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

public class Manager extends JFrame {

	private Point[][] mine;
	private int currentRobot;
	public static final int NUM_ROBOTS = 4;
	public static final int POINT_SIZE = 26;
	public static final int QUEUE_HORIZONTAL = 50;
	public static final int QUEUE_VERTICAL = 550;
	public static Map<String, String> ROBOTS;
	
//		"Zlad",
//		"Keytarist Girl",
//		"Space Invader",
//		"Darth Vapour"

	public static final int FRAME_SIZE = 650;
	private int numRows;
	private int numCols;
	private String inputFile;
	private ArrayList<Robot> queue = new ArrayList<Robot>();;
	
	private Mine m;
	
	public Manager(String inputFile) {
		this.inputFile = inputFile;
		//add(minePanel);
		try {
			readInputFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		setVisible(true);
		setSize(FRAME_SIZE, FRAME_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		m = new Mine(mine, queue, numRows, numCols);
		add(m);
		setManager();
	}
	
	//Everytime we run the test, we need this class to initialize the data.
	public void setManager(){
		ROBOTS = new HashMap<String, String>();
		ROBOTS.put("Zlad", "#7D26CD");
		ROBOTS.put("Keytarist Girl", "#FF00FF");
		ROBOTS.put("Space Invader", "#66CD00");
		ROBOTS.put("Darth Vapour", "#555555");
		
		//queue = new ArrayList<Robot>();
		currentRobot = 0;
		int i = 0;
		for (String name : ROBOTS.keySet()) {
			queue.add(new Robot(name, ROBOTS.get(name), mine, m, numRows, numCols));
			queue.get(i).setQueuePosition(i);
			i++;
		}
	}
	
	public void sendRobot(int cavernNumber) {
		queue.get(currentRobot).findCavern(cavernNumber);
		
		//Set the next robot is the current robot
	    currentRobot = (currentRobot + 1) % NUM_ROBOTS;
	    for (Robot robot : queue) {
	    	robot.moveUp();
	    }
	}
	
	private void readInputFile() throws BadConfigFormatException, FileNotFoundException {
		ArrayList<ArrayList<String>> tempMine = new ArrayList<ArrayList<String>>();
		Scanner mineRead = new Scanner(new FileReader(inputFile));

		numRows = 0;
		while (mineRead.hasNextLine()) {

			tempMine.add(new ArrayList<String>());
			String readLine = mineRead.nextLine();
			String[] letters = readLine.split(",");

			for (int i = 0; i < letters.length; i++) {
				tempMine.get(numRows).add(letters[i]);
			}

			if (numCols != 0 && numCols != letters.length) {
				mineRead.close();
				throw new BadConfigFormatException("Row lengths are inconsistant in layout config file.");
			} else {
				numCols = letters.length;
			}
			numRows++;
		}
		mineRead.close();

		mine = new Point[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (i > tempMine.size())
					throw new BadConfigFormatException("Temp board row count doesn't match config file");

				if (j > tempMine.get(0).size())
					throw new BadConfigFormatException("Temp board column count doesn't match config file");

				switch (tempMine.get(i).get(j)) {
				case "W":
					mine[i][j] = new Point(i, j, PointType.WALL);
					break;
				case "P":
					mine[i][j] = new Point(i, j, PointType.PATH);
					break;
				case "E":
					mine[i][j] = new Point(i, j, PointType.PATH);
					getPointAt(i, j).isEntrance = true;
					break;
				default:
					mine[i][j] = new CavernPoint(i, j, PointType.CAVERN ,tempMine.get(i).get(j));
					break;
				}
			}
		}
	}

	//FOR DEV ONLY
	public int getCurrentRobot() {	
		return currentRobot;
	}
	
	//FOR DEV ONLY
	public Point getPointAt(int row, int col) {
		return mine[row][col];
	}
	
	//FOR DEV ONLY
	public ArrayList<Robot> getQueue() {
		return queue;
	}
	
	
	public static void main(String[] args) {
		Manager m = new Manager("mine.csv");

		
		//TODO remove
		m.sendRobot(1);
		m.sendRobot(2);
	}
	
	
}
