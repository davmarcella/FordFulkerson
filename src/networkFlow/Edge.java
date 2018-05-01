package networkFlow;

public class Edge {
	Node dest;
	boolean backEdge;
	Edge associateEdge;
	int capacity;
	int flow;


	/***
	 * Constructor for Edge
	 * If you add edge from A --> B then because of how Ford Fulkerson works, it will generate a 'backEdge' from B --> A
	 * BackEdge param just helps determine if it was a generated Edge or not
	 * @param dest node
	 * @param capacity of edge
	 * @param backEdge whether or not it's a generated Edge
	 */
	public Edge(Node dest, int capacity, boolean backEdge){
		this.dest = dest;
		this.capacity = capacity;
		if(backEdge)
			this.capacity = 0;
		this.backEdge = backEdge;
		flow = 0;
	}
	
	
	/***
	 * @return the current flow of the edge
	 */
	public int getFlow(){
		return flow;
	}
	
	
	/***
	 * Removes specific number of units of flow
	 * @param units of flow to remove
	 */
	public void removeFlow(int units){
		if((flow - units) >= 0){
			flow -= units;
			if(associateEdge.backEdge == true){
				if(flow == 0){
					associateEdge.flow = 0;
				}
				else{
					associateEdge.flow = capacity-flow;
				}
			}
		}
		else{
			System.out.println("cannot remove "+units+" units of flow with an existing flow of "+flow);
		}
		
	}
	
	
	/***
	 * Adds specific number of units of flow
	 * @param units of flow to add
	 */
	public void addFlow(int units){	
		if((flow + units) <= capacity){
			flow += units;
			if(associateEdge.backEdge == true){
				associateEdge.capacity = flow;
			}
		}
		else{
			System.out.println("overload on node "+dest.name);
			System.out.println("already "+flow+" units. Can't add "+units+" with a capacity of " +capacity);
		}
	}
	
	
	/***
	 * @return the destination node's name
	 */
	public String toString(){
		return dest.toString();
	}
	
	
	/***
	 * Returns the cost
	 * @return capacity
	 */
	public int getCapacity(){
		return capacity;
	}
}
