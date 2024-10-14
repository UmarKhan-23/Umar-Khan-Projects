import java.util.ArrayList;

/**
 * This is just a placeholder class for the actual Backend class that we would get from the
 * Backend Developer.
 */
public class BackendPlaceholder implements BackendInterface{

  //private IterableMultiKeySortedCollectionInterface<Meteorite> RBT;

  //public BackendPlaceholder(IterableMultiKeySortedCollectionInterface RBT) {
    // this.RBT = RBT;
  // }

  public BackendPlaceholder() {}

  /**
   * load a data file specified at front end
   * @param fileName of the file
   */
  public boolean load(String fileName) {
    return true;
  }

  /**
   * List the meteorites with the highest mass in the data set
   * */
  public ArrayList<MeteoriteInterface> maxMass() {
    ArrayList<MeteoriteInterface> list = new ArrayList<>();
    list.add((MeteoriteInterface) new Meteorite("Umar", 30.00, 20.00 ,
        true, 50));
    return list;
  }

  /**
   * List all meteorites with a mass between two given thresholds
   * @param minMass minimum mass threshold
   * @param maxMass maximum mass threshold
   * */
  public ArrayList<MeteoriteInterface> massBtw(double minMass, double maxMass) {
    ArrayList<MeteoriteInterface> list = new ArrayList<>();
    list.add((MeteoriteInterface) new Meteorite("Umar", 30.00, 20.00 ,
        true, 50));
    list.add((MeteoriteInterface) new Meteorite("Khan", 10.00, 10.00 ,
        false, 100));
    return list;
  }

}


