import java.util.ArrayList;

public interface BackendInterface{
  /**
   * load a data file specified at front end
   * @param fileName of the file
   */
  public boolean load(String fileName);

  /**
   * list the meteorites with the highest mass in the data set
   * */
  public ArrayList<MeteoriteInterface> maxMass();

  /**
   * list all meteorites with a mass between two specified thresholds
   * @param minMass minimum mass threshold
   * @param maxMass maximum mass threshold
   * */
  public ArrayList<MeteoriteInterface> massBtw(double minMass, double maxMass);

}
