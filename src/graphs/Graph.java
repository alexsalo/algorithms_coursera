package graphs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;

public class Graph {
    Set<Integer>[] adj;
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
        adj = (Set<Integer>[]) new Set[V];
        for (int v = 0; v < V; v++)
            adj[v] = new HashSet<Integer>();
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
        String filename = "src/structures/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);

        System.out.println(G.toString());
        System.out.println("Max degree: " + G.maxDegree());
        G.DFS(1);
    }

}
