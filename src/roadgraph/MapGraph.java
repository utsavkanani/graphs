/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import geography.GeographicPoint;
import geography.RoadSegment;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	//Map of GeographicPoint to RoadNode in order to map geographic points
	private Map<GeographicPoint, RoadNode> nodes = null;
	// list of all edges in the graph
	private List<RoadEdge> edges = null;
	//This variable is used to maintain the list of neighbors for each GeographicPoint/node added to the graph.
	private Map<GeographicPoint, ArrayList<GeographicPoint>> adjListsMap;

	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
	    nodes = new HashMap<GeographicPoint, RoadNode>();
	    edges = new ArrayList<RoadEdge>();
	    adjListsMap = new HashMap<GeographicPoint, ArrayList<GeographicPoint>>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return nodes != null ? nodes.size() : 0;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
	    Set<GeographicPoint> result = new HashSet<GeographicPoint>();
		//TODO: Implement this method in WEEK 2
	    for(RoadNode node : nodes.values()) {
	        result.add(node.getLocation());
	    }
	    return result;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return edges != null ? edges.size() : 0;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
	    if(location == null || nodes.containsKey(location))
	        return false;
	    else {
	        RoadNode tmp = new RoadNode (location, new ArrayList<RoadEdge>());
	        nodes.put(location, tmp);
	        ArrayList<GeographicPoint> neighbors = new ArrayList<GeographicPoint>();
	        adjListsMap.put(location,  neighbors);
	        return true;
	    }

	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		if(null == from || null == to || null == roadName || null == roadType 
		        || length < 0 || !nodes.containsKey(from) 
		        || !nodes.containsKey(to))
		    throw new IllegalArgumentException("Either points have not already been added as nodes to "
		            + "the graph or one of the  the arguments is null or length is less than 0.");
		RoadEdge tmpEdge = new RoadEdge(from, to, roadName, roadType, length);
		edges.add(tmpEdge);
		nodes.get(from).addEdge(tmpEdge);
	    (adjListsMap.get(from)).add(to);
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		Queue<GeographicPoint> searchQueue = new LinkedBlockingQueue<GeographicPoint>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		LinkedList<GeographicPoint> result = new LinkedList<GeographicPoint>();
		if(null == start || null == goal) throw new IllegalArgumentException();
		
		searchQueue.add(start);
        visited.add(start);
        GeographicPoint curr = null;
		
		while(!searchQueue.isEmpty()) {
		    curr = searchQueue.remove();
		    nodeSearched.accept(curr);
		    if(curr.equals(goal)) break;
		    for(GeographicPoint neighbor :getUnvisitedNeighbors(curr, visited)) {
		        visited.add(neighbor);
		        searchQueue.add(neighbor);
		        parentMap.put(neighbor, curr);
		        
		    }
		      
		}
		
		if(curr == null || !curr.equals(goal)) return null;
		
		for(GeographicPoint node = goal; node != null; node = parentMap.get(node)) {
		    result.push(node);
		}
		
		return result;
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

	}
	
	
	private List<GeographicPoint> getUnvisitedNeighbors(GeographicPoint node, Set<GeographicPoint>visited) {
	    List<GeographicPoint> result = new ArrayList<GeographicPoint>();
	    for(GeographicPoint n : this.adjListsMap.get(node)) {
	        if(!visited.contains(n)) {
	            result.add(n);
	        }
	    }
	    
	    return result;
	    
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
