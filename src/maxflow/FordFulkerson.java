package maxflow;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

/*
 *  1. st-cut - partition of the V into two s-set and t-set
 *  2. cut capacity - sum of weights of out-edges from s-set to t-set
 *  3. find min cut? 
 *  - how to cut the graph efficiently - what is the min set of edges to disconnect the graph? 
 *  
 *  MaxFlow: for each edge:
 *      - capacity
 *      - flow <= capacity
 *      - for each v: inflow = outflow
 *      
 *      what is the max you can get into the target?
 *      
 *  Ford-Fulkerson:
 *      1. Start with 0 flow
 *      2. While there is an augmenting path:
 *          - find it
 *          - compute bottleneck capacity
 *          - increase flow on that path by bottleneck capacity
 *          
 *  Applications:
 *      - Bipartite matching
 *      - Baseball elimination
 *      - Data mining
 *      - Image segmentation
 *      
 *  Time Complexity:
 *      - compute min cut - easy
 *      - find aug path - dfs
 *      - how many augmentations? 
 *      
 *  How to choose aug path:
 *      - sp - 1/2EV
 *      - fattest path - Eln(EU)
 *      
 *  ~O(E^3)
 */
public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;
    
    public FordFulkerson(FlowNetwork G, int s, int t) {
        value = 0;
        while (hasAugmentingPath(G, s, t)) {
            // compute bottleneck capacity on the path going backward
            double bottleneck = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottleneck = Math.min(bottleneck, edgeTo[v].residualCapacityTo(v));
            
            //augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottleneck);
            
            value += bottleneck;
        }
    }
    
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.V()];
        
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        marked[s] = true;
        
        while (!q.isEmpty()) {
            int v = q.dequeue();
            
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                // found a path from s to w in resid network ?
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
        
        return marked[t];
    }
    
    public double value() {
        return value;
    }
    
    // is v reachable from s in resid network
    public boolean inCut(int v) {
        return marked[v];
    }
    
    public static void main(String[] args) {
        String filename = "src/maxflow/data/tinyFN.txt";
        In in = new In(filename);
        FlowNetwork fn = new FlowNetwork(in);
        System.out.println(fn);    
        
        FordFulkerson ff = new FordFulkerson(fn, 0, 5);
        System.out.println(ff.value());
    }

}
