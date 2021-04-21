
/////////////////////

//Java program for implementation of Ford Fulkerson
//algorithm

/*
 * Intesar - Marwa - Haya 
 * */
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.LinkedList;

class Phase3{
	static final int V = 6; // Number of vertices in graph
	boolean visited[] = new boolean[V];
	
	/* Returns true if there is a path from source 's' to
	sink 't' in residual graph. Also fills parent[] to
	store the path */
	boolean bfs(int rGraph[][], int s, int t, int parent[])
	{
		// Create a visited array and mark all vertices as
		// not visited
	
	
		for (int i = 0; i < V; ++i) {
			visited[i] = false;
	
		}
		// Create a queue, enqueue source vertex and mark
		// source vertex as visited
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		visited[s] = true;
		parent[s] = -1;
		

		// Standard BFS Loo
		
		while (queue.size() != 0) {
			int u = queue.poll();
			int v = 0;
			
			for (v = 0; v < V; v++) {
			
				if (visited[v] == false && rGraph[u][v] > 0) {
				
					// If we find a connection to the sink
					// node, then there is no point in BFS
					// anymore We just have to set its parent
					// and can return true
		
					
					if (v == t) {
						parent[v] = u;
						
						return true;	
					}
					queue.add(v);
					parent[v] = u;
					visited[v] = true;			
						
				}
			}	
		}
		
	
		// We didn't reach sink in BFS starting from source,
		// so return false
		return false;
	}

	// Returns tne maximum flow from s to t in the given
	// graph
	int fordFulkerson(int graph[][], int s, int t)
	{
		int min ;
		int u, v;
		int Capcity=0;
		// Create a residual graph and fill the residual
		// graph with given capacities in the original graph
		// as residual capacities in residual graph
		// Residual graph where rGraph[i][j] indicates
		// residual capacity of edge from i to j (if there
		// is an edge. If rGraph[i][j] is 0, then there is
		// not)
		int rGraph[][] = new int[V][V];
	
		for (u = 0; u < V; u++) {
			for (v = 0; v < V; v++) {
				rGraph[u][v] = graph[u][v];
			}
		
		}
		// This array is filled by BFS and to store path
		int parent[] = new int[V];
		int max_flow = 0; // There is no flow initially
		// Augment the flow while tere is path from source
		// to sink
		while (bfs(rGraph, s, t, parent)) {
			ArrayList<Integer> AguPath = new ArrayList<Integer>();
			// Find minimum residual capacity of the edhes
			// along the path filled by BFS. Or we can say
			// find the maximum flow through the path found.
			int path_flow = Integer.MAX_VALUE;		
			for (v = t; v != s; v = parent[v]) {
				AguPath.add(v);
				u = parent[v];
				path_flow = Math.min(path_flow, rGraph[u][v]);
			
			}
			AguPath.add(s);
			Collections.reverse(AguPath);
		
			// update residual capacities of the edges and
			// reverse edges along the path
			Capcity=0;
			for (v = t; v != s; v = parent[v]) {
				u = parent[v];
				rGraph[u][v] -= path_flow;
				rGraph[v][u] += path_flow;
				 
			}
			
			System.out.println("-----------------------------------------------");
			System.out.println("Agumentation path  :");
			for(int i = 0 ; i < AguPath.size(); i++ ) {
				System.out.print((AguPath.get(i)+1)  );
			if (i < AguPath.size()-1)
				System.out.print("->"  );
			}
			// Add path flow to overall flow
			max_flow += path_flow;
			System.out.println(" Capacity :"+ path_flow+" \nUpdate flow "+max_flow );
		
		}
		

	
	//we now maximum possible flow == Minimum cut 
	min=max_flow;
	System.out.println("-----------------------------------------------");
	System.out.println("********The maximum possible flow is :"
			+ max_flow+" ********");
	System.out.println("The min cut is : "+ min);
	   // Print all edges that are from a reachable vertex to
    // non-reachable vertex in the original graph   
	System.out.println("The minimum cut edges : ");
for(int i = 0 ; i< visited.length;i++) {
	for (int j = 0 ; j< visited.length;j++) {
		if(   visited[i] && !visited[j] && graph[i][j]  != 0) {
			System.out.println((i+1)+">"+(j+1)  );
			
		}
		
	}
	
}
		// Return the overall flow
		return max_flow;
	}

	// Driver program to test above functions
	public static void main(String[] args)
		throws java.lang.Exception
	{
		// Let us create a graph shown in the above example
		int graph[][] = new int[][] {
			{ 0, 2, 7, 0, 0, 0 }, { 0, 0, 0, 3, 4, 0 },
			{ 0, 0, 0, 4, 2, 0 }, { 0, 0, 0, 0, 0, 1 },
			{ 0, 0, 0, 0, 0, 5 }, { 0, 0, 0, 0, 0, 0 }
		};
		Phase3 m = new Phase3();

						m.fordFulkerson(graph, 0, 5);
	}
}
