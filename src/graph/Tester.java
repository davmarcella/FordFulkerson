package graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class Tester {	
	
	//creates a random graph
	//creates all the nodes
	//creates all the edges
	public static UndirectedGraph createRandomGraph(int numNodes, int totalNumEdges/*per node*/){
		UndirectedGraph g = new UndirectedGraph();
		Random r = new Random();
		String src = Integer.toString(r.nextInt(numNodes));
		String dest = Integer.toString(r.nextInt(numNodes));
		
		//check to make sure the number of edges is valid for the number of nodes
		if(totalNumEdges > numNodes*(numNodes-1)/2){
			System.out.println("Too many edges for that many nodes");
			return null;
		}
		
		//create all the nodes
		for(int i = 0; i < numNodes; i++){
			g.addNode(Integer.toString(i));
		}
		
		//doesn't account for if the edge already exists
		//we might not want to because ex: numNodes = 3 and numEdges = 6. its not possible.
		//if we account for it, we would have to create some case to determine if then numEdges and numNodes is valid
		//otherwise we would end up in a loop of it consistently trying to add edges when its not possible
		for(int i = 0; i < totalNumEdges; i++){
			while(g.addEdge(src, dest) == false){
				src = Integer.toString(r.nextInt(numNodes));
				dest = Integer.toString(r.nextInt(numNodes));
			}
			g.addEdge(src, dest);
		}
		return g;
		
		
	}

	//creates a small world graph
	//creates all the nodes
	//creates all the edges based on number of connections per node (AAABBCCD)
	public static UndirectedGraph createSWGraph(int numNodes, int totalNumEdges){
		int numEdgesPerNode = totalNumEdges/numNodes;
		Map<Integer, Integer> m = new HashMap<Integer,Integer>();
		//because maps and arraylists use the same format, changing this wouldn't be any better. 
		//The syntax below would still be just as messy.
		UndirectedGraph g = new UndirectedGraph();
		Random r = new Random();
		String node = "";
		
		//check to make sure the number of edges is valid for the number of nodes
		if(totalNumEdges > numNodes*(numNodes-1)/2){
			System.out.println("Too many edges for that many nodes");
			return null;
		}
		
		//seed the function with a fully connected graph with numNodes = numEdgesPerNode
		int j = 0;
		g = createRandomGraph(numEdgesPerNode, numEdgesPerNode*(numEdgesPerNode-1)/2);
		j += g.g.nodes.size(); //i = number of nodes
		
		
		//put the edges in the hashmap
		//while i is less than double the amount of edges 
		//each edge is represented by 2 edges since it's undirected
		int i = 0;
		while( i < j*(j-1)/2*2){
			for(int x = 0; x < j; x++){
				for(int y = 0; y < j; y++){
					//m.put(x, i);
					System.out.println(x+ " " +i);
					i++;
				}
				
			}
		}

		
		//find a dest and source from dictionary 
		while (j < numNodes){
			g.addNode(j+"");
			
			//for each node, the number of edges you add = totalNumEdges/numNodes;
			for(int y = 0; y < numEdgesPerNode; y++){
				
				//choose a node randomly from the hashmap to connect to and put it in the hashmap
				node = Integer.toString(r.nextInt(m.size()-1));
				m.put(m.size(), m.get(Integer.parseInt(node)));
				
				
				//if edge == null, add edge
				//if edge != null, remove the two nodes from hashmap, choose new src and dest nodes and put them in hashmap
				//the addEdge function checks to make sure the src and dest are different
				while(g.addEdge(j+"", m.get(Integer.parseInt(node))+"")==false){// this isn't equal  to false when adding certain duplicates
					m.remove(m.size()-1);
					node=Integer.toString(r.nextInt(m.size()-1));
					m.put(m.size(), m.get(Integer.parseInt(node)));
				}

			}
			
			System.out.println("Edge " + j + " to " + m.get(Integer.parseInt(node)) + " added successfully");
		}
		return g;
	}
	
	//deletes a graph randomly
	//chooses a node randomly and deletes it
	public static List<Integer> destroyGraphRandom(UndirectedGraph g){
		List<Integer> nodesRemoved = new ArrayList<Integer>();
		int startNodes = g.g.nodes.size();
		String node;
		Node n;
		int numNodes = g.g.nodes.size();
		Random r = new Random();

		
		//get a random node, put it in the list and delete it from the graph
		for(int i = 0; i < startNodes; i++){
			//choose a node at random and set n = to that node
			node = Integer.toString(r.nextInt(numNodes));
			n = g.getNode(node);
			
			//if the node has already been removed choose a new one
			while(n == null){
				node = Integer.toString(r.nextInt(numNodes));
				n = g.getNode(node);
			}
			//add the node to the list and remove the node from the graph
			nodesRemoved.add(Integer.parseInt(node));
			g.removeNode(node);
			
		}
		
		return nodesRemoved;
		
	}
	
	//deletes a graph adversarially
	//chooses the node with the most connections and deletes it
	public static  List<Integer> destroyGraphAdv(UndirectedGraph g){
		List<Integer> nodesRemoved = new ArrayList<Integer>();
		int nde = g.getMaxFragment();
		Node n = g.getNode(nde+"");
		int startNodes = g.g.nodes.size();
		
		while(n!=null){
			nodesRemoved.add(Integer.parseInt(n.name));
			g.removeNode(n.name);
			nde = g.getMaxFragment();
			n = g.getNode(nde+"");
		}
		
		//go through all the remaining nodes and delete them
		int nodesLeft = startNodes - nodesRemoved.size();
		for(int i = 0; i < nodesLeft; i++){
			//using index 0 since you're deleting the first thing in the list
			n = g.g.nodes.get(0);
			nodesRemoved.add(Integer.parseInt(n.name));
			g.removeNode(n.name);
			
		}
		return nodesRemoved;
	}

 
	public static void main(String[] args) throws IOException {
		

		DirectedGraph g = new DirectedGraph();
		g.readGraph("graph.txt");
		g.display(false);
		
		


		
		
	}

}
