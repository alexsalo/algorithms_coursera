package digraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
    
    // for cycle detection
    private Stack<Integer> cycle;
    private boolean[] onStack; 
    
    // for strongly connected componets
    private int[] id;
    private int curId;
    
    // for shortest paths
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
        onStack = new boolean[G.V()];
        cycle = null;
        id = new int[G.V()];
        
        curDist = 0;
        curId = 0;
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++){
            marked[v] = false;
            onStack[v] = false;
            edgeTo[v] = NOPATH; // no edge
            distTo[v] = NOPATH;
            id[v] = NOPATH;
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
    
    /*********************************************************************
     *********************** DETECT CYCLES *******************************
     ********************************************************************/
    public Iterable<Integer> cycle() {
        resetArrays();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && cycle == null)
                dfsStack(v);
        return cycle;
    }
    
    private void dfsStack(int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (cycle != null)
                return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfsStack(w);
            } else if (onStack[w]){ // oops, reached something already on stack
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }
    
    /*********************************************************************
     *********************** TOPOLOGICAL SORT ****************************
     ********************************************************************/
    // collect postorder - the order with which we are done with the verticies
    // only iff no directed cycles
    public Iterable<Integer> topologicalSort(Digraph G) {
        if (cycle() != null) 
            System.out.print("Oops...we have a cycle here: ");
        
        ArrayList<Integer> postorder = new ArrayList<Integer>();
        resetArrays();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                postorder = dfsTopological(v, postorder);
        //Collections.reverse(postorder);
        return postorder;
    }
    
    public Iterable<Integer> topologicalSort() {
        return topologicalSort(G);
    }
    
    private ArrayList<Integer> dfsTopological(int v, ArrayList<Integer> postorder) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfsTopological(w, postorder);
        postorder.add(v);
        return postorder;
    }
    
    /*********************************************************************
     ***************** STRONGLY CONNECTED COMPONENTS *********************
     ********************************************************************/
    /*  Kosaraju-Sharir 2N alg
     *  1. Compute reverse postorder in reverse G
     *  2. Run DFS in G, visiting unmarked verticies in reverse postorder
     */
    public List<List<Integer>> stronglyConnectedComponents() {
        List<List<Integer>> scc = new ArrayList<List<Integer>>();
        Iterable<Integer> postorder = topologicalSort(G.reverse());
        
        resetArrays();
        Iterator<Integer> postOrderIterator = postorder.iterator();
        while (postOrderIterator.hasNext()) {
            int v = postOrderIterator.next();
            if (!marked[v]) {
                dfsSCC(v);
                curId++;
            }
        }
        //System.out.println(Arrays.toString(id));
        
        for (int i = 0; i < curId; i++)
            scc.add(new ArrayList<Integer>());
        
        for (int v = 0; v < id.length; v++) {
            scc.get(id[v]).add(v);
        }
        
        return scc;
    }
    
    private void dfsSCC(int v) {
        marked[v] = true;
        id[v] = curId;
        for (int w : G.adj(v))
            if (!marked[w]) 
                dfsSCC(w);
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
        System.out.println();
        
        String dag = "src/digraphs/data/dag_schedueling.txt";
        in = new In(dag);
        Digraph G2 = new Digraph(in);
        DigraphProcess topsort = new DigraphProcess(G2);
        System.out.println("Toppological sort: " + topsort.topologicalSort());       
        System.out.println("Cycle: " + topsort.cycle());
        System.out.println();
        
        String cyclefile = "src/digraphs/data/cycle.txt";
        in = new In(cyclefile);
        Digraph G3 = new Digraph(in);
        DigraphProcess gcycle = new DigraphProcess(G3);
        System.out.println("Toppological sort: " + gcycle.topologicalSort());       
        System.out.println("Cycle: " + gcycle.cycle());
        System.out.println();
        
        System.out.println("Topo order: " + dg.topologicalSort());
        System.out.println("SCC: " + dg.stronglyConnectedComponents());
    }

}
