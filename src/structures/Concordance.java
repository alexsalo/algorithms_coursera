package structures;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class Concordance {

    public static void main(String[] args) {
        String filename = "bukowsky_women.txt";
        In in = new In(filename);
        
        // splite by one or more whitespaces
        String[] words = in.readAll().split("\\s+");
        Hashtable<String, Set<Integer>> st = new Hashtable<String, Set<Integer>>();
        for (int i = 0; i < words.length; i++){
            String s = words[i];
            if (!st.containsKey(s))
                st.put(s, new HashSet<Integer>());
            Set<Integer> pages = st.get(s);
            pages.add(i);
        }

        while (!StdIn.isEmpty()){
            String query = StdIn.readString();
            Set<Integer> pages = st.get(query);
            for (int k : pages){
                for (int i = k - 4; i < k; i++)
                    System.out.print(words[i] + " ");
                System.out.print("*" + words[k] + "* ");
                for (int i = k + 1; i <= k + 4; i++)
                    System.out.print(words[i] + " ");
                System.out.println();
            }
        }
    }

}
