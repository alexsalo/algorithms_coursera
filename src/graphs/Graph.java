package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.In;

/*
 *  - Bipartite (Two-Colorability problem) - is every edge connects two different types of vertex
 *          - use DFS: on each next recursive call - assign flipped color
 *                     and check if can reach the vertex with the same color
 *  - Does G have a cycle? 
 *          - use DFS: check if can dfs to itself
 *          
 *  - Euler tour - is there a cycle that uses each edge exatcly once? 
 *          - iff G is connected && every v in V: deg(v) % 2 = 0 (even degree)
 *          - to find: linear time by diligently dfsing through edges
 *          
 *  - Hamiltonian cycle - TSP - visit each vertex exactly once
 *          - NP-Complete (Intractable)
 *          
 *  - Isomorphism - are two graphs identical
 *          - rename each pair of nodes and check if equal: V!
 *          - No one knows whether it's NP-C or easy problem
 *          
 *  - Topological flatness - no crossing edges on the plane
 *          - Linear time ~dfs by Tarjan 1970 (very complicated)
 */
public class Graph {
    ArrayList<Integer>[] adj;
    int V;
    int E;

    public Graph(int V){
        this.V = V;
        initGraph(V);
    }
    
    public Graph(In in){
        V = in.readInt();
        E = in.readInt();
        initGraph(V);
        
        for (int e = 0; e < E; e++)
            addEdge(in.readInt(), in.readInt());             
    }
    
    @SuppressWarnings("unchecked")
    private void initGraph(int V){
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<Integer>();
    }
    
    /*********************************************************************
     ************************* GRAPH API *********************************
     ********************************************************************/
    public void addEdge(int v, int w){
        adj[v].add(w);
        adj[w].add(v);
    }
    
    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public int V(){ 
        return V;
    }
    
    public int E(){
        return E;
    }
    
    /*********************************************************************
     ************************* GRAPH FUNC ********************************
     ********************************************************************/
    public int degree(int v){
        int deg = 0;
        for (@SuppressWarnings("unused") int w : adj(v)) deg++;
        return deg;
    }
    
    public int maxDegree(){
        int maxDeg = 0;
        for (int v = 0; v < V; v++){
            int deg = degree(v);
            if (deg > maxDeg)
                maxDeg = deg;
        }
        return maxDeg;
    }
    
    public double averageDegree(){
       return 2.0 * E / V;
    }
    
    public int numberOfSelfLoops(){
        int cnt = 0;
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                if (v == w)
                    cnt++;
        return cnt / 2;
    }   
    
    public boolean hasEulerTour() {
        boolean[] marked = dfs(0, new boolean[V]);
        for (boolean b : marked)
            if (!b)
                return false;
        for (int v = 0; v < V; v++)
            if (degree(v) % 2 != 0)
                return false;
        return true;
    }
    
    /*********************************************************************
     ********************** GRAPH EXPLORATION ****************************
     ********************************************************************/
    public void DFS(int v) {
        boolean[] visited = new boolean[V];
        visited = dfs(v, visited);
        System.out.println(Arrays.toString(visited));
    }
    
    private boolean[] dfs(int v, boolean[] visited) {
        visited[v] = true;
        for (int w : adj(v))
            if (!visited[w])
                visited = dfs(w, visited);
        return visited;
    }
    
    public void BFS(int v) {
        boolean[] visited = new boolean[V];
        Queue<Integer> q = new LinkedList<Integer>();
        visited = bfs(v, visited, q);        
        System.out.println(Arrays.toString(visited));
    }
    
    private boolean[] bfs(int v, boolean[] visited, Queue<Integer> q) {
        visited[v] = true;
        for (int w : adj(v))
            if (!visited[w])
                q.add(w);
        if (!q.isEmpty())
            bfs(q.poll(), visited, q);
        return visited;
    }
    
    
    /*********************************************************************
     ********************* HELPER & CLIENT *******************************
     ********************************************************************/
    public String toString(){
        String s = "";
        for (int v = 0; v < V; v++){
            s += v + " -> ";
            for (int w : adj(v))
                s += w + ", ";
            s += "\n";
        }
        return s;
    }
    
    public static void main(String[] args) {
        String filename = "src/graphs/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);

        System.out.println(G.toString());
        System.out.println("Max degree: " + G.maxDegree());
        G.DFS(1);
        G.BFS(1);
        System.out.println("Has Euler Tour: " + G.hasEulerTour());
    }

}
