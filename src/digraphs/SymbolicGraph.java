package digraphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.In;

public class SymbolicGraph {
    private Digraph G;
    private Map<String, Integer> stringtoint;
    private Map<Integer, String> inttostring;
    
    public SymbolicGraph(In in, String delimeter){
        String[] lines = in.readAllLines();     
        stringtoint = new HashMap<String, Integer>();
        inttostring = new HashMap<Integer, String>();
        
        // convert to ints
        int v = -1;
        for (String line : lines) {
            for (String word : line.split(delimeter))
                if (!stringtoint.containsKey(word)) {
                    stringtoint.put(word, ++v);
                    inttostring.put(v, word);
                }
        }
        
        // create graph
        G = new Digraph(stringtoint.size());
        for (String line : lines) {
            String[] words = line.split(delimeter);
            int from = stringtoint.get(words[0]);
            for (int i = 1; i < words.length; i++)
                G.addEdge(from, stringtoint.get(words[i]));
        }
    }
    
    public Iterable<String> topoSort() {
        DigraphProcess dg = new DigraphProcess(G);
        Iterable<Integer> sorted = dg.topologicalSort();
        List<String> list = new ArrayList<String>();
        for (int v : sorted)
            list.add(inttostring.get(v));
        return list;
    }

    public static void main(String[] args) {
        String filename = "src/digraphs/data/jobs.txt";
        In in = new In(filename);
        SymbolicGraph sg = new SymbolicGraph(in, "/");
        System.out.println(sg.topoSort());
    }

}
