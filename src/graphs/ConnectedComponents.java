package graphs;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

/*
 *  DFS gives constant time guarantee
 *      - comes with heavy pre-processing
 *  Union-Find does lglgN which in practice is just as good
 *      + UF is online
 *      + no need to create a graph representation
 *      + we can check if any two w connected while adding edges!
 *      
 *  Thus if many intermixed addEdge and CC -> use UF
 *  If already have graph - use DFS - gives other benefits along     
 *  
 */
public class ConnectedComponents {
    private boolean[] marked;
    private int[] id;
    private int current_id;
    private Graph G;
    
    public ConnectedComponents(Graph G) {
        this.G = G;  
        id = new int[G.V()];
        marked = new boolean[G.V()];
        
        current_id = 0;
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) {
                dfs(v);
                current_id++;
            }
    }

    private void dfs(int v){
        marked[v] = true;
        id[v] = current_id;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(w);
    }
    
    public boolean areConnected(int from, int to) {
        return id[from] == id[to];
    }
    
    public Iterable<List<Integer>> connectedComponents() {
        List<List<Integer>> cc = new ArrayList<List<Integer>>();
        int maxId = 0;
        for (int i : id)
            if (i > maxId)
                maxId = i;
        for (int i = 0; i <= maxId; i++)
            cc.add(new ArrayList<Integer>());
        
        for (int v = 0; v < G.V(); v++)
            cc.get(id[v]).add(v);
        
        return cc;
    }
    
    public int count() {
        return current_id;
    }

    public static void main(String[] args) {
        String filename = "src/graphs/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);
        ConnectedComponents cc = new ConnectedComponents(G);
        System.out.println(cc.areConnected(0, 4));
        System.out.println("CC: " + cc.count());
        System.out.println(cc.connectedComponents());
    }

}
