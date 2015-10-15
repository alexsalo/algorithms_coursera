package graphs;

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
 *  Finds shortest path to all the nodes from the source
 */
public class BFSPaths {
    private static final int NOPATH = -1;
    private boolean[] marked;
    private int[] edgeTo;
    private Graph G;
    
    public BFSPaths(Graph G) {
        this.G = G;
    }
    
    private void bfs(int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++){
            marked[v] = false;
            edgeTo[v] = NOPATH; // no edge
        }
            
        Queue<Integer> q = new LinkedList<Integer>();
        
        q.add(s);
        marked[s] = true;
        while (!q.isEmpty()){
            int v = q.poll();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    q.add(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                }                    
            }
        }
    }
    
    // only internal calls when already perfomed bfs
    private boolean hasPath(int to) {
        return edgeTo[to] != NOPATH;
    }
    
    public Iterable<Integer> shortestPath(int from, int to) {
        bfs(from);
        if (!hasPath(to))
            return null;
        
        Stack<Integer> path = new Stack<Integer>();        
        path.push(to);
        while (edgeTo[to] != from) {
            to = edgeTo[to];
            path.push(to);
        }
        path.push(edgeTo[to]);
        return path;
    }

    public static void main(String[] args) {
        String filename = "src/graphs/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);
        BFSPaths p = new BFSPaths(G);
        System.out.println(p.shortestPath(3, 2));
        System.out.println(p.shortestPath(0, 9));
    }

}
