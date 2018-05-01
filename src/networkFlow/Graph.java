package networkFlow;


public interface Graph {
	
	/***
	 * Adds node to the graph
	 * @param name of node 
	 * @return true if successful or false if not
	 */
	public boolean addNode(String name);

	/***
	 * Adds an edge given a source and dest node names
	 * @param src node name 
	 * @param dest noe name
	 * @return true if successful or false if not
	 */
	public boolean addEdge(String src, String dest);
	
	/***
	 * Removes a node from the graph given the name
	 * @param name of node to remove
	 */
	public void removeNode(String name);
	
	/***
	 * Finds and removes an edge given the two nodes it connects
	 * @param src node name
	 * @param dest node name
	 */
	public void removeEdge(String src, String dest);
	
}
