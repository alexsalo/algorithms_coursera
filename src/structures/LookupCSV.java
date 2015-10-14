package structures;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class LookupCSV {

    public static void main(String[] args) {
        /*In in = new In(args[0]);
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);*/
        String filename = "src/structures/data/misspellings.csv";
        In in = new In(filename);
        int keyField = 0;
        int valField = 1;

        HashTable<String, String> st = new SeparateChainingHashTable<String, String>();
        while (!in.isEmpty()){
            String line = in.readLine();
            String[] tokens = line.split(",");
            //System.out.println(line);
            st.put(tokens[keyField], tokens[valField]);
        }
        
        // Spell checker
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            System.out.println(st.get(s));
        }
    }

}
