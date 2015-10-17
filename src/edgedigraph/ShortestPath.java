package edgedigraph;

import structures.Stack;

/*
 *  Single source: shortest path from one source to every other
 *  
 *  Restrictions: 
 *      - Nonnegative, Euclidian, arbitrary weights
 *      - No "negative" directed cycles
 *  
 *  Assume: SP exist
 *  
 *  Edge relaxation:
 *      distTo[v] - len of the shortest known path s-v
 *      distTo[w] - len of the shortest known path s-w
 *      edgeTo[w] is the last edge on the shortest known path s-w
 *      
 *      relax - if v-w has shorter path -> update edgeTo[w] and distTo[w]
 *  
 *  Optimality Conditions:
 *      1. for each v in distTo[v] is the len of some path s-v
 *      2. for each e=e-v: distTo[w] <= distTo[v] + e.weight
 *      
 *  Generic alg:
 *      - init distTo[] = inf
 *      - relax edges until optimality conditions are satisfied
 *      
 *      - How to choose which edge to relax?
 *      
 *  - Dijkstra (nonnegative weights)
 *  - Topo sort (no directed cycles)
 *  - Bellman Ford (no negative cycles)
 *  
 *  Applications:  
 *      - Navigation and planning
 */
public class ShortestPath {
    protected static final double INF = Double.MAX_VALUE;
    protected double[] distTo;
    protected DirectedEdge[] edgeTo;
    
    public ShortestPath(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
    }
    
    double distTo(int v) {
        return distTo[v];
    }
    
    protected void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        // if shorter path available
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }
    
    public Iterable<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
