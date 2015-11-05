package code_prep.strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import edu.princeton.cs.algs4.In;

public class WordToWord {
    static Set<String> dict;
    static Set<String> seen;
    static Map<String, String> edgeTo;
    static Queue<String> q;
    
    //BFS-like search
    public static Iterable<String> findTransition(String start, String goal) {
        seen = new HashSet<String>();
        edgeTo = new HashMap<String, String>();
        q = new LinkedList<String>();
        boolean found = false;
        
        // BFS
        q.add(start);
        while (!q.isEmpty() && !found) {
            String from = q.poll();
            for (int pos = 0; pos < from.length(); pos++) {
                for (char c = 'a'; c < 'z'; c++) {
                    String to = from.substring(0, pos) + c + from.substring(pos + 1);
                    if (dict.contains(to)) {
                        if (to.equals(goal))
                            found = true;
                        if (!seen.contains(to)) {
                            q.add(to);
                            seen.add(to);
                            edgeTo.put(to, from);
                        }
                    }
                }
            }
        }
        
        // unwind path to goal
        LinkedList<String> path = new LinkedList<String>();
        while (!goal.equals(start)) {
            path.add(goal);
            goal = edgeTo.get(goal);
        }
        path.add(goal);
        Collections.reverse(path);
        return path;
    }
    

    private static void readDict() {
        dict = new HashSet<String>();
        String filename = "src/strings/data/web2.txt";
        In in = new In(filename);
        while (!in.isEmpty()) {
            String word = in.readString();
            dict.add(word);
        }
    }
    public static void main(String[] args) {
        readDict();
        System.out.println(findTransition("alex", "goal"));
    }

}
