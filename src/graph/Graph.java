package graph;


public interface Graph {
	
	public boolean addNode(String name);

	public boolean addEdge(String src, String dest);
	
	public void removeNode(String name);
	
	public void removeEdge(String src, String dest);
	
}
