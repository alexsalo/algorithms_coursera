package edgedigraph;

import structures.Stack;
import edu.princeton.cs.algs4.In;

/*
 *  Works only for Di DAGs, faster than Dijkstra since need only E + V
 * 
 *  Application:
 *      Seam carving  
 *      
 *  To find Longest Paths:
 *      - reverse edge weights and do SP
 *      
 *  App:
 *      - Job scheduling in manufacturing
 */
public class AcyclicSP extends ShortestPath{   
    private boolean[] marked;    
    private Stack<Integer> postorder;
    
    /*
     *  1. Get topological order
     *  2. Relax out edges of v in topo order 
     */
    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        super(G, s);
        topoSort(G, s);
    }

    private void topoSort(EdgeWeightedDigraph G, int s) {
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INF;
        distTo[s] = 0;
     
        topologicalSort(G);
        System.out.println(postorder);
        
        for (int v : postorder)
            for (DirectedEdge e : G.adj(v))
                relax(e);
    }
    
    private void topologicalSort(EdgeWeightedDigraph G) { 
        marked = new boolean[G.V()];
        postorder = new Stack<Integer>();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                dfsTopological(v, G);
    }

    private void dfsTopological(int v, EdgeWeightedDigraph G) {
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w])
                dfsTopological(w, G);
        }
        postorder.push(v);
    } 

    public static void main(String[] args) {
        String filename = "src/edgedigraph/data/tinyEWDAG.txt";
        In in = new In(filename);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);
        
        System.out.println("\n---------------\nShortest Paths:");
        int s = 5;
        ShortestPath sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); v++)
            System.out.println(sp.pathTo(v));
        
        filename = "src/edgedigraph/data/tinyEWDAG-neg-weights.txt";
        in = new In(filename);
        G = new EdgeWeightedDigraph(in);
        s = 5;
        System.out.println("\n---------------\nLongest Paths:");
        sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); v++)
            System.out.println(sp.pathTo(v));
    }
}
