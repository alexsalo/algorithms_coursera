package edgedigraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP extends ShortestPath{   
    IndexMinPQ<Double> pq;
    
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        super(G, s);
        Dijkstra(G, s);
    }

    /*
     *  1. Conside verticies with min distTo[] values
     *  2. Add vertex to tree and relax all edges pointing from that vertex
     *  
     *  Proof:
     *      - each edge relaxed exactly once
     *      - we only decrease distTo[w]
     *      
     *  Almost the same algorithm as Prim's MST:
     *      - Prim: add vertex with shortest dist to MST (WEG)
     *      - Dijkstra: add vertex with shortest dist to SOURCE. (WEDiG)
     *      
     *  Running Time:
     *      - Heap: E*lgV
     *      - d-way Heap: E*lg_(E/V)V
     *      - Fib heap: E + V*lgV
     */
    private void Dijkstra(EdgeWeightedDigraph G, int s) {
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INF;
        distTo[s] = 0;
        
        pq = new IndexMinPQ<Double>(G.V());
        
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) 
                relax(e);
        }
    }

    protected void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        // if shorter path available
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) // update key with cur min dist
                pq.decreaseKey(w, distTo[w]);
            else // put on pq
                pq.insert(w, distTo[w]);
        }
    }
    

    public static void main(String[] args) {
        String filename = "src/edgedigraph/data/tinyEWD.txt";
        In in = new In(filename);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);
        
        int s = 0;
        ShortestPath sp = new DijkstraSP(G, s);
        for (int v = 0; v < G.V(); v++)
            System.out.println(sp.pathTo(v));
    }
}
