

import java.util.List;

/**
 * Represents a path in a graph, storing the sequence of nodes (path), the weights of each segment
 * (walking times), and the total weight (total time).
 *
 * @param <NodeType> The type of the nodes in the path.
 * @param <EdgeType> The type of the weights of the path segments, must extend Number.
 */
public class Path<NodeType, EdgeType extends Number> implements PathInterface {

  // List of nodes representing the path
  List<String> path;
  // List of weights (e.g., walking times) for each segment of the path
  List<Double> walkingTimes;
  // Total weight (e.g., total time) for the entire path
  Double totalTime;

  /**
   * Constructor for Path. Initializes a new Path with given nodes, segment weights, and total weight.
   *
   * @param path List of nodes in the path.
   * @param walkingTimes List of weights for each segment of the path.
   * @param totalTime The total weight for traversing the path.
   */
  public Path(List<String> path, List<Double> walkingTimes, Double totalTime) {
    this.path = path;
    this.walkingTimes = walkingTimes;
    this.totalTime = totalTime;
  }

  /**
   * Retrieves the sequence of nodes in the path.
   *
   * @return A List of nodes representing the path.
   */
  public List<String> getPath() {
    return path;
  }

  /**
   * Retrieves the weights (such as times, distances, or costs) associated with each segment
   * of the path. A segment is defined as the section of the path between two consecutive nodes.
   *
   * @return A List containing the weights for each segment of the path, correlating to the order
   *         of nodes in the path.
   */
  public List<Double> getWalkingTimes(){
    return walkingTimes;
  }

  /**
   * Calculates and retrieves the total cost or time to traverse the path from start to end.
   * This is often the sum of all the weights of the segments in the path.
   *
   * @return The total cost or time as a double, representing the aggregated weight
   *         of traversing the entire path.
   */
  public Double getTotalTime() {
    return totalTime;
  }
}

