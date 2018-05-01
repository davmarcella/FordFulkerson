package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Node {
	
	String name;
	List<Edge> edges = new ArrayList<Edge>();
	boolean visited = false;
	
	public Node(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	/***
	 * finds an edge given the destination node
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
	 * adds an edge given the destination node
	 * @param dest
	 */
	public boolean addEdge(Node dest){
		return addEdge(dest, 0);
	}
	
	
	/***
	 * adds an edge given the destination node and the cost
	 * @param dest
	 * @param cost
	 */
	public boolean addEdge(Node dest, int cost){
		Edge e = getEdge(dest);
		
		//make sure the edge doesn't already exist
		if(e == null){
			e = new Edge(dest, cost);
			edges.add(e);
			return true;
		}
		else{
			//System.out.println("error edge " + dest.toString()+ "-" + name +" already exists");
			return false;
		}	
	}
	
	
	/***
	 * removes an edge given the destination node
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
	 * deletes all the edges
	 */
	public void deleteAllEdges(){
		edges.clear();
	}
	
	
	/***
	 * returns the fragment size associated with that node 
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
