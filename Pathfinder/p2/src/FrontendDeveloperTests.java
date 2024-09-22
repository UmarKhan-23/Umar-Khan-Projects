import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class FrontendDeveloperTests {

  /**
   *
   */
  @Test
  public void partnerTestGetShort() {

  }

  /**
   * Ensures functionality of getShortestPath() by testing that the route will return
   * the proper shortest path, even if it does not step through the least number of nodes.
   */
  @Test
  public void integrationTestGetShortestPath () {
    TextUITester tester = new TextUITester("load\ncampus.dot\nshort\nMemorial Union\nEducation Building\nquit\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> backend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, backend);
    frontend.run();
    String output = tester.checkOutput();
    scanner.close();
    Assertions.assertTrue(output.contains("Radio Hall") && output.contains("113.0"));
  }

  /**
   * Tests that the backend can return a correct string to the frontend when stats is called
   */
  @Test
  public void testIntegrationGetStatistics() {
    TextUITester tester = new TextUITester("load\ncampus.dot\nstats\nquit\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> backend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, backend);
    frontend.run();
    String output = tester.checkOutput();
    scanner.close();
    Assertions.assertTrue(output.contains("Number of buildings: 160") &&
        output.contains("Number of edges: 800") &&
        output.contains("Total travel time: 112"));
  }

  /**
   * This test ensures proper functionality of the loadFile method when given correct input
   */
  @Test
  public void testIntegrationLoadFile() {
    TextUITester tester = new TextUITester("load\ncampus.dot\nquit\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> backend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, backend);
    frontend.run();
    String output = tester.checkOutput();
    scanner.close();
    Assertions.assertTrue(output.contains("File successfully loaded."));
  }

  /**
   * This test ensures proper functionality of the loadFile method when given correct input
   */
  @Test
  public void testLoadFile() {
    TextUITester tester = new TextUITester("campus.dot\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> placeholderBackend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, placeholderBackend);
    frontend.loadData();
    String output = tester.checkOutput();
    scanner.close();
    if(output.contains("File successfully loaded."))
      Assertions.assertTrue(true);
    else
      Assertions.assertTrue(false);
  }

  /**
   * This method ensures that the loadFile method will prompt the user for input a second time if and only
   * if the first input is not a valid filepath.
   */
  @Test
  public void testLoadFileTypo() {
    TextUITester tester = new TextUITester("camps.dot\ncampus.dot\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> placeholderBackend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, placeholderBackend);
    frontend.loadData();
    String output = tester.checkOutput();
    scanner.close();
    if(output.contains("Error: FileNotFoundException!"))
      Assertions.assertTrue(true);
    else
      Assertions.assertTrue(false);
  }

  /**
   * This ensures proper output of statistics to the user when getStatistics is called with valid inputs
   */
  @Test
  public void testGetStatistics() {
    TextUITester tester = new TextUITester("load\ncampus.dot\nstats\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> placeholderBackend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, placeholderBackend);
    frontend.run();
    String output = tester.checkOutput();
    scanner.close();
    if(output.contains("Number of buildings") ||
        output.contains("160"))
      Assertions.assertTrue(true);
    else
      Assertions.assertTrue(false);
  }

  /**
   * This method ensures proper output of the shortest path when getShortestPath is called with valid inputs
   */
  @Test
  public void testGetShortestPath() {
    TextUITester tester = new TextUITester("load\ncampus.dot\nshort\nMemorial Union\nScience Hall\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> placeholderBackend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, placeholderBackend);
    frontend.run();
    String output = tester.checkOutput();
    if(output.contains("105.8") && output.contains("Total distance wal"))
      Assertions.assertTrue(true);
    else
      Assertions.assertTrue(false);
  }

  /**
   * Method ensures that the frontend outputs the proper error message when getShortestPath can not find a
   * valid path from B to A
   */
  @Test
  public void testGetShortestPathError() {
    TextUITester tester = new TextUITester("load\ncampus.dot\nshort\nerror\nerror\nquit\nquit\n");
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> placeholderBackend = new Backend<String, Integer>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, placeholderBackend);
    frontend.run();
    String output = tester.checkOutput();
    if(output.contains("Please enter startpoint") &&
        output.contains("Error! No valid path"))
      Assertions.assertTrue(true);
    else
      Assertions.assertTrue(false);
  }
}


