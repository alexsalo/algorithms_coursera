package edgegraphs;

import java.util.ArrayList;
import java.util.List;

import structures.MinPQ;
import structures.WeightedUF;
import edu.princeton.cs.algs4.In;

/*  ST - subgraph of G that is connected and acyclic,
 *       where G is connected graph with positive weight edges
 *  
 *  Find: min ST?
 *  
 *  - Cluster Analysis
 *  - Face detection
 *  
 *  Cut - graph partition of its verticies into two non empy sets
 *  Crossing edge - connects verticies from different sets in cut
 *  
 *  Proposition: Given any cut: edge with min weight would be in the MST
 *  Greedy Alg: just generate random cuts and add edge with min weight into MST
 *      - How to choose cut so it doesn't have an edge from MST?
 *      - How to find edge with min weight?
 *  
 *  Impl: 
 *      Kruskal
 *      Prim
 *      Boruvka
 *      
 *  Applications:
 *      - 
 */
public class MST {
    private double weight;
    private List<Edge> mst;  // result
    private MinPQ<Edge> pq;  // sorted by weight edges
    
    private boolean[] marked; // for lazy Prim MST
    
    public Iterable<Edge> edges() {
        return mst;
    }
    
    public double weight() {
        return weight;
    }
    
    /*
     *  1. Sort edges in ascending order
     *  2. Add next edge to tree T unless would create a cycle
     *  
     *  Works since it's a greedy alg with cuts 
     *  
     *  To Find cycle: use UF
     *      - if v and w are in same set - then v-w would create a cycle
     *      - to add v-w -> merge v and w
     *      
     *   O(E*lgE) 
     */
    public Iterable<Edge> KruskalMST(EdgeWeightedGraph G) {
        mst = new ArrayList<Edge>();
        
        pq = new MinPQ<Edge>();    
        for (Edge e : G.edges())
            pq.insert(e);    
        
        WeightedUF uf = new WeightedUF(G.V());
        
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.isConnected(v, w)) { // check if would create a cycle
                uf.union(v, w);
                mst.add(e);
                weight += e.weight();
            }
        }
        return mst;
    }
    
    /*
     *  1. Start with V0
     *  2. Grow tree greedily by adding edge with exactly one endpoint in MST
     *      and having the min weight
     *      
     *  Lazy since we keep obsolete edges on PQ 
     *   
     *   O(E*lgE) 
     */
    public Iterable<Edge> PrimLazyMST(EdgeWeightedGraph G) {
        mst = new ArrayList<Edge>();
        
        pq = new MinPQ<Edge>();    
        marked = new boolean[G.V()];
        visit(G, 0);
        
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) 
                continue; // ignore obsolete edge with two ends in MST
            mst.add(e);
            // investigate the edges that now connected to the mst via v and w
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
        return mst;
    }
    
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)])
                pq.insert(e);
    }

    public static void main(String[] args) {
        String filename = "src/edgegraphs/data/tinyEWG.txt";
        String filenameMed = "src/edgegraphs/data/mediumEWG.txt";
        In in = new In(filename);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        in = new In(filenameMed);
        EdgeWeightedGraph Gmed = new EdgeWeightedGraph(in);
        
        MST mst = new MST();
        for (Edge e : mst.KruskalMST(G))
            System.out.println(e);
        System.out.println("Weight: " + mst.weight());
        
        System.out.println();
        
        for (Edge e : mst.PrimLazyMST(G))
            System.out.println(e);
        System.out.println("Weight: " + mst.weight());

        System.out.println();
        mst.KruskalMST(Gmed);
        System.out.println("Weight: " + mst.weight());
        mst.PrimLazyMST(Gmed);
        System.out.println("Weight: " + mst.weight());
    }

}
