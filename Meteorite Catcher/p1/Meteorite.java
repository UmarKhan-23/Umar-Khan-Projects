/**
 * Meteorite class stores several information about one meteorite
 */
public class Meteorite{

    private String name;
    private double latitude;
    private double longtitude;
    private boolean fall;
    private double mass;
  
    /**
     * Meteorite class stores several information about one meteorite
     * @param name name of the meteorite
     * @param latitude latitude of where the meteorite is found
     * @param longitude longtitude of where the meteorite is found
     * @param fall whether the meteorite fell is witnessed
     * @param mass mass of the meteorite
     */
    public Meteorite(String name, double latitude, double longitude, boolean fall, double mass){
      this.name=name;
      this.latitude=latitude;
      this.longtitude=longitude;
      this.fall=fall;
      this.mass=mass;
    }
    /**
     * gets name of the Meteorite
     * @return name as a string
     */
    public String getName(){
      return this.name;
    }
  
    /**
     * gets the latitude coordinate of the Meteorite
     * @return latitude as a double
     */
    public double getLatitude(){
      return this.latitude;
    }
  
    /**
     * gets the longitude coordinate of the Meteorite
     * @return longitude as a double
     */
    public double getLongtitude(){
      return this.longtitude;
    }
  
    /**
     * gets whether the Meteorite fall was observed or was found after impact
     * @return "Fall" if fall is true; "Found" if fall is false
     */
    public String getFall(){
      if (this.fall) {
        return "Fall";
      }
      else {
        return "Found";
      }
    }
  
    /**
     * gets the mass of the Meteorite object
     * @return mass as double
     */
    public double getMass(){
      return this.mass;
    }
  
    public String toString(){
      return "Name: " + this.getName() + " Mass: " + this.getMass() + "g "  + this.getFall() + " Location: (" + String.format("%.6f", this.getLatitude()) + ", "
          + String.format("%.6f", this.getLongtitude()) + ")";
    }
  }
  