package strings;

import java.util.Arrays;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import digraphs.Digraph;

public class NFA {
    private static final char WILDCARD = '.';
    
    private char[] re; // black (match) transitions
    private Digraph G; // epsilon transitions
    private int M;
    
    private boolean[] marked;
    
    public NFA(String regexp) { 
        M = regexp.length();
        re = regexp.toCharArray();
        G = buildEpsilonTransitionsDigraph();
    }
    
    /**
     * Parse the Regexp.
     * ---------------
     *      1. Concatenation - make match transitions
     *      2. Parenthesis   - put epsilon transition to the next state
     *      3. Closure *     - put 3 epsilon transitions: prev->*; *->prev; *->next;
     *      4. Or |          - put ( -> or+1; or -> )
     *      
     * Impl:
     *      ( - push ( to stack
     *      | - push | to stack
     *      ) - pop from stack and add epsilon transitions
     * @return Digraph representing NFA
     */
    private Digraph buildEpsilonTransitionsDigraph() {
        Digraph G = new Digraph(M + 1);
        Stack<Integer> ops = new Stack<Integer>();
        for (int i = 0; i < M; i++) {
            int lp = i; // lp - left parenthesis
            
            // left ( or |
            if (re[i] == '(' || re[i] == '|')
                ops.add(i);
            
            // right )
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else // re[or] == (
                    lp = or;                
            }
            
            // closure in ahead char
            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            
            // add all the epsilon transition to the next states
            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i + 1);
        }
        return G;
    }
    
    /**
     *  NFA simulation
     *  1. get states reachable from start by epsilon transitions
     *  2. for each next char:
     *      - do match transitions from available start set
     *      - do epsilon transitions everywhere generating reachability set
     *  3. check if accepting state is reachable
     */
    public boolean recognizes(String text) {
        Set<Integer> pc = new TreeSet<Integer>(); // reachable states
        marked = new boolean[G.V()];

        // states reachable from start by epsilon-transitions
        DFS(0);
        for (int v = 0; v < G.V(); v++)
            if (marked[v])
                pc.add(v); // add them to the set
        
        // read letter and do mathc transition, make eps-transitions, update current set of states
        for (int i = 0; i < text.length(); i++) {   
            // 1. Do match transitions
            // set of states reachable after scanning last char
            Set<Integer> match = new TreeSet<Integer>();
            for (int v : pc) { // test each state available at the beginning
                if (v == M) continue;
                if ((re[v] == text.charAt(i)) || re[v] == WILDCARD)
                    match.add(v + 1); // add all match (black) transitions
            }
            
            // 2. Do epsilon transitions
            // get all the reachable states
            marked = new boolean[G.V()];
            DFS(match);
            pc = new TreeSet<Integer>();
            for (int v = 0; v < G.V(); v++)
                if (marked[v])
                    pc.add(v); // add them to the set
        }
        
        for (int v : pc)
            if (v == M)
                return true; // If last state is reachable, ACCEPT.
        return false; // REJECT
    }
    
    private void DFS(int s) {
        marked[s] = true;
        for (int v : G.adj(s))
            if (!marked[v])
                DFS(v);
    }
    
    private void DFS(Set<Integer> match) {
        for (int s : match)
            if (!marked[s])
                DFS(s);
    }

    public String toString() {
        return Arrays.toString(re) + G;
    }
    
    public static void main(String[] args) {
        NFA nfa = new NFA("((A*B|AC)D)");
        System.out.println(nfa);
    }
}
