import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Frontend class is responsible for interacting with the user, presenting options, and
 * making appropriate calls to the backend.
 */
public class Frontend implements FrontendInterface {

  // Reference to the backend system.
  BackendPlaceholder backend;

  // Scanner object to read user input.
  Scanner input;

  /**
   * Constructor initializes the frontend with a backend system.
   * @param backend Reference to the backend.
   */
  public Frontend(BackendPlaceholder backend) {
    this.backend = backend;
  }

  /**
   * Starts the main interactive command loop where the user can choose
   * different options related to meteorites.
   */
  public void startMainLoop() {
    System.out.println("Welcome to Meteorite Catcher!\n");
    displayMenus();
    System.out.println("ended");
  }


  /**
   * Displays the main menu of choices for the user and processes user input.
   */
  public void displayMenus() {

    boolean loopGoing = true;
    input = new Scanner(System.in);

    while(loopGoing) {
      // Display the main options.
      System.out.println("Please make a choice\n");
      System.out.println("1) Load data file\n");
      System.out.println("2) List meteorites by highest mass\n");
      System.out.println("3) List all meteorites within given mass range\n");
      System.out.println("4) Exit App\n");

      // Convert the user's choice from string to int.
      String stringChoice = input.next();
      int choice;
      try { // checks if the user entered anything other than a number
        choice = Integer.parseInt(stringChoice);
      }
      catch (Exception e) {
        System.out.println("Enter a number");
        continue;
      }


      if (choice >= 5 || choice <= 0) {
        String msg = "Out of range choice";
        invalidInputMessage(msg);
      }

      if (choice == 1) {
        System.out.println("Please enter file path");
        String path = input.next();
        loadDataFile(path);
      } else if (choice == 2) {
        listHighestMassMeteorites();
      } else if (choice == 3) {
        System.out.println("Enter lower threshold");
        double lowerThreshold = input.nextDouble();
        System.out.println("Enter upper threshold");
        double upperThreshold = input.nextDouble();
        listAllMeteoritesWithinMassRange(lowerThreshold, upperThreshold);
      } else if (choice == 4) {
        loopGoing = false;
        exitApp();
      }
    }

  }


  /**
   * Attempts to load the meteorite data from the provided file path.
   * @param file Name and path of the file to be loaded.
   */
  public void loadDataFile(String file) {
    if (backend.load(file)) {
      System.out.println("File loaded");
    }
    else {
      String msg = "File not loaded";
      invalidInputMessage(msg);
    }
  }


  /**
   * Method to handle the command to list the meteorites with the highest mass.
   */
  public void listHighestMassMeteorites() {
    ArrayList<MeteoriteInterface> list = backend.maxMass();
    System.out.println(list);
  }


  /**
   * Method to handle the command to list all the meteorites with mass between two specified
   * thresholds.
   * @param lowerThreshold lower boundary of the given mass range.
   * @param upperThreshold upper boundary of the given mass range.
   */
  public void listAllMeteoritesWithinMassRange(double lowerThreshold, double upperThreshold) {
    if (lowerThreshold < 0) {
      String errorMsg = "Lower Boundary is smaller than 0";
      invalidInputMessage(errorMsg);
      return;
    }
    if (lowerThreshold > upperThreshold) {
      String errorMsg = "Lower Boundary is greater than upper Boundary";
      invalidInputMessage(errorMsg);
      return;
    }
    ArrayList<MeteoriteInterface> list = backend.massBtw(lowerThreshold, upperThreshold);
    System.out.println(list);
  }


  /**
   * Method to handle the command to exit the app.
   */
  public void exitApp() {
    System.out.println("\nThank you for using Meteorite Catcher!\n");
    input.close();
  }


  /**
   * Displays a given error or invalid input message to the user.
   * @param msg The error message to be displayed.
   */
  public void invalidInputMessage(String msg) {
    System.out.println(msg);
  }

  public static void main(String[] args) {
    BackendPlaceholder backendPlaceholder = new BackendPlaceholder();
    Frontend frontend = new Frontend(backendPlaceholder);
    frontend.startMainLoop();
  }

}

