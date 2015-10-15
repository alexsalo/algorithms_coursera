package graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class EulerTour {
    private boolean[] marked;
    private Graph G;
    
    public EulerTour(Graph G) {
        this.G = G;  
        marked = new boolean[G.V()];
        dfs(0);
    }
    
    private void dfs(int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) 
                dfs(w);              
    }
    
    public boolean hasEulerTour() {
        for (boolean b : marked)
            if (!b)
                return false;
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) % 2 != 0)
                return false;
        return true;
    }
    
    public Iterable<Integer> eulerTour() {
        Stack<Integer> tour = new Stack<Integer>();
        if (!hasEulerTour())
            return tour;
        marked = new boolean[G.V()];
        
        return tour;
    }

    public static void main(String[] args) {
        //String filename = "src/graphs/data/seven-bridges.txt";
        String filename = "src/graphs/data/has-euler-cycle.txt";
        In in = new In(filename);
        Graph G = new Graph(in);
        System.out.println(G);
        EulerTour eulertour = new EulerTour(G);
        
        System.out.println(eulertour.hasEulerTour());
    }
}