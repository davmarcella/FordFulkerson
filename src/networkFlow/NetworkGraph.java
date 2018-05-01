package networkFlow;

import java.util.List;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/***
 * Network graph is a specially designed undirected graph to specifically demonstrate a Network Flow problem
 * 
 * @author David
 * @version 1.0
 * @since 2017-05-17
 *
 */
public class NetworkGraph implements Graph{
	
	
	public List<Node> nodes;
	//path temp bottleneck variable used for DFS and finding a new path
	public Stack<Node> path = new Stack<Node>();
	int tempBottleneck = 0;
	
	
	/***
	 * Constructor for NetworkGraph. Initializes nodes Linked List
	 */
	public NetworkGraph(){
		nodes = new LinkedList<Node>();
	}
	
	
	/***
	 * Gets a node given the name
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
	 * Adds a node given the name
	 * @param name of node
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
	 * Calls alternate addEdge function with a capacity of 0
	 * @param src node 
	 * @param dest node
	 */
	public boolean addEdge(String src, String dest){
		return addEdge(src, dest, 0);
	}
	
	
	/***
	 * Adds an edge given the source and destination nodes and capacity
	 * @param src node
	 * @param dest node
	 * @param capacity of edge
	 * @return true if successful, false if not 
	 */
	public boolean addEdge(String src, String dest, int capacity) {
		Node srcNode = getNode(src);
		Node destNode = getNode(dest);
		
		//as long as the node isn't pointing to itself do your thing
		if(src.equals(dest)){
			System.out.println("error source and destination are the same");
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
			
			Edge frontEdge = srcNode.addEdge(destNode, capacity, false);
			Edge backEdge = destNode.addEdge(srcNode, capacity, true);
			if(frontEdge != null && backEdge != null){
				frontEdge.associateEdge = backEdge;
				backEdge.associateEdge = frontEdge;
				return true;
			}
			else{
				return false;
			}
			
			
			
			
		}
	}
	
	
	/***
	 * Removes a node given the name
	 * @param name of node to remove
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
	 * Removes an edge given the source and destination nodes
	 * @param src node
	 * @param dest node
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
	 * Displays the graphs adjacency list with/without the cost
	 * based on the boolean value of 'displayCosts'
	 * @param displayCapacity boolean
	 */
	public void display(boolean displayCapacity){
		String output = "";
		Iterator<Node> n_itr = nodes.iterator();
		Node n ;
	
		while(n_itr.hasNext()){
			n = n_itr.next();
			output += "\n" + n.toString()  + " --> ";
			Iterator<Edge> e_itr = n.edges.iterator();
			while(e_itr.hasNext()){
				Edge e = e_itr.next();
				if(displayCapacity == true)
					output += e.toString() + "["+e.getFlow()+"] --> ";
				else
					output += e.toString() + " --> ";
			}
			output += " /";
		}
		output+= "\n";
		System.out.println(output);
	}
	
	
	/***
	 * Generates a graphvis generated pdf file
	 * @param displayCapacity whether or not to display the capacity
	 * @param fileName of .dot and .pdf file
	 * @throws IOException if cannot write .dot file
	 * @throws InterruptedException if cannot execute command to generate pdf
	 */
	public void displayGraphVis(boolean displayCapacity, String fileName) throws IOException, InterruptedException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".dot"));
		Iterator<Node> n_itr = nodes.iterator();
		Node n ;
		//create .dot file
		String file = "digraph d { \n";
		while(n_itr.hasNext()){
			n = n_itr.next();
			Iterator<Edge> e_itr = n.edges.iterator();
			while(e_itr.hasNext()){
				Edge e = e_itr.next();
				if(e.backEdge == false){
					file += "\t" + n.toString() + " -> ";
					if(displayCapacity == true)
						file += e.toString() + " [label=\"" + (e.getCapacity()-e.getFlow()) + "/" + (e.associateEdge.getCapacity()) + "\"];\n";
					else
						file += e.toString() + ";\n";
				}
			}
		}
		file+= "}";
		//save the .dot file
		writer.write(file);
		System.out.println(file);
		writer.close();
		//execute command to convert .dot file into .pdf
		String command = "/usr/local/bin/dot -Tpdf " + fileName + ".dot -o " + fileName + ".pdf";
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(command);
		p.waitFor();
	}
	
	
	/***
	 * Reads the graph given a filename String
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
	 * Resets all the nodes boolean visited property to false
	 */
	public void resetVisited(){
		Iterator<Node> list = nodes.iterator();
		while(list.hasNext()){
			Node n = list.next();
			n.visited = false;
		}
		
	}
	
	
	/***
	 * Adds units of flow to an edge given a source and destination
	 * The units of flow is subtracted from the edge's capacity
	 * @param src node
	 * @param dest node
	 * @param units of flow to add to edge
	 */
	public void addFlow(String src, String dest, int units){
		Edge e = getNode(src).getEdge(getNode(dest));
		e.addFlow(units);
		if(src == "2" && dest == "1"){
			System.out.println("blasoidhas");
		}
	}
	
	
	/***
	 * Removes units of flow from an edge given a source and destination
	 * The units of flow will be added to the edge's capacity
	 * @param src node
	 * @param dest node
	 * @param units of flow to remove from edge 
	 */
	public void removeFlow(String src, String dest, int units){
		Edge e = getNode(src).getEdge(getNode(dest));
		e.removeFlow(units);
	}
	
	
	/***
	 * Finds a path given a source and dest node uses DFS
	 * Adds nodes to NetworkGraph's Stack<Node> path to create path
	 * While finding path, also keeps track of bottleneck and returns value
	 * @param src node 
	 * @param dest node
	 * @param maxCapac current max capacity to keep track of bottleneck
	 * @return bottleneck of path or null
	 */
	public Integer find(Node src, Node dest, int maxCapac){
		if(src == dest){
			return maxCapac;
		}
		else{
			int maxCapacity = maxCapac;
			src.visited = true;
			//System.out.println(src.name + " has been visisted");
			
			for(int i = 0; i < src.edges.size(); i++){
				Edge e = src.edges.get(i);
				Node n = e.dest;
				
				//if we haven't seen the node
				if(n.visited != true){
					//if there is available flow on that edge
					if(e.getCapacity() - e.getFlow() > 0){
						//check for a new bottleneck
						if(e.getCapacity()-e.getFlow() < maxCapac){
							maxCapacity = e.getCapacity() - e.getFlow();
							//System.out.println("new max\t" + maxCapacity + "\ton node "+ e.dest.name);
						}
						//System.out.println("calling on node "+ n.name + " with a maxCapacity of "+maxCapacity);
						//keeps going from 1 to 3 to 1
						Integer max = find(n, dest, maxCapacity);
						n.visited = true;
						//System.out.println("max "+ max);
						if(max != null){
							path.add(n);
							return max;
						}
					}
				}
				
			}
		}
		return null;
	}
	
	
	/***
	 * Given a start node and dest node, hasPath calls find() which uses DFS to determine if there is a path
	 * from src to dest. Returns a stack of the path or null
	 * @param start node
	 * @param dest node
	 * @return Stack<Node> path or null
	 */
	public Stack<Node> hasPath(Node start, Node dest){
		//add startNode to the path and set visited = true
		start.visited = true;
		
		//finds the path and returns the bottleneck
		//starts with max integer value; any time it encounters smaller number that becomes bottleneck 
		Integer bottleneck = find(start, dest, Integer.MAX_VALUE);
		//finally add the start node to the path
		path.add(start);
		Stack<Node> returnPath = null;
		//make sure the destination was reached
		if(dest.visited){
			returnPath = path;
		}
		
		//reset NetworkGraph's Stack<Node> path and the visited boolean for each node
		path = new Stack<Node>();
		resetVisited();
		
		if(returnPath != null){
			tempBottleneck = bottleneck; //might be pointless

			//then apply the bottleneck to each node
			for(int i = returnPath.size()-1; i>0; i--){
				Node sourceN = returnPath.get(i);
				Node destN = returnPath.get(i-1);
				Edge e = sourceN.getEdge(destN); 
				
				e.addFlow(bottleneck); //apply the bottleneck
			}
		}
		return returnPath;
	}
	
//	public boolean isCyclic(){
//		
//		
//	}
	
	/***
	 * Performs Ford Fulkerson max flow algorithm on the graph given a start node and dest node
	 * @param startNode 
	 * @param dstNode
	 * @return max flow
	 */
	public int fulkerson(String startNode, String dstNode){
		Node strtNode = getNode(startNode);
		Node destNode = getNode(dstNode);
		int maxFlow = 0;
		
		//adds the start node to the queue and sets visited to true
		
		Stack<Node> path = new Stack<Node>();
		path = hasPath(strtNode,destNode);
		//temp bottlneck
		int bottleneck = Integer.MAX_VALUE;
		while(path != null){
			maxFlow+=tempBottleneck;
			//get next path and reset the bottleneck
			path = hasPath(strtNode, destNode);
		}
		
		return maxFlow;
		
		
	}
	
		
	public static void main(String[] args) {
		NetworkGraph ng = new NetworkGraph();

		//this will run fulkers
		try{
			ng.readGraph("fulkersonKiller.txt");
		}catch(IOException e){
			System.out.println("Error opening file\n" +e);
		}
		
		try{
			ng.displayGraphVis(true, "beforeFulkerson");
			System.out.println("Max Flow: " + ng.fulkerson("0", "3"));
			ng.displayGraphVis(true, "afterFulkerson");
		}catch(Exception e){
			System.out.println("Error writing to file\n" +e);
		}
		
		
		
		
		//passes
		//CP3 4.26.2 (t-lim)
		//https://visualgo.net/en/maxflow
//		ng.addEdge("0","3",3);
//		ng.addEdge("0","2",5);
//		ng.addEdge("2","3",3);
//		ng.addEdge("2","1",3);
//		ng.addEdge("3","1",5);
//		ng.addEdge("1","4",4);
//		ng.addEdge("2","4",3);
//		ng.display(true);
//		System.out.println("Max Flow: " + ng.fulkerson("0","4"));
//		ng.display(true);
		
		//passes
		//CP3 4.26.2 (t-lim)
		//https://visualgo.net/en/maxflow
//		ng.addEdge("0", "2", 5);
//		ng.addEdge("0", "3", 3);
//		ng.addEdge("2", "3", 3);
//		ng.addEdge("3", "1", 5);
//		ng.addEdge("2", "1", 3);
//		ng.addEdge("1", "4", 7);
//		ng.addEdge("2", "4", 3);
//		ng.display(true);
//		System.out.println("Max Flow: " + ng.fulkerson("0", "4"));
//		ng.display(true);
		
		
		//passes
//		//dinic showcase 
//		//https://visualgo.net/en/maxflow
//		ng.addEdge("0","1",5);
//		ng.addEdge("0","2",8);
//		ng.addEdge("0","3",3);
//		ng.addEdge("0","4",3);
//		ng.addEdge("0","5",7);
//		ng.addEdge("1","9",4);
//		ng.addEdge("2","9",9);
//		ng.addEdge("3","6",1);
//		ng.addEdge("4","7",4);
//		ng.addEdge("5","8",6);
//		ng.addEdge("6","9",1);
//		ng.addEdge("7","9",6);
//		ng.addEdge("8","9",5);
//		ng.addEdge("0","9",7);
//		ng.display(true);
//		System.out.println(ng.fulkerson("0","9"));
//		ng.display(true);
		
		
		//first examples 
//		ng.addEdge("0","1");
//		ng.addEdge("0","2");
//		ng.addEdge("1","2");
//		ng.addEdge("1","3");
//		ng.addEdge("2","3");
//		ng.addEdge("3","5");
//		ng.addEdge("3","6");
//		ng.addEdge("2","4");
	




	}

}
