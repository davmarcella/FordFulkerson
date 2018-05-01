package graph;

public class Edge {
	Node dest;
	int cost;

	public Edge(Node dest){
		this.dest = dest;
		cost = 0;
	}
	

	public Edge(Node dest, int cost){
		this.dest = dest;
		this.cost = cost;
	}
	
	/***
	 * returns the destination node
	 */
	public String toString(){
		return dest.toString();
	}
	
	/***
	 * returns the cost
	 * @return
	 */
	public int getCost(){
		return cost;
	}
}
