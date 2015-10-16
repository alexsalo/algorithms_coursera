package digraphs;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;

/*
 *  - Reachability: 
 *          - Unreachable code
 *          - Garbage Collector
 *          - Web Crawling
 *  
 *  - Path finding
 *  
 *  - Topological Sort
 *          - Precedence Scheduling For DAG directed acyclic graph
 *  
 *  - Directed Cycle Detection
 *          - Avoid deadlocks
 * 
 *  - Strong Connectivity
 *          - Strong connected component if there is a path from any to any.
 *          - Reverse graph has the same connected components
 *          - Kernel DAG: toposort and combine cc as a single node
 *                 - no cycles since they are inside cc
 *                 
 *                 
 *  - PageRank - importance of web-page
 *  - Shortest Path
 */
public class Digraph {
    ArrayList<Integer>[] adj;
    int V;
    int E;

    public Digraph(int V){
        this.V = V;
        initGraph(V);
    }
    
    public Digraph(In in){
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
    
    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                reverse.addEdge(w, v);
        return reverse;
    }
    
    public int outdegree(int v) {
        return adj[v].size();
    }
    
    /*public int indegree(int v) {
        return adj[v].size();
    }*/
    

    /*********************************************************************
     ********************* HELPER & CLIENT *******************************
     ********************************************************************/
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < V; v++){
            sb.append(v + " -> ");
            for (int w : adj(v))
                sb.append(w + ", ");
            sb.setCharAt(sb.length() - 2, '\n');
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String filename = "src/digraphs/data/tiny-digraph.txt";
        In in = new In(filename);
        Digraph G = new Digraph(in);

        System.out.println(G);
        System.out.println(G.reverse());
    }

}
