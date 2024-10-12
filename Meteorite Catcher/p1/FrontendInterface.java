import java.io.FileNotFoundException;

/**
 * Interface for the front end for the Meteorite Catcher application.
 */
public interface FrontendInterface {

  /*

  // Constructor that accepts a reference to the backend and a Scanner instance.
  public Meteorite(BackendInterface backend)

  */


  /**
   * Method to start the main command loop for the user.
   */
  public void startMainLoop();


  /**
   * Method to handle the command to display the menus.
   */
  public void displayMenus();


  /**
   * Method to specify (and load) a data file.
   * @param file name of the file
   * @throws FileNotFoundException if the filename is not found.
   */
  public void loadDataFile(String file) throws FileNotFoundException;


  /**
   * Method to handle the command to list the meteorites with the highest mass.
   */
  public void listHighestMassMeteorites();


  /**
   * Method to handle the command to list all the meteorites with mass between two specified
   * thresholds.
   * @param lowerThreshold lower boundary of the given mass range.
   * @param upperThreshold upper boundary of the given mass range.
   */
  public void listAllMeteoritesWithinMassRange(double lowerThreshold, double upperThreshold);


  /**
   * Method to handle the command to exit the app.
   */
  public void exitApp();


  /**
   * Method to handle invalid input and give suitable feedback.
   */
  public void invalidInputMessage(String msg);

}



