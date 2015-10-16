package edgegraphs;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
    private int V;
    private int E;
    private List<Edge> alledges;
    private List<Edge>[] adj;
    
    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V){
        this.V = V;
        adj = (List<Edge>[]) new List[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<Edge>();
    }
    
    public EdgeWeightedGraph(In in){
        this(in.readInt());
        E = in.readInt();
        alledges = new ArrayList<Edge>();
        
        for (int e = 0; e < E; e++) {
            Edge edge = new Edge(in.readInt(), in.readInt(), in.readDouble());
            addEdge(edge);  
            alledges.add(edge);
        }
    }
    
    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }
    
    public Iterable<Edge> adj(int v){
        return adj[v];
    }
    
    public Iterable<Edge> edges(){        
        return alledges;
    }
    
    public int V(){
        return this.V;
    }
    
    public int E(){
        return this.E;
    }
    
    public String toString(){
        String s = "";
        for (int v = 0; v < V; v++){
            s += v + " -> ";
            for (Edge e : adj(v))
                s += e.other(v) + " (" + e.weight() + "), ";
            s += "\n";
        }
        return s;
    }
    
    public static void main(String[] args) {
        String filename = "src/edgegraphs/data/tinyEWG.txt";
        In in = new In(filename);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        
        System.out.println(G);
        System.out.println();
        System.out.println(G.edges());
    }
}
