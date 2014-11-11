package FinalProject;

import java.util.LinkedList;
import java.util.Queue;

public class Manager {

	private Point[][] mine;
	private int currentRobot;
	public static final int NUM_ROBOTS = 4;
	private static final int MINE_SIZE = 20;
	private String inputFile;
	private Queue<Robot> queue;
	
	public Manager(String inputFile) {
		this.inputFile = inputFile;
		readInputFile();
		queue = new LinkedList<Robot>();
		mine = new Point[MINE_SIZE][MINE_SIZE];
		currentRobot = 0;
	}
	
	public void sendRobot(int cavernNumber) {
		
	}
	
	private void readInputFile() {
		
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
*/
}
