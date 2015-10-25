package strings;

import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Context:
 *      - Substring search: single index of pattern in text
 *      - Pattern matching: set of strings in text
 *      
 * Pattern:
 *      - Med: GCG(CGG|AGG)*CTG for Fragile X syndrome
 *      - Syntax Highlight
 *      - Google code search
 *      - Validate data entry field
 *      
 * Parsing text file:
 *      - Compile 
 *      - Crawl web
 *      
 * Operations:
 *        order  
 *      - 3     Concatenation AB
 *      - 4     OR            A|B
 *      - 2     closure       A* - zero or more occurrences
 *      - 1     parenthesis   (AB)*A
 *      
 *      Shorthand notations (could be constructed with basic operations):
 *      -       wildcard      . - matches any
 *      -       [A-Za-z][a-z]* - Capitalized - match
 *                             - camelCase   - no match  
 *                             
 *      -       {4} exactly 4 times
 *      
 *      Ex:
 *          - Substring search: .*string.*
 *          - SSN [0-9]{3}-[0-9]{2}-[0-9]{4}
 *          - Java identifier [$_A-Za-z][$_A-Za-z0-9]*
 *          
 * Regex is easier to write than to read, hard to debug
 * 
 * Regex ~= DFA ~= NFA
 * 
 * Approach:
 *      - Do the same as for KMP, but use . as wildcard, use dfa
 *        (the problem is that DFA might have infinite # of states - can't model
 *        
 *      - Use NFA - consider all possible transitions
 *      
 * Represent NFA:
 *      - States 0..M 
 *      - keep re[]
 *      - put black transitions in array
 *      - put all epsilon transitions as a digraph
 *      
 * Simulation:
 *      - after reading a char: keep the set of all the possible states of NFA
 *      - reachability with DFS from each source without unmarking ~(E + V)
 */
public class Regex {
    
    private static Iterable<String> RegexKnuthMorrisPrat(String text, String pattern) {
        Queue<String> q = new LinkedList<String>();
        int N = text.length();

        return q;
    }

    private static int[][] compilePatternWithWildCard(String pattern, int R, int shift) {
        int M = pattern.length();
        int[][] dfa = new int[R][M]; 
        return dfa;
    }

    public static void main(String[] args) {
        Stopwatch timer;
        String filename = "src/strings/data/tale.txt";
        //filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String text = in.readAll();
        //String pattern = "faltering";
        String pattern ="so well that my name"; 
        //pattern = "seashell";
        pattern = "gua..";
        timer = new Stopwatch();
        System.out.println(RegexKnuthMorrisPrat(text, pattern));
        System.out.println(timer.elapsedTime());
    }

}
