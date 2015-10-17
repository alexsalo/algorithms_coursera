package edgedigraph;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class EdgeWeightedDigraph {
    private int V;
    private int E;
    private List<DirectedEdge>[] adj;
    
    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(int V){
        this.V = V;
        adj = (List<DirectedEdge>[]) new List[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<DirectedEdge>();
    }
    
    public EdgeWeightedDigraph(In in){
        this(in.readInt());
        E = in.readInt();
        
        for (int e = 0; e < E; e++) {
            DirectedEdge edge = new DirectedEdge(in.readInt(), in.readInt(), in.readDouble());
            addEdge(edge);  
        }
    }
    
    public void addEdge(DirectedEdge e){
        int v = e.from();
        adj[v].add(e);
    }
    
    public Iterable<DirectedEdge> adj(int v){
        return adj[v];
    }
    
    public Iterable<DirectedEdge> edges(){      
        List<DirectedEdge> alledges = new ArrayList<DirectedEdge>();
        for (int v = 0; v < V; v++)
            alledges.addAll(adj[v]);
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
            for (DirectedEdge e : adj(v))
                s += e.from() + " -> " + e.to() +  " (" + e.weight() + "), ";
            s += "\n";
        }
        return s;
    }
    
    public static void main(String[] args) {
        String filename = "src/edgedigraph/data/tinyEWD.txt";
        In in = new In(filename);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        
        System.out.println(G);
        System.out.println(G.edges());
    }
}
