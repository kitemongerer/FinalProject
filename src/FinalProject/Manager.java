package FinalProject;

import java.util.LinkedList;
import java.util.Queue;

public class Manager {

	private Point[][] mine;
	private int currentRobot;
	public static final int NUM_ROBOTS = 4;
	public static final String[] names = {
		"Zlad",
		"Keytarist Girl",
		"Space Invader",
		"Darth Vapour"
	};
	private static final int MINE_SIZE = 20;
	private String inputFile;
	private LinkedList<Robot> queue;
	
	public Manager(String inputFile) {
		this.inputFile = inputFile;
		mine = new Point[MINE_SIZE][MINE_SIZE];
		readInputFile();
		queue = new LinkedList<Robot>();
		currentRobot = 0;
		for (int i = 0; i < NUM_ROBOTS; i++)
			queue.add(new Robot(names[i]));
	}
	
	public void sendRobot(int cavernNumber) {

	}
	
	private void readInputFile() {
		//FOR tests
		for (int i = 0; i < MINE_SIZE; i++) {
			for (int j = 0; j < MINE_SIZE; j++) {
				mine[i][j] = new Point();
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
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
*/
}
