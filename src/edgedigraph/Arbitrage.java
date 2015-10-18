package edgedigraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Arbitrage {
    // this class cannot be instantiated
    private Arbitrage() { }

    /**
     *  Reads the currency exchange table from standard input and
     *  prints an arbitrage opportunity to standard output (if one exists).
     */
    public static void main(String[] args) {
        String filename = "src/edgedigraph/data/rates.txt";
        In in = new In(filename);
        
        // V currencies
        int V = in.readInt();
        String[] name = new String[V];

        // create complete network
        edu.princeton.cs.algs4.EdgeWeightedDigraph G = new edu.princeton.cs.algs4.EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = in.readString();
            for (int w = 0; w < V; w++) {
                double rate = in.readDouble();
                // take log to convert to sum and neg cycle problem
                edu.princeton.cs.algs4.DirectedEdge e = new edu.princeton.cs.algs4.DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }

        // find negative cycle
        edu.princeton.cs.algs4.BellmanFordSP spt = new edu.princeton.cs.algs4.BellmanFordSP(G, 0);
        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (edu.princeton.cs.algs4.DirectedEdge e : spt.negativeCycle()) {
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        }
        else {
            System.out.println("No arbitrage opportunity");
        }
    }

}