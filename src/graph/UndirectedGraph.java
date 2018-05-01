package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import java.io.IOException;

public class UndirectedGraph {
	DirectedGraph g;
		
	public UndirectedGraph(){
		g = new DirectedGraph();
		
	}
	
	/***
	 * gets a node given the name
	 * @param name
	 * @return Node or null
	 */
	public Node getNode(String name){
		return g.getNode(name);
	}
	
	/***
	 * adds an edge given the two nodes it connects
	 * @param src
	 * @param dest
	 */
	public boolean addEdge(String src, String dest){
		return addEdge(src, dest, 0);
	}
	
	/***
	 * adds an edge given the two nodes it connects and the cost
	 * @param src
	 * @param dest
	 * @param cost
	 */
	public boolean addEdge(String src, String dest, int cost){
		return g.addEdge(dest, src, cost) && g.addEdge(src,  dest, cost);
	}
	/***
	 * removes an edge given the nodes it connects
	 * @param src
	 * @param dest
	 */
	public void removeEdge(String src, String dest){
		g.removeEdge(src, dest);
		g.removeEdge(dest, src);
	}
	
	/***
	 * adds a node given the name
	 * @param node
	 */
	public boolean addNode(String node){
		return g.addNode(node);
	}
	
	/***
	 * removes a node given the name
	 * @param node
	 */
	public void removeNode(String node){
		g.removeNode(node);
	}
	
	/***
	 * displays the graphs adjacency list with/without the cost depending
	 * on the value of 'displayCosts'
	 * @param displayCosts
	 */
	public void display(boolean displayCosts){
		g.display(displayCosts);
	}
	
	/***
	 * reads the graph given a filename
	 * @param fileName
	 * @throws IOException
	 */
	public void readGraph(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = "";
		while((line=reader.readLine()) != null){
			String [] items = line.trim().split(" ");
			
			switch(items.length){
				case 0:
					break;
				case 1:
					addNode(items[0]);
					break;
				case 2:
					addEdge(items[0], items[1]);
					break;
				case 3:
					addEdge(items[0], items[1], Integer.parseInt(items[2]));
					break;
			}
			
		}
		reader.close();
		
	}
	
	
	/***
	 * gets a hashmap of the all the different edgenumbers and corresponding number of nodes with that many edges
	 * @return Hashmap
	 */
	public HashMap<Integer, Integer> getEdgeNums(){
		return g.getEdgeNums();
	}
	
	/***
	 * returns node with the largest number of connections
	 * @return Node
	 */
	public int getMaxFragment(){
		return g.getMaxFragment();
	}
	
	public void resetVisited() {
		g.resetVisited();
	}

}
