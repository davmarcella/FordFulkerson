package graph;

import java.util.LinkedList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class DirectedGraph implements Graph{

	public List<Node> nodes;
	int x;
	
	public DirectedGraph(){
		nodes = new LinkedList<Node>();
		x = 0;
	}
	
	
	/***
	 * gets a node given the name
	 * @param name
	 * @return Node or null
	 */
	public Node getNode(String name){
		Node n = null;
		Iterator<Node> itr = nodes.iterator();
		//go through all the nodes and return when the names equal
		while(itr.hasNext()){
			n = itr.next();
			if(n.name.equals(name)){
				return n;
			}
		}
		return null;
	}
	
	
	/***
	 * adds a node given the name
	 */
	public boolean addNode(String name) {
		Node n = getNode(name);
		
		//make sure the node doesn't already exist
		if(n == null){
			n = new Node(name);
			nodes.add(n);
			return true;
		}
		else{
			System.out.println("error node " + name + " already exists");
			return false;
		}
	}

	

	/***
	 * adds an edge given the source and destination nodes
	 */
	public boolean addEdge(String src, String dest) {
		return addEdge(src, dest, 0);		
	}
	
	
	/***
	 * adds an edge given the source and destination nodes and cost
	 * @param src
	 * @param dest
	 * @param cost
	 */
	public boolean addEdge(String src, String dest, int cost) {
		Node srcNode = getNode(src);
		Node destNode = getNode(dest);
//		EdgeAttr e = new EdgeAttr(10);
		srcNode.addEdge(destNode);
//		destNode.addEdge(srcNode);
//		Edge e1 = srcNode.getEdge(destNode);
//		Edge e2 = destNode.getEdge(srcNode);
//		e1.sibling = e2;
//		e2.sibling = e1;
//		e1.attr = e;
//		e2.attr = e;
		
		
		
		
		
		
		
		
		//as long as the node isn't pointing to itself do your thing
		if(src.equals(dest)){
			//System.out.println("error source and destination are the same");
			return false;
		}
		else{
			//if the source or destination nodes don't exist create them and add them
			if(srcNode == null){
				srcNode = new Node(src);
				nodes.add(srcNode);
			}
			if(destNode == null){
				destNode = new Node(dest);
				nodes.add(destNode);
			}
			return srcNode.addEdge(destNode, cost);
		}
	}

	
	/***
	 * removes a node given the name
	 */
	public void removeNode(String name) {
		Node n = getNode(name);
		Iterator<Node> itr = nodes.iterator();
		//if the node exists in the first place
		if(n != null){
			//go through all the other nodes and delete any reference to itself
			while(itr.hasNext()){
				//Node n2 = itr.next();
				//n2.removeEdge(n);
				itr.next().removeEdge(n);
			}
			nodes.remove(n);
		}
		else{
			System.out.println("error node " +name+" doesn't exist");
		}
		
	}

	
	/***
	 * removes an edge given the source and destination nodes
	 */
	public void removeEdge(String src, String dest) {
		Node srcNode = getNode(src);
		Node destNode = getNode(dest);
		//as long as both nodes exist just remove the edge
		if(srcNode != null && destNode != null){
			srcNode.removeEdge(destNode);
		}
		else{
			System.out.println("There is no edge between node " + src + 
					" and node " + dest);
		}
		
	}
	
	
	/***
	 * displays the graphs adjacency list with/without the cost
	 * based on the value of 'displayCosts'
	 * @param displayCosts
	 */
	public void display(boolean displayCosts){
		String output = "";
		Iterator<Node> n_itr = nodes.iterator();
		Node n ;
	
		while(n_itr.hasNext()){
			n = n_itr.next();
			output += "\n" + n.toString()  + " --> ";
			Iterator<Edge> e_itr = n.edges.iterator();
			while(e_itr.hasNext()){
				Edge e = e_itr.next();
				if(displayCosts == true)
					output += e.toString() + "," + e.getCost() + " --> ";
				else
					output += e.toString() + " --> ";
			}
			output += " /";
		}
		output+= "\n";
		System.out.println(output);
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
	 * graph function that will return the hashmap with edge numbers
	 * @return HashMap
	 */
	public HashMap<Integer, Integer> getEdgeNums(){
		HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();
		int numEdges = 0;
		int num = 0;
		Node n;
		Iterator<Node> n_itr = nodes.iterator();
		
		while(n_itr.hasNext()){
			n = n_itr.next();
			numEdges = n.edges.size();
			if(numEdges > 0){
				if(h.containsKey(numEdges)){
					num = h.get(numEdges);
					h.put(numEdges, ++num);
				}
				else{
					h.put(numEdges, 1);
				}
			}
		}
		return h;
	}
	
	public void resetVisited() {
		Iterator<Node> itr = nodes.iterator();
		while (itr.hasNext()) {
			Node n = itr.next();
			n.visited = false;
		}
	}
	
	
	/***
	 * returns node with the largest number of connections
	 * @return Node
	 */
	public int getMaxFragment(){
		resetVisited();
		Iterator<Node> itr = nodes.iterator();
		int largest = 0;
		while (itr.hasNext()) {
			Node n = itr.next();
			if (n.visited == false) {
				int currentSize = n.fragmentSize();
				if (currentSize > largest)
					largest = currentSize;
			}
		}
		return largest;
	}
	

}
