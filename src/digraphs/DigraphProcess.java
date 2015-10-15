package digraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import edu.princeton.cs.algs4.In;

public class DigraphProcess {
    private static final int NOPATH = -1;
    private boolean[] marked;
    private int[] edgeTo;
    
    private int curDist;
    private int[] distTo;
    
    private Digraph G;
    
    public DigraphProcess(Digraph G){
        this.G = G;
        resetArrays();
    }
    
    private void resetArrays() {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        
        curDist = 0;
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++){
            marked[v] = false;
            edgeTo[v] = NOPATH; // no edge
            distTo[v] = NOPATH;
        }
    }
    
    /*********************************************************************
     ************************* REACHABILITY ******************************
     ********************************************************************/
    private void dfs(int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) {
                dfs(w);
                edgeTo[w] = v;
            }
    }
    
    public Iterable<Integer> bloodfill(int source) {
        resetArrays();
        dfs(source);
        List<Integer> fill = new ArrayList<Integer>();
        for (int v = 0; v < G.V(); v++)
            if (marked[v])
                fill.add(v);
        return fill;
    }
    
    private void bfs(int s) {
        bfs(new int[]{s});
    }
    
    // fancy bfs for multiple-source shortest paths
    private void bfs(int[] sources){
        resetArrays();
            
        Queue<Integer> q = new LinkedList<Integer>();
        
        for (int s : sources) {
            q.add(s);
            marked[s] = true;
            distTo[s] = curDist;
        }
        while (!q.isEmpty()){
            int v = q.poll();
            curDist++;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    q.add(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = curDist;
                }                    
            }
        }
    }
    
    /*********************************************************************
     ***************************** PATHS *********************************
     ********************************************************************/
    // only internal calls when already perfomed bfs
    private boolean hasPath(int to) {
        return edgeTo[to] != NOPATH;
    }
    
    public Iterable<Integer> shortestPath(int from, int to) {
        bfs(from);
        return shortestPath(new int[]{from}, to);
    }
    
    public Iterable<Integer> shortestPath(int[] from, int to) {
        if (!hasPath(to))
            return null;
        
        Stack<Integer> path = new Stack<Integer>();        
        path.push(to);
        // check we can go to any source from to
        while (!arrayContains(from, edgeTo[to])) {
            to = edgeTo[to];
            path.push(to);
        }
        path.push(edgeTo[to]);
        Collections.reverse(path);
        return path;
    }
    
    private boolean arrayContains(int[] a, int s) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == s)
                return true;
        return false;
    }

    //~N ! Just one linear BFS with multiple sources and done!
    public Map<Integer, Iterable<Integer>> shortestPaths(int[] sources) {   
        bfs(sources);
        Map<Integer, Iterable<Integer>> sps = new HashMap<Integer, Iterable<Integer>>();
        for (int v = 0; v < G.V(); v ++) {
            if (!arrayContains(sources, v))
                sps.put(v, shortestPath(sources, v));
        }
        
        return sps;
    }

    public static void main(String[] args) {
        String filename = "src/digraphs/data/tiny-digraph.txt";
        In in = new In(filename);
        Digraph G = new Digraph(in);
        //System.out.println(G);
        DigraphProcess dg = new DigraphProcess(G);
        System.out.println("Filling from 0: " + dg.bloodfill(0));
        System.out.println("SP: 3 -> 2: " +dg.shortestPath(3, 2));
        System.out.println("SP: 0 -> 7: " +dg.shortestPath(0, 7));
        System.out.println("SP: 6 -> 12: " + dg.shortestPath(6, 12));
        System.out.println(Arrays.toString(dg.distTo));
        System.out.println("SPs from 0 or 7 to all other verticies:\n   " + dg.shortestPaths(new int[]{0, 7}));
    }

}
