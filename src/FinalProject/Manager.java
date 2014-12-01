package FinalProject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Manager extends JFrame {

	private Point[][] mine;
	private int currentRobot;
	public static final int NUM_ROBOTS = 4;
	public static int NUM_CAVERNS = 0;
	public static final int POINT_SIZE = 26;
	public static final int QUEUE_HORIZONTAL = 50;
	public static final int QUEUE_VERTICAL = 550;
	public static Map<String, String> ROBOTS;
	private JTextField cavernChoice;
	private JTextField speedSettingField;

	//		"Zlad",
	//		"Keytarist Girl",
	//		"Space Invader",
	//		"Darth Vapour"

	public static final int FRAME_SIZE = 700;
	private int numRows;
	private int numCols;
	private String inputFile;
	private ArrayList<Robot> queue = new ArrayList<Robot>();;

	private Mine m;
	private JLabel[] labels;

	public Manager(String inputFile) {
		this.inputFile = inputFile;
		//add(minePanel);
		try {
			readInputFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		setSize(1000, FRAME_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		m = new Mine(mine, queue, numRows, numCols);
		add(m);
		setManager();

		add(createNextRobotPanel(), BorderLayout.EAST);
		setVisible(true);
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
		if (queue.get((currentRobot + 1) % NUM_ROBOTS).getRoutes().containsKey(cavernNumber)) {
			queue.get(currentRobot).getRoutes().put(cavernNumber, queue.get((currentRobot + 1) % NUM_ROBOTS).getRoute(cavernNumber));
		}
		queue.get(currentRobot).findCavern(cavernNumber);

		//Set the next robot is the current robot
		currentRobot = (currentRobot + 1) % NUM_ROBOTS;
		for (Robot robot : queue) {
			robot.moveUp();
		}
		for (int i = 0; i < NUM_ROBOTS; i++) {
			String text = queue.get(i).getName() + ": ";
			for (int key :queue.get(i).getRoutes().keySet()) {
				text += key + ", ";
			}
			text = text.substring(0, text.length() - 2);
			labels[i].setText(text);
		}
	}

	public JPanel createNextRobotPanel() {
		JPanel nextRobotPanel = new JPanel();
		nextRobotPanel.setLayout(new GridLayout(4,1));
		nextRobotPanel.add(createNextButton());
		nextRobotPanel.add(createChooseCavern());
		nextRobotPanel.add(createRobotStatus());
		nextRobotPanel.add(timerSetting());
		return nextRobotPanel;
	}

	private class IncreaseSpeedListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (queue.get(currentRobot).getSpeed() - 25 <= 0){
				JOptionPane.showMessageDialog(null, "The speed of this robot is too fast!");
			}else{
				queue.get(currentRobot).setSpeed(queue.get(currentRobot).getSpeed() - 25);
				speedSettingField.setText(((Integer) (500 - queue.get(currentRobot).getSpeed())).toString());
			}
		}
	}

	private class DecreaseSpeedListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (queue.get(currentRobot).getSpeed() +25 >= 500){
				JOptionPane.showMessageDialog(null, "The speed of this robot is too fast!");
			}else{
				queue.get(currentRobot).setSpeed(queue.get(currentRobot).getSpeed() + 25);
				speedSettingField.setText(((Integer)(500 - queue.get(currentRobot).getSpeed())).toString());
			}
		}
	}

	public JPanel timerSetting(){
		JPanel settingSpeed = new JPanel();
		settingSpeed.setLayout(new GridLayout(3,1));
		JButton inSpeed = new JButton("Increase Speed");
		JButton deSpeed = new JButton("Decrease Speed");
		inSpeed.addActionListener(new IncreaseSpeedListener());
		deSpeed.addActionListener(new DecreaseSpeedListener());
		settingSpeed.setBorder(new TitledBorder(new EtchedBorder(), "Set the Speed of Robots"));

		speedSettingField = new JTextField(20);
		speedSettingField.setText(((Integer)queue.get(currentRobot).getSpeed()).toString());
		speedSettingField.setEditable(false);
		settingSpeed.add(speedSettingField);
		settingSpeed.add(inSpeed);
		settingSpeed.add(deSpeed);
		return settingSpeed;	

	}

	public JPanel createRobotStatus() {
		JPanel robotStatus = new JPanel();
		robotStatus.setLayout(new GridLayout(4,4));
		robotStatus.setBorder(new TitledBorder(new EtchedBorder(),"List of Robots and explored Caverns"));
		labels = new JLabel[NUM_ROBOTS];
		int i = 0;
		for (Robot robot : queue) {
			labels[i] = new JLabel(robot.getName() + ":  ");
			String str = "";
			str = robot.getName() + ":";
			labels[i] = new JLabel(str);
			robotStatus.add(labels[i]);		
			i++;
		}
		return robotStatus;
	}

	public JButton createNextButton() {
		JButton nextRobot = new JButton("Next");
		nextRobot.addActionListener(new NextRobotListener());
		return nextRobot;
	}

	public JPanel createChooseCavern() {
		JPanel chooseCavern = new JPanel();
		JLabel chooseText = new JLabel("Which cavern should the next robot find?");
		cavernChoice = new JTextField(20);
		chooseCavern.setLayout(new GridLayout(2,1));
		chooseCavern.add(chooseText);
		chooseCavern.add(cavernChoice);
		chooseCavern.setBorder(new EtchedBorder());
		return chooseCavern;
	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	private class NextRobotListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cavernInput = cavernChoice.getText();
			int cavernNumber;
			if (!isInt(cavernInput))
				JOptionPane.showMessageDialog(null, "You must enter a number.");
			else {
				Boolean readyForNext = true;
				for (Robot r : queue) {
					if (!r.inQueue()) {
						readyForNext = false;
					}
				}
				cavernNumber = Integer.parseInt(cavernInput);
				if (cavernNumber <= 0 || cavernNumber > NUM_CAVERNS)
					JOptionPane.showMessageDialog(null, "The cavern number must be between 1 and " + NUM_CAVERNS + ".");
				else if (!readyForNext)
					JOptionPane.showMessageDialog(null, "A robot is still exploring please wait.");
				else
					sendRobot(cavernNumber);//Problem is with this line.
			}
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
					NUM_CAVERNS++;
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
		Manager m = new Manager("mine2.csv");
	}


}
