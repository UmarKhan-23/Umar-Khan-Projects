

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode
   * contains data about one specific path between the start node and another
   * node in the graph. The final node in this path is stored in its node
   * field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor
   * field (this field is null within the SearchNode containing the starting
   * node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost
   * SearchNode has the highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   * @param map the map that the graph uses to map a data object to the node
   *        object it is stored in
   */
  public DijkstraGraph(MapADT<NodeType, Node> map) {
    super(map);
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path
   * between the provided start and end locations. The SearchNode that is returned by this
   * method represents the end of the shortest path that is found: its cost is the cost of
   * that shortest path, and the nodes linked together through the predecessor references
   * represent all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *                                or when either start or end data do not
   *                                correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {

    //checks if any of the nodes provided are empty and if hte
    if (!containsNode(start) || !containsNode(end) || start == null || end == null) {
      throw new NoSuchElementException("Start or end node not found in the graph.");
    }
    PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>(); //make a priorityQueue to
    // store SearchNodes
    MapADT<NodeType, SearchNode> visitedNode = new PlaceholderMap<>();
    SearchNode startNode = new SearchNode(nodes.get(start), 0, null);

    priorityQueue.add(startNode); 

    while (!priorityQueue.isEmpty()) {
      SearchNode current = priorityQueue.poll();

      // Check if the current node is the end node
      if (current.node.data.equals(end)) {
        return current; // Found the shortest path
      }

      // Skip if the current node is already visited
      if (visitedNode.containsKey(current.node.data)) {
        continue;
      }

      // Mark the current node as visited
      visitedNode.put(current.node.data, current);

      // Explore each edge leaving the current node
      for (Edge edge : current.node.edgesLeaving) {
        NodeType successorData = edge.successor.data;

        // Only consider unvisited successors
        if (!visitedNode.containsKey(successorData)) {
          double newPathCost = current.cost + edge.data.doubleValue();
          SearchNode node = new SearchNode(edge.successor, newPathCost, current);
          priorityQueue.add(node);
        }
      }
    }

    // Throw an exception if no path is found
    throw new NoSuchElementException("No path from start to end is found");
  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses
   * Dijkstra's shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // Initialize the linked list to store the path
    LinkedList<NodeType> path = new LinkedList<>();

    // Compute the shortest path from start to end
    SearchNode current = computeShortestPath(start, end);

    // Backtrack from the end node to the start node
    while (current != null) {
      // Add each node to the front of the list
      path.addFirst(current.node.data);
      current = current.predecessor;
    }

    return path;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest
   * path from the node containing the start data to the node containing the
   * end data. This method uses Dijkstra's shortest path algorithm to find
   * this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    try {
      return computeShortestPath(start, end).cost;
    }
    catch (Exception e) {
      return -1.0;
    }
  }

  // TODO: implement 3+ tests in step 4.1

  /**
   * Test method for verifying the shortest path computation from node D to I.
   * It creates a graph with predefined nodes and edges, computes the shortest path from D to I,
   * and then checks if the computed path matches the expected one.
   */
  @Test
  public void test1(){
    // Create a new graph with String nodes and Integer edge weights
    DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    graph.insertNode("A"); // Insert nodes into the graph
    graph.insertNode("B");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertNode("G");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("M");
    graph.insertEdge("A", "M", 5);  // Insert edges between nodes with their respective weights
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("M", "E", 3);
    ArrayList<String> pathData = new ArrayList<>(); // ArrayList to store the path data
    // (nodes in the shortest path)
    DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("D", "I");
    while (current != null) {
      pathData.add(0, current.node.data);
      current = current.predecessor;
    }
    Assertions.assertEquals(17, graph.shortestPathCost("D", "I"));
    Assertions.assertEquals("[D, A, H, I]", pathData.toString());
  }

  /**
   * Test method for verifying the shortest path and its cost from node H to E.
   * This method sets up a graph, computes the shortest path from H to E,
   * and asserts both the correctness of the path and the total cost of that path.
   */
  @Test
  public void test2(){
    // Create a new graph with String nodes and Integer edge weights
    DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    graph.insertNode("A"); // Insert nodes into the graph
    graph.insertNode("B");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertNode("G");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("M");
    graph.insertEdge("A", "M", 5);  // Insert edges between nodes with their respective weights
    graph.insertEdge("A", "B", 1);
    graph.insertEdge("A", "H", 8);
    graph.insertEdge("B", "M", 3);
    graph.insertEdge("H", "B", 6);
    graph.insertEdge("H", "I", 2);
    graph.insertEdge("I", "H", 2);
    graph.insertEdge("I", "D", 1);
    graph.insertEdge("I", "L", 5);
    graph.insertEdge("D", "G", 2);
    graph.insertEdge("D", "A", 7);
    graph.insertEdge("G", "L", 7);
    graph.insertEdge("F", "G", 9);
    graph.insertEdge("M", "F", 4);
    graph.insertEdge("M", "E", 3);
    ArrayList<String> pathData = new ArrayList<>(); // ArrayList to store the path data
    // (nodes in the shortest path)
    DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("H", "E");
    while (current != null) {
     pathData.add(0, current.node.data);
     current = current.predecessor;
    }
    Assertions.assertEquals(12, graph.shortestPathCost("H", "E"));
    Assertions.assertEquals("[H, B, M, E]", pathData.toString());
  }


  /**
   * Test method for verifying the behavior of the graph when no path exists.
   * The test creates a graph with nodes but without edges, thus no path should exist between any two nodes.
   * It then checks if the path from H to E is non-existent and if the cost associated with this non-existent path is 0.
   */
  @Test
  public void test3() {
    // Create a new graph with String nodes and Integer edge weights
    DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    graph.insertNode("A"); // Insert nodes into the graph
    graph.insertNode("B");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertNode("G");
    graph.insertNode("H");
    graph.insertNode("I");
    graph.insertNode("L");
    graph.insertNode("M"); // ArrayList to store the path data
    // (nodes in the shortest path)

    Assertions.assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("H", "E"));

  }

}
