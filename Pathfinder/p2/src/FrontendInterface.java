package Pathfinder.p2.src;

public interface FrontendInterface {
    // Constructor:
    // public Frontend(Scanner input, BackendInterface backend)
  
    /**
     * Prompts the user for a path for the data file and passes it to the backend to
     * read data. Does not throw a FileNotFoundException, handled by while loop that goes until
     * valid input is given or user quits.
     */
    public void loadData();
  
    /**
     * Retrieve statistics from the dataset and returns summary.
     *
     * @return Summary of dataset statistics, including number of buildings, number of edges, and
     * total walking time
     */
    public String getStatistics();
  
    /**
     * Determines the shortest path between two buildings (start and destination) and returns a
     * summary of the path.
     *
     * @return The shortest path between the start and destination, including all buildings along
     * the way, the walk time between each edge, and total time to walk from start to destination
     */
    public String getShortestPath();
  
    /**
     * Runs the program, including all sub commands such as getting the shortest path between two
     * buildings and getting summary statistics, and exits when the user quits the program.
     */
    public void run();
  
    /**
     * Exits the main loop of program
     */
    public void exit();
  }
  
  
  