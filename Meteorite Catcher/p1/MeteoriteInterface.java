public interface MeteoriteInterface {
    /**
     * gets name of the Meteorite
     * @return name as a string
     */
    public String getName();
  
    /**
     * gets the latitude coordinate of the Meteorite
     * @return latitude as a double
     */
    public double getLatitude();
  
    /**
     * gets the longitude coordinate of the Meteorite
     * @return longitude as a double
     */
    public double getLongtitude();
  
    /**
     * gets whether the Meteorite fall was observed or was found after impact
     * @return true if fall was observed, false if was found after impact
     */
    public boolean getFall();
  
    /**
     * gets the mass of the Meteorite object
     * @return mass as  double
     */
    public double getMass();
  }
  
  
  