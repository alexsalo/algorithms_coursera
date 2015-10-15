package graphs;

import edu.princeton.cs.algs4.In;

public class Cycle {
    private boolean[] marked;
    private boolean hasCycle = false;
    private Graph G;
    
    public Cycle(Graph G) {
        this.G = G;  
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) 
                dfs(v, v);
    }
    
    private void dfs(int v, int u) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) 
                dfs(w, v);
            else if (w != u) // reached self while dfsing
                hasCycle = true;                
    }
    
    public boolean hasCycle() {
        return hasCycle;
    }

    public static void main(String[] args) {
        String filename = "src/graphs/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);
        Cycle c = new Cycle(G);
        
        System.out.println(c.hasCycle());
    }
}
