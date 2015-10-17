package edgedigraph;

import edu.princeton.cs.algs4.In;

/*
 *  If we have:
 *      - negative weights - Dijkstra doesn't work
 *      - cycles           - Topo sort doesn't work
 *  
 *  IF negative cycles - SP doesn't exist
 *  
 *  
 */
public class BellmanFordSP extends ShortestPath{

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        super(G, s);
        BellmanFord(G);
    }
    
    /*
     *  For V times: relax each edge
     */
    private void BellmanFord(EdgeWeightedDigraph G) {
        for (int i = 0; i < G.V(); i++)
            for (int v = 0; v < G.V(); v++)
                for (DirectedEdge e : G.adj(v))
                    relax(e);
    }

    public static void main(String[] args) {
        String filename = "src/edgedigraph/data/tinyEWDn.txt";
        In in = new In(filename);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);
        
        System.out.println("---------------\nSortest Paths:");
        int s = 5;
        ShortestPath sp = new BellmanFordSP(G, s);
        for (int v = 0; v < G.V(); v++)
            System.out.println(sp.pathTo(v));
    }

}
