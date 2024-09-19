import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class contains test methods for testing the functionality of the BackendPlaceholder class.
 * It includes tests for loading data from a file, retrieving statistics, finding the shortest path,
 * calculating the total time of a path, and handling invalid inputs.
 */
public class BackendDeveloperTests {

  // Create instances of PlaceholderMap, DijkstraGraph, and BackendPlaceholder
  PlaceholderMap map = new PlaceholderMap();
  DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(map);
  Backend backend = new Backend(graph);

  /**
   * Test for loading data from a file.
   * This test checks if the file is found and correctly read, and verifies the contents of the
   * loaded data.
   */
  @Test
  public void testLoadFile() throws FileNotFoundException {
    // Flag to check if file loading was successful
    boolean fileFound = false;
    try {
      backend.readDataFromFile("campus.dot");
      fileFound = true;
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

    // Assert that the file was found and data was loaded successfully
    Assertions.assertTrue(fileFound);

    // Test the contents of the loaded data
    int expectedNumNodes = 160; // Expected number of keys in the map
    int actualNumNodes = map.getSize(); // Actual number of keys in the map
    boolean testLocation = map.containsKey("Wisconsin State Historical Society"); // Check if a specific key exists in
    // the map

    // Assert that the actual number of keys matches expected and a specific location exists
    Assertions.assertEquals(expectedNumNodes, actualNumNodes);
    Assertions.assertTrue(testLocation);
  }


  /**
   * Test for retrieving statistics from the backend.
   * This test checks if the statistics returned by the backend match the expected output.
   */
  @Test
  public void testGetStatistics() throws FileNotFoundException {
    backend.readDataFromFile("campus.dot");
    // Define expected and actual statistics from the backend
    String expected =
        graph.getNodeCount() + "," + graph.getEdgeCount() + "," + backend.totalTime + ",";
    String actual = backend.getStatistics();
    Assertions.assertEquals(expected, actual);
  }


  /**
   * Test for finding the shortest path between two points.
   * This test checks if the path found is correct in terms of the number of nodes and the order of nodes.
   */
  @Test
  public void testGetShortestPath() throws FileNotFoundException {
    // Load data and define start and end points
    backend.readDataFromFile("campus.dot");
    String startPoint = "Memorial Union";
    String endPoint = "Science Hall";
    // Get the shortest path from start to end point
    PathInterface shortestPath = backend.getShortestPath(startPoint, endPoint);
    List path = shortestPath.getPath();
    // Define expected and actual number of nodes in the path
    int expectedNumNodes = 2;
    int actualNumNodes = path.size();
    Assertions.assertEquals(expectedNumNodes, actualNumNodes);
    // Define expected and actual order of nodes in the path
    String expectedOrder = "[Memorial Union, Science Hall]";
    String actualOrder = path.toString();
    Assertions.assertEquals(expectedOrder, actualOrder);
  }


  /**
   * Test for calculating the total time of a path.
   * This test checks if the total time calculated for a given path is as expected.
   */
  @Test
  public void testGetTime() throws FileNotFoundException {
    // Load data and define start and end points
    backend.readDataFromFile("campus.dot");
    String startPoint = "Memorial Union";
    String endPoint = "Science Hall";
    backend.getShortestPath(startPoint, endPoint); // Get the total time for the shortest path
    double expectedTime = 105.8;
    double actualTime = backend.getShortestPath(startPoint, endPoint).getTotalTime().doubleValue();
    System.out.println(actualTime);
    Assertions.assertEquals(expectedTime, actualTime);
  }


  /**
   * Test for handling invalid input.
   * This test checks if the system correctly handles cases where the input points do not exist in the graph.
   */
  @Test
  public void testInvalidInput() throws FileNotFoundException {
    // Load data and define invalid start and end points
    backend.readDataFromFile("campus.dot");
    String startPoint = "A";
    String endPoint = "B";
    boolean elementFound = true;
    try {
      backend.getShortestPath(startPoint, endPoint);
    }
    catch (NoSuchElementException e) {
      System.out.println("No such element in the graph");
      elementFound = false;
    }
    Assertions.assertFalse(elementFound);
  }

  /**
   * This test method, part of a test class, is designed to validate the integration of the
   * frontend and backend roles, specifically testing how the application responds when the file
   * loaded and the statistics are printed.
   *
   * @throws FileNotFoundException if the file operations within the test encounter this exception
   */
  @Test
  public void testIntegration1() throws FileNotFoundException {
    // Testing with an invalid file name input
    TextUITester tester = new TextUITester("load\ncampus.dot\nstats\nquit\n");
    Scanner input = new Scanner(System.in);
    Frontend testFrontend = new Frontend(input, backend);
    testFrontend.run(); //running the frontend loop
    String expected = "Number of buildings: 160\n" +
        "Number of edges: 800\n" +
        "Total travel time: 112758.199";
    String actual = tester.checkOutput();
    Assertions.assertTrue(actual.contains(expected));
  }


  /**
   * This test method, part of a test class, aims to validate the integration of the
   * frontend and backend roles, particularly focusing on shortest path between two nodes.
   *
   * @throws FileNotFoundException if file operations within the test encounter this exception.
   */
  @Test
  public void testIntegration2() throws FileNotFoundException {
    TextUITester tester = new TextUITester("""
        load
        campus.dot
        short
        Memorial Union
        North Hall
        quit
        """);
    Scanner input = new Scanner(System.in);
    Frontend testFrontend = new Frontend(input, backend);
    testFrontend.run();
    String expected = "You are now at: North Hall.\n" + "Total distance walked: 388.9";
    String actual = tester.checkOutput();
    Assertions.assertTrue(actual.contains(expected));
  }

  /**
   * This test tests the quit method of the Frontend class of the UW Pathfinder application.
   * It simulates user interaction by providing input (in this case, the "quit" command)
   * and checks whether the application responds as expected.
   * This method checks whether the quit method runs the way it is expected to when you quit
   * the app.
   */
  @Test
  public void testPartnersFrontend1() throws FileNotFoundException {
    TextUITester tester = new TextUITester("quit\n");
    Scanner input = new Scanner(System.in);
    Frontend testFrontend = new Frontend(input, backend);
    testFrontend.run();
    String expected = "Byebye"; //expected output from the application after quitting.
    String actual = tester.checkOutput();
    Assertions.assertTrue(actual.contains(expected));
  }


  /**
   * This test tests the loadFile method of the Frontend class of the UW Pathfinder
   * application. It simulates user interaction by providing input by loading the file and
   * then checking if the expected output is returned.
   */
  @Test
  public void testPartnersFrontend2() throws FileNotFoundException {
    TextUITester tester = new TextUITester("load\ncampus.dot\nquit");
    Scanner input = new Scanner(System.in);
    Frontend testFrontend = new Frontend(input, backend);
    testFrontend.run();
    String expected = "File successfully loaded."; //expected output after loading.
    String actual = tester.checkOutput();
    Assertions.assertTrue(actual.contains(expected));
  }
}
