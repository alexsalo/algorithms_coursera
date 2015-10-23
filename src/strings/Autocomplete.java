package strings;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class Autocomplete {

    public static void main(String[] args) {
        String filename = "src/strings/data/web2.txt";
        In in = new In(filename);

        RWayTrie<Boolean> autocomplete = new RWayTrie<Boolean>();

        while (!in.isEmpty()) {
            String s = in.readString();
            autocomplete.put(s.toLowerCase(), true);
        }

        // System.out.println(checker.keysWithPrefix("autoa"));
        
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            System.out.println(autocomplete.keysWithPrefix(s));
        }
    }
}
