package graph;

import java.util.HashMap;

public class HashTesting {
	
	public static void main(String args[]){
		HashMap<Integer, Integer> t = new HashMap<Integer, Integer>();
		t.put(1,10);
		int x = t.get(1);
		x++;
		t.put(1,x);
		System.out.println(x);
	}
}
