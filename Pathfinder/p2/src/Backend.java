import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Backend class for handling graph-based operations.
 * Utilizes a DijkstraGraph for operations like finding the shortest path and
 * retrieving graph statistics. Generic to allow for any node and edge weight types,
 * with the constraint that edge weights must be a subclass of Number.
 *
 * @param <NodeType> The type of the nodes in the graph.
 * @param <EdgeType> The type of the edge weights in the graph, must extend Number.
 */
public class Backend<NodeType, EdgeType extends Number> implements BackendInterface {

  // Instance of a graph for graph operations
  GraphADT<String, Double> graph;
  // Variable to store the total time of all edges
  double totalTime = 0;

  /**
   * Constructor for Backend.
   * Initializes the Backend with a specific graph instance for operations.
   *
   * @param graph The DijkstraGraph instance to be used.
   */
  public Backend(GraphADT<String, Double> graph) {
    this.graph = graph;
  }

  /**
   * Reads and loads data from a file into the graph.
   * Parses the file to construct nodes and edges in the graph.
   *
   * @param fileName The name of the file to read data from.
   * @throws FileNotFoundException if the file is not found.
   */
  public void readDataFromFile(String fileName) throws FileNotFoundException {
    // Scanner for reading the file
    Scanner input;
    try {
      input = new Scanner(new File(fileName));
    } catch (FileNotFoundException e) {
      // Propagate the exception if file is not found
      throw new FileNotFoundException("File is not there");
    }

    // Patterns for parsing nodes and edges from the file
    Pattern nodePattern = Pattern.compile("\"(\\S+(\\s\\S+)*)\" -- \"(\\S+(\\s\\S+)*)\"");
    Pattern edgePattern = Pattern.compile("\\d+\\.\\d+");

    // Variables to hold node and edge data
    String startNode = "";
    String endNode = "";
    double weight = 0;

    while (input.hasNextLine()) {
      String line = input.nextLine().trim();

      // Skip braces, typically used in graphviz DOT files
      if (line.contains("{") || line.contains("}"))
        continue;

      // Match and extract nodes and edges
      Matcher nodeMatcher = nodePattern.matcher(line);
      Matcher edgeMatcher = edgePattern.matcher(line);
      if (nodeMatcher.find()) {
        // Extract node names
        startNode = nodeMatcher.group(1);
        endNode = nodeMatcher.group(3);
        // Insert nodes into the graph if they don't exist
        if (!graph.containsNode(startNode))
          graph.insertNode(startNode);
        if (!graph.containsNode(endNode))
          graph.insertNode(endNode);
      }

      if (edgeMatcher.find()) {
        // Extract and parse the edge weight
        weight = Double.parseDouble(edgeMatcher.group());
      }
      // Insert edge into the graph if it doesn't exist
      if (!graph.containsEdge(startNode, endNode)) {
        graph.insertEdge(startNode, endNode, weight);
      }
      // Accumulate the total weight
      totalTime += weight;
    }
  }

  /**
   * Finds and returns the shortest path between two nodes.
   * Utilizes Dijkstra's algorithm via the graph object.
   *
   * @param startPoint The starting node identifier.
   * @param endPoint The ending node identifier.
   * @return PathInterface representing the shortest path.
   */
  public PathInterface<String, Double> getShortestPath(String startPoint, String endPoint) {
    if(!graph.containsNode(startPoint) || !graph.containsNode(endPoint)) {
      throw new NoSuchElementException("Nodes are not present in the graph");
    }
    // Find the shortest path data and cost
    List<String> shortestPath = graph.shortestPathData(startPoint, endPoint);
    Double totalTime = graph.shortestPathCost(startPoint, endPoint);

    // List to store walking times between nodes
    List<Double> walkingTimes = new ArrayList<>();
    // Calculate walking times for each path segment
    for (int i = 0; i < shortestPath.size() - 1; i++ )  {
      walkingTimes.add(graph.getEdge(shortestPath.get(i), shortestPath.get(i + 1)));
    }

    // Return the path with its associated data
    return new Path(shortestPath, walkingTimes, totalTime);
  }

  /**
   * Retrieves and returns statistical information about the graph.
   * Includes details like number of nodes, edges, and total walking time.
   *
   * @return String containing statistical information about the graph.
   */
  public String getStatistics() {
    // Construct and return the statistics string
    return graph.getNodeCount() + "," + graph.getEdgeCount() + "," + totalTime + ",";
  }


  /**
   * The main method for the PathFinder application. This method initializes a backend and a new
   * frontend instance. And then it runs the frontend
   *
   * @param args unused
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Backend<String, Integer> backend = new Backend<>(new DijkstraGraph<String, Double>(new PlaceholderMap<>()));
    Frontend frontend = new Frontend(scanner, backend);
    frontend.run(); //run the application
  }
}
