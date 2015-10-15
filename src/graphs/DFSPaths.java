package graphs;

import java.util.Stack;

import edu.princeton.cs.algs4.In;

public class DFSPaths {
    private Graph G;
    private int source;
    private Stack<Integer> path;
    
    public DFSPaths(Graph G, int source){
        this.G = G;
        this.source = source;
    }
    
    public boolean hasPathTo(int v){
        boolean[] visited = new boolean[G.V()];
        visited = dfs(v, visited);
        return visited[source] && visited[v];
    }
    
    private boolean[] dfs(int v, boolean[] visited) {
        visited[v] = true;
        for (int w : G.adj(v))
            if (!visited[w])
                visited = dfs(w, visited);
        return visited;
    }
    
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        path = new Stack<Integer>();
        path.push(source);
        boolean[] visited = new boolean[G.V()];
        findPath(v, visited);
        return path;
    }
    
    private boolean[] findPath(int v, boolean[] visited) {        
        visited[v] = true;
        
        if (visited[source] && visited[v])
            return visited;
        
        for (int w : G.adj(v))
            if (!visited[w]) 
                visited = findPath(w, visited);               
            
        path.push(v);
        return visited;
    }
    
    public static void main(String[] args) {
        String filename = "src/graphs/data/tiny-graph.txt";
        In in = new In(filename);
        Graph G = new Graph(in);
        DFSPaths paths = new DFSPaths(G, 1);
        System.out.println("Path 1-3: " + paths.hasPathTo(3));
        System.out.println("Path 1-3: " + paths.pathTo(3));
        System.out.println("Path 1-7: " + paths.pathTo(7));
    }
}
