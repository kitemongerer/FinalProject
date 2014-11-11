package FinalProject;

public class Manager {

	private Point[][] mine;
	private int currentRobot;
	public static final int NUM_ROBOTS = 4;
	private static final int MINE_SIZE = 20;
	private String inputFile;
	
	public Manager(String inputFile) {
		this.inputFile = inputFile;
		readInputFile();
		mine = new Point[MINE_SIZE][MINE_SIZE];
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
