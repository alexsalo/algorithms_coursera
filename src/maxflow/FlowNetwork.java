package maxflow;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;

public class FlowNetwork {
    private ArrayList<FlowEdge>[] adj;
    private final int V;

    public FlowNetwork(int V){
        this.V = V;
        initGraph(V);
    }
    
    public FlowNetwork(In in){
        V = in.readInt();
        int E = in.readInt();
        initGraph(V);
        
        for (int e = 0; e < E; e++)
            addEdge(new FlowEdge(in.readInt(), in.readInt(), in.readDouble()));             
    }
    
    @SuppressWarnings("unchecked")
    private void initGraph(int V){
        adj = (ArrayList<FlowEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<FlowEdge>();
    }
    
    /*********************************************************************
     ************************* GRAPH API *********************************
     ********************************************************************/
    public void addEdge(FlowEdge e){
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }
    
    public Iterable<FlowEdge> adj(int v){
        return adj[v];
    }

    public int V(){ 
        return V;
    }

    /*********************************************************************
     ********************* HELPER & CLIENT *******************************
     ********************************************************************/
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < V; v++){
            sb.append(v + " -> ");
            for (FlowEdge e : adj(v))
                sb.append(e + ", ");
            sb.setCharAt(sb.length() - 2, '\n');
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String filename = "src/maxflow/data/tinyFN.txt";
        In in = new In(filename);
        FlowNetwork fn = new FlowNetwork(in);

        System.out.println(fn);
    }

}
