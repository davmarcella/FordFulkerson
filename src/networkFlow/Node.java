package networkFlow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Node {
	
	public String name;
	List<Edge> edges = new ArrayList<Edge>();
	boolean visited = false;
	
	/***
	 * Constructor of Node 
	 * @param name of node
	 */
	public Node(String name){
		this.name = name;
	}
	
	/***
	 * Prints out the name
	 */
	public String toString(){
		return name;
	}
	
	/***
	 * Finds an edge given the destination node
	 * @param dest
	 * @return Edge or null
	 */
	public Edge getEdge(Node dest){
		Edge e = null;
		Iterator<Edge> itr = edges.iterator();
		while(itr.hasNext()){
			e = itr.next();
			if(e.dest == dest){
				return e;
			}
		}
		return null;
	}
	
	
	/***
	 * adds an edge given the destination node and the cost
	 * @param dest
	 * @param cost
	 * @return Edge or null
	 */
	public Edge addEdge(Node dest, int capacity){
		return addEdge(dest,capacity,false);
	}
	
	
	/***
	 * Adds an edge given the dest node, capacity, and whether or not it's a backedge (generated edge)
	 * @param dest node
	 * @param capacity of edge
	 * @param backEdge boolean whether or not this was a generated backEdge
	 * @return
	 */
	public Edge addEdge(Node dest, int capacity, boolean backEdge){
		Edge e = getEdge(dest);

		//make sure the edge doesn't already exist
		if(e == null){
			e = new Edge(dest, capacity, backEdge);
			edges.add(e);
			return e;
		}
		else{
			System.out.println("error edge " + dest.toString()+ "-" + name +" already exists");
			return null;
		}	
	}

	
	/***
	 * Removes an edge given the destination node
	 * @param dest
	 */
	public void removeEdge(Node dest){
		Edge e = getEdge(dest);
		
		//make sure the edge exists
		if(e != null){
			edges.remove(e);
		}
	}
	
	
	/***
	 * Deletes all the edges
	 */
	public void deleteAllEdges(){
		edges.clear();
	}

	
	/***
	 * Returns the fragment size associated with that node 
	 * @return int fragmentSize
	 */
	public int fragmentSize() {
		visited = true;
		int total = 1;
		Edge currentEdge;
		Iterator<Edge> itr = edges.iterator();
		while (itr.hasNext()) {
			currentEdge= itr.next();
			if ( !currentEdge.dest.visited )
				total += currentEdge.dest.fragmentSize();
		}
		return total;
	}

}
