package code_prep.strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class FindAllAnagrams {
    
    // insertion sort
    private static String sortLetters(String s) {
        char[] c = s.toCharArray();
        for (int i = 1; i < c.length; i++) {
            char x = c[i];
            int j;
            for (j = i; j > 0 && c[j - 1] > x; j--)
                c[j] = c[j - 1];
            c[j] = x;
        }
        return new String(c);
    }
    
    public static void main(String[] args) {
        // 1. compute anagrams N * (L^2)
        Map<String, Set<String>> anagrams = new HashMap<String, Set<String>>();
        String filename = "src/strings/data/web2.txt";
        In in = new In(filename);
        while (!in.isEmpty()) {
            String word = in.readString();
            String key = sortLetters(word);
            Set<String> set;
            if (anagrams.containsKey(key)) set = anagrams.get(key);                
            else                       set = new HashSet<String>();
            set.add(word);
            anagrams.put(key, set);
        }
        
        // 2. lookup - const time
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();            
            // Option 1. "anagrams"
            System.out.println(anagrams.get(sortLetters(s)));
            
            // Option 2. "all you can compose"
            Set<String> set = new HashSet<String>();
            String key = sortLetters(s);
            for (String subkey: allSubstrings(key, new ArrayList<String>()))
                if (anagrams.containsKey(subkey))
                    set.addAll(anagrams.get(subkey));
            System.out.println(set);
        }
        
    }
    
    // NEED TO FIX THIS METHOD TO COMPUTE ALL THE POSSIBLE COMBINATIONS
    public static ArrayList<String> allSubstrings(String sortedString, ArrayList<String> list) {
        int n = sortedString.length();
        
        for (int len = 2; len < n; len++)
            for (int i = 0; i < n - len + 1; i++) {
                String s1 = sortedString.substring(i, i + len);
                list.add(s1);      
            }
        return list;
    }

}
