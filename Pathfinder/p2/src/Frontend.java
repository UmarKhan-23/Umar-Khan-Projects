import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Frontend implements FrontendInterface{

  // Fields:
  Scanner mainScanner;
  Backend<String, Integer> backend;
  String loadDataHelperMessage;
  // Constructor:
  public Frontend(Scanner input, BackendInterface<String, Integer> backend) {
    mainScanner = input;
    this.backend = (Backend<String, Integer>) backend;
    loadDataHelperMessage = "Please enter the filepath";
  }

  /**
   * Prompts the user for a path for the data file and passes it to the backend to
   * read data. Does not throw a FileNotFoundException, handled by while loop that goes until
   * valid input is given or user quits.
   */
  @Override
  public void loadData() {
    System.out.println(loadDataHelperMessage + ": ");
    String filePath;
    boolean loop = true;
    filePath = mainScanner.nextLine();
    while (loop) {
      try {
        backend.readDataFromFile(filePath);
        loop = false;
        System.out.println("File successfully loaded.");
        System.out.println();
      } catch (FileNotFoundException e) {
        System.out.println("Error: FileNotFoundException!");
        System.out.println();
        System.out.println(loadDataHelperMessage +", or \"quit\" to exit: ");
        filePath = mainScanner.nextLine();
        loop = true;
        if(filePath.equals("quit")) {
          loop = false;
        }
      }
    }
  }

  /**
   * Retrieve statistics from the dataset and returns summary.
   *
   * @return Summary of dataset statistics, including number of buildings, number of edges, and
   * total walking time
   */
  @Override
  public String getStatistics() {
    String fromBackend = backend.getStatistics();
    String[] list = fromBackend.split(",");
    String cat = "Number of buildings: ";
    cat += list[0] +"\n";
    cat += "Number of edges: " + list[1] + "\n";
    cat += "Total travel time: " + list[2] + "\n";
    return cat;
  }

  /**
   * Determines the shortest path between two buildings (start and destination) and returns a
   * summary of the path.
   *
   * @return The shortest path between the start and destination, including all buildings along
   * the way, the walk time between each edge, and total time to walk from start to destination
   */
  @Override
  public String getShortestPath() {
    System.out.println("Please enter startpoint: ");
    String start = mainScanner.nextLine();
    System.out.println("Please enter endpoint: ");
    String end = mainScanner.nextLine();
    String cat = "\n";
    double distance = 0.0;
    try {

      Path<String, Integer> shortest = (Path<String, Integer>) backend.getShortestPath(start, end);
      cat += "Start point: ";

      for (int i = 0; i < shortest.getPath().size(); i++) {
        cat += shortest.getPath().get(i) +".\n";
        if (i != shortest.getPath().size() - 1) {
          cat += "Distance to next location: " + shortest.getWalkingTimes().get(i) + "\n\n";
          cat += "You are now at: ";
        }
      }
      cat += "Total distance walked: " + shortest.getTotalTime();
      System.out.println(cat);
    } catch (NoSuchElementException e) {
      System.out.println("Error! No valid path between "+start+" and "+end+"!");
    }
    return "";
  }

  /**
   * Runs the program, including all sub commands such as getting the shortest path between two
   * buildings and getting summary statistics, and exits when the user quits the program.
   */
  @Override
  public void run() {
    System.out.println("Hi. Please enter one of the following commands: ");
    String input = "";
    while(!input.equals("quit")) {
      System.out.println("load - Loads data into the system.");
      System.out.println("stats - Prints the stats of the loaded map.");
      System.out.println("short - Prints the shortest path between two points.");
      System.out.println("quit - quits the program.");
      input = mainScanner.nextLine();
      switch(input) {
        case "load":
          loadData();
          break;
        case "stats":
          System.out.println(getStatistics());
          break;
        case "short":
          System.out.println(getShortestPath());
          break;
        case "quit":
          break;
        default:
          System.out.println("Command not recognized!");
      }
    }
    exit();
  }

  /**
   * Exits the main loop of program
   */
  @Override
  public void exit() {
    System.out.println("Byebye!");
  }

}
