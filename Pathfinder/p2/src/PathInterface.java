

import java.util.List;

/**
 * Interface for defining the structure of a path resulting from a shortest path search in a graph.
 * It is designed to store and allow retrieval of the sequence of nodes in the path,
 * the costs or times associated with traversing between consecutive nodes,
 * and the total cost or time of the entire path.
 *
 * @param <NodeType> The data type of the nodes in the graph (e.g., String, Integer, etc.).
 * @param <EdgeType> The data type of the edge weights in the graph,
 *                   which must be a subclass of Number (e.g., Integer, Double, etc.).
 */
public interface PathInterface<NodeType, EdgeType extends Number> {

  /**
   * Retrieves the path as an ordered list of nodes. The sequence of nodes represents the
   * traversal path from the starting node to the ending node.
   *
   * @return A List containing the nodes that make up the path, in the order they are to be traversed.
   */
  public List<String> getPath();

  /**
   * Retrieves the weights (such as times, distances, or costs) associated with each segment
   * of the path. A segment is defined as the section of the path between two consecutive nodes.
   *
   * @return A List containing the weights for each segment of the path, correlating to the order
   *         of nodes in the path.
   */
  public List<Double> getWalkingTimes();

  /**
   * Calculates and retrieves the total cost or time to traverse the path from start to end.
   * This is often the sum of all the weights of the segments in the path.
   *
   * @return The total cost or time as a double, representing the aggregated weight
   *         of traversing the entire path.
   */
  public Double getTotalTime();
}

