package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Manager extends JFrame {

	private Point[][] mine;
	private int currentRobot;
	public static final int NUM_ROBOTS = 4;
	public static final String[] names = {
		"Zlad",
		"Keytarist Girl",
		"Space Invader",
		"Darth Vapour"
	};
	private int numRows;
	private int numCols;
	private String inputFile;
	private LinkedList<Robot> queue;
	
	private Mine m;
	
	public Manager(String inputFile) {
		this.inputFile = inputFile;
		//add(minePanel);
		try {
			readInputFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		setManager();
		setVisible(true);
		setSize(500, 500);
		m = new Mine(mine, numRows, numCols);
		add(m);
	}
	
	//Everytime we run the test, we need this class to initialize the data.
	public void setManager(){
		queue = new LinkedList<Robot>();
		currentRobot = 0;
		for (int i = 0; i < NUM_ROBOTS; i++)
			queue.add(new Robot(names[i], mine, numRows, numCols));
	}
	
	public void sendRobot(int cavernNumber) {;
		queue.get(currentRobot).findCavern(cavernNumber);
		//Set the next robot is the current robot
	    currentRobot = (currentRobot + 1) % NUM_ROBOTS;
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
	public LinkedList<Robot> getQueue() {
		return queue;
	}
	
	
	public static void main(String[] args) {
		Manager m = new Manager("mine.csv");
	}
	
	
}
