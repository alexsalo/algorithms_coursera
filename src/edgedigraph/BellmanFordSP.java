package edgedigraph;

import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.In;

/*
 *  If we have:
 *      - negative weights - Dijkstra doesn't work
 *      - cycles           - Topo sort doesn't work
 *  
 *  IF negative cycles - SP doesn't exist
 *  
 *  Arbitrage: if there is a neg cycle in currency exchange
 *  
 */
public class BellmanFordSP extends ShortestPath{
    private boolean[] onQ;
    private Queue<Integer> q;
    private int opsCount = 0;
    
    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        super(G, s);
        BellmanFord(G, s);
        System.out.println("BellmanFord ops: " + opsCount);
        
        opsCount = 0;
        BellmanFordQueue(G, s);
        System.out.println("BF + queue ops: " + opsCount);
    }
    
    /*
     *  For V times: relax each edge
     *  
     *  Improvement:
     *      - if distTo[v] doesn't change during pass i,
     *          no need to relax any edge pointing from v in then next path
     *      - keep the queue of v
     *      
     *  Worst case: still EV, but average E + V
     *  
     *  Neg Cycle: if any v updated on the Vth run - there is a cycle
     */    
    private void BellmanFord(EdgeWeightedDigraph G, int s) {
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INF;
        distTo[s] = 0;
        
        for (int pass = 0; pass < G.V(); pass++)
            for (int v = 0; v < G.V(); v++)
                for (DirectedEdge e : G.adj(v)) {
                    relax(e);
                    opsCount++;
                }
    }
    
    private void BellmanFordQueue(EdgeWeightedDigraph G, int s) {
        onQ = new boolean[G.V()];
        q = new LinkedList<Integer>();
        
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INF;
        distTo[s] = 0;
        
        q.add(s);
        onQ[s] = true;
        
        while (!q.isEmpty()) {
            int v = q.poll();
            onQ[v] = false;
            relaxEdges(G, v);
        }
    }
    
    protected void relaxEdges(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)){
            opsCount++;
            int w = e.to();
            // if shorter path available
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQ[w]) {
                    q.add(w);
                    onQ[w] = true;
                }
            }            
        }
    }

    public static void main(String[] args) {
        String filename = "src/edgedigraph/data/tinyEWDn.txt";
        In in = new In(filename);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);        

        int s = 2;
        ShortestPath sp = new BellmanFordSP(G, s);
        System.out.println("---------------\nShortest Paths:");
        for (int v = 0; v < G.V(); v++)
            System.out.println(sp.pathTo(v));
    }

}
