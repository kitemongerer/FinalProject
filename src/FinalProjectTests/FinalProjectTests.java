package FinalProjectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import FinalProject.CavernPoint;
import FinalProject.Manager;
import FinalProject.Point;
import FinalProject.PointType;
import FinalProject.Robot;

public class FinalProjectTests {
	private static Manager manager;
	public static final String standardInputFile = "mine.csv";
	
	@BeforeClass
	public static void before() {
		manager = new Manager(standardInputFile);
	}
	
	@Test
	public void testFileReading() {
		manager.setManager();
		
		// Test that paths are read correctly
		assertEquals(PointType.PATH, manager.getPointAt(4, 5).type);
		assertEquals(PointType.PATH, manager.getPointAt(16, 6).type);
		assertEquals(PointType.PATH, manager.getPointAt(16, 14).type);
		
		//Test if entrance is read correctly
		assertEquals(true, manager.getPointAt(19, 19).isEntrance);
		assertEquals(PointType.PATH, manager.getPointAt(19, 19).type);
		
		//Test that Walls are read correctly
		assertEquals(PointType.WALL, manager.getPointAt(0, 19).type);
		assertEquals(PointType.WALL, manager.getPointAt(5, 5).type);
		assertEquals(PointType.WALL, manager.getPointAt(6, 2).type);
		
		//Test that Walls are read correctly
		assertEquals(PointType.WALL, manager.getPointAt(0, 19).type);
		assertEquals(PointType.WALL, manager.getPointAt(5, 5).type);
		assertEquals(PointType.WALL, manager.getPointAt(6, 2).type);
		
		//Test that caverns are read correctly
		assertEquals(PointType.CAVERN, manager.getPointAt(2, 1).type);
		assertEquals(1, ((CavernPoint)manager.getPointAt(2, 1)).getCavernNumber());
		assertEquals(PointType.CAVERN, manager.getPointAt(14, 4).type);
		assertEquals(2, ((CavernPoint)manager.getPointAt(14, 4)).getCavernNumber());
		assertEquals(PointType.CAVERN, manager.getPointAt(8, 12).type);
		assertEquals(3, ((CavernPoint)manager.getPointAt(8, 12)).getCavernNumber());
		assertEquals(PointType.CAVERN, manager.getPointAt(3, 15).type);
		assertEquals(4, ((CavernPoint)manager.getPointAt(3, 15)).getCavernNumber());
	}
	
	@Test
	public void testNextRobot() {
		manager.setManager();

		// Check the robot number is right then send that robot and check that the next robot is now current
		assertEquals(0, manager.getCurrentRobot());
		manager.sendRobot(1);
		
		assertEquals(1, manager.getCurrentRobot());
		manager.sendRobot(1);

		assertEquals(2, manager.getCurrentRobot());
		manager.sendRobot(1);

		assertEquals(3, manager.getCurrentRobot());
		manager.sendRobot(1);

		assertEquals(0, manager.getCurrentRobot());
		manager.sendRobot(1);
	}

	@Test
	public void testFindingCaverns() {
		manager.setManager();

		manager.sendRobot(1);
		manager.sendRobot(2);
		manager.sendRobot(3);
		manager.sendRobot(4);
		
		ArrayList<Robot> queue = manager.getQueue();
		Robot robot = queue.get(0);
		ArrayList<Point> path = robot.getRoutes().get(1);
		
		//Check that the size of the path is correct
		assertEquals(36, path.size());
		//Check that the last element of the path (top of stack) is the correct cavern space
		assertEquals(manager.getPointAt(2, 1), path.get(path.size() - 1));
			
		//Since the queue went all the way around and we aren't changing the order
		//The next robot is in spot 1
		robot = queue.get(1);
		path = robot.getRoutes().get(2);
		assertEquals(21, path.size());
		assertEquals(manager.getPointAt(14, 4), path.get(path.size() - 1));	
		
		robot = queue.get(2);
		path = robot.getRoutes().get(3); //18 or 19
		
		//There are two paths to cavern 3
		assertTrue(path.size() == 18 || path.size() == 19);
		
		if (path.size() == 18) {
			assertEquals(manager.getPointAt(9, 12), path.get(path.size() - 1));
		} else if (path.size() == 19) {
			assertEquals(manager.getPointAt(8, 12), path.get(path.size() - 1));
		}
		
		robot = queue.get(3);
		path = robot.getRoutes().get(4);
		
		//There are also two paths to cavern 4
		assertTrue(path.size() == 49 || path.size() == 51);
		
		//Both paths lead to the same spot though so only one check here
		assertEquals(manager.getPointAt(3, 15), path.get(path.size() - 1));	
	}
	
	@Test
	public void testAlreadyKnowsPath() {
		manager.setManager();

		// send all 4 robots to cavern 1
		manager.sendRobot(1);
		manager.sendRobot(1);
		manager.sendRobot(1);
		manager.sendRobot(1);
		
		// send first robot once again to cavern 1
		manager.sendRobot(1);
		ArrayList<Robot> q = manager.getQueue();
		assertEquals(36, q.get(3).getRoutes().get(1).size());
	}
}
