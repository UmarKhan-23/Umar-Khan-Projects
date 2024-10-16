import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;

/**
 * This class contains tests for the Frontend, focusing on its various functionalities.
 * Each method tests a specific functionality of the Frontend.
 */
public class FrontendDeveloperTests {
  BackendPlaceholder backend = new BackendPlaceholder();

  /**
   * Test to ensure the displayMenus method of Frontend class contains the words "Please make a
   * choice" which ensures that the program is working fine after giving the appropriate input.
   */
  @Test
  public void testMenus() {
    TextUITester tester = new TextUITester("4\n");
    Frontend testFrontend = new Frontend(backend);
    testFrontend.startMainLoop();
    String output = tester.checkOutput();
    assertTrue(output.contains("Please make a choice"));
  }

  /**
   * Test to ensure the loadDataFile method of Frontend class calls backend and loads the file after
   * giving the appropriate input.
   */
  @Test
  public void testLoadFile() {
    TextUITester tester = new TextUITester("1\nuk\n4\n");
    Frontend testFrontend = new Frontend(backend);
    testFrontend.startMainLoop();
    String output = tester.checkOutput();
    assertTrue(output.contains("File loaded"));
  }

  /**
   * Test to check if the Frontend class does what it is supposed to do if the user enters invalid
   * input.
   */
  @Test
  public void testInput() {
    TextUITester tester = new TextUITester("5\ninvalid\n4\n");
    Frontend testFrontend = new Frontend(backend);
    testFrontend.startMainLoop();
    String output = tester.checkOutput();
    assertTrue(output.contains("Out of range choice"));
  }

  /**
   * Test to ensure the listHighestMassMeteorites method of Frontend class returns the expected
   * output.
   */
  @Test
  public void listHighestMassMeteoritesTester() {
    TextUITester tester = new TextUITester("2\n4\n");
    Frontend testFrontend = new Frontend(backend);
    testFrontend.startMainLoop();
    String output = tester.checkOutput();
    assertTrue(output.contains("Umar, 50.0, 30.0, 20.0, true"));
  }

  /**
   * Test to ensure the listAllMeteoritesWithinMassRange method of Frontend class returns the
   * expected output.
   */
  @Test
  public void listHighestMassMeteoritesWithinRangeTester() {
    TextUITester tester = new TextUITester("3\n30\n120\n4");
    Frontend testFrontend = new Frontend(backend);
    testFrontend.startMainLoop();
    String output = tester.checkOutput();
    assertTrue(output.contains("Umar, 50.0, 30.0, 20.0, true, Khan, 100.0, 10.0, 10.0, false"));
  }


  /**
   * This test tests the integration of the code by testing the load file method of the backend.
   */

  /*
  @Test
  public void testIntegration1() {
    BackendInterface backendInterface = new BackendInterface();
    Frontend testFrontend = new Frontend(backendInterface);
    ArrayList<Meteorite> list = backendInterface.load("meteorTest.csv");
    assertTrue(list.contains("Aarhus"));
  }

   */

  /**
   * This test tests the integration of the code by testing the maxMass method of the backend.
   */

  /*
  @Test
  public void testIntegration2() {
    BackendInterface backendInterface = new BackendInterface();
    Frontend testFrontend = new Frontend(backendInterface);
    ArrayList<Meteorite> list1 = backendInterface.load("meteorTest.csv");
      ArrayList<Meteorite> list2 = backendInterface.maxMass();
      assertTrue(list2.contains("Abee"));
    }

   */
    


}

