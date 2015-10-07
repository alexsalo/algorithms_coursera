package structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;


/*
 * 1. Insert with key
 * 2. Search by key
 * 
 * Use only immutable keys: String, File
 */
public class SymbolTable<Key extends Comparable<Key>, Value> {

    /*// Overrides existing value on this key
    public void put(Key key, Value val) {

    }

    // return null if not present
    public Value get(Key key) {

    }

    public boolean isEmpty() {

    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void delete(Key key) {
        put(key, null);
    }

    public Iterable<Key> keys() {

    }
    
    public Iterable<Key> keys(Key lo, Key hi) {

    }*/

    public static void main(String[] args) throws FileNotFoundException {
        int minlen = Integer.parseInt(args[0]);
        //SymbolTable<String, Integer> st = new SymbolTable<String, Integer>();
        HashMap<String, Integer> st = new HashMap<String, Integer>();
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("text.txt")));
        Scanner sc = null;
        while (scanner.hasNext()){
            sc = new Scanner(scanner.nextLine());
            while (sc.hasNext()){
                String word = sc.next();
                if (word.length() < minlen) continue;  // ignore short words
                if (!st.containsKey(word)) st.put(word, 1);
                else                    st.put(word, st.get(word) + 1);
            }
        }
        scanner.close();
        sc.close();
        
        String max = "";
        st.put(max, 0);
        for (String word : st.keySet())
            if (st.get(word) > st.get(max))
                max = word;
        System.out.println(max + " " + st.get(max));
    }
}
