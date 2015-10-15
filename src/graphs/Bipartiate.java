package graphs;

import edu.princeton.cs.algs4.In;

public class Bipartiate {
    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColorable = true;
    private Graph G;
    
    public Bipartiate(Graph G) {
        this.G = G;  
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) 
                dfs(v);
    }
    
    private void dfs(int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) {
                color[w] = !color[v]; // set the opposite color
                dfs(w);
            }
            else if (color[w] == color[v])
                isTwoColorable = false;                
    }
    
    public boolean isToColorable() {
        return isTwoColorable;
    }

    public static void main(String[] args) {
        String filename = "src/graphs/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);
        Bipartiate b = new Bipartiate(G);
        
        System.out.println(b.isToColorable());
    }
}
