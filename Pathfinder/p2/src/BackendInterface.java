import java.io.FileNotFoundException;

/**
 * Interface defining the backend operations for graph-related functionalities.
 *
 * @param <NodeType> The type of the nodes in the graph.
 * @param <EdgeType> The type of the weights on the edges in the graph. It extends the Number class to
 *                   use numerical operations on the weights.
 */
public interface BackendInterface<NodeType, EdgeType extends Number>  {

  /**
   * Reads data from a specified file and constructs the graph based on the data. This method is
   * expected to throw a FileNotFoundException if the file does not exist.
   * @param fileName Name of the file
   * @throws FileNotFoundException if the file with given name is not found
   */
  public void readDataFromFile(String fileName) throws FileNotFoundException;

  /**
   * Finds the shortest path between two nodes in the graph, identified by their data.
   * The start and end points are specified by the user.
   *
   * @param startPoint The data identifying the starting node of the path.
   * @param endPoint The data identifying the ending node of the path.
   * @return Path instance containing the nodes and edge weights along the shortest path.
   */
  public PathInterface<NodeType, EdgeType> getShortestPath(String startPoint, String endPoint);

  /**
   * Retrieves statistical information about the graph, such as the number of nodes,
   * the number of edges, and other relevant data.
   *
   * @return A string containing the statistical data about the graph.
   */
  public String getStatistics();

}

