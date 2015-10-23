package strings;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

/*
 *  - Store chars in nodes
 *  - Each node has 3 kids: smaller, equal, larger
 *  MUCH more SPACE efficient
 *  
 *  - TST:          3 null links in each leaf
 *  - 26-way Trie: 26 null links in each leaf
 *  
 */
public class TrenarySearchTrie<Value> {
    private Node root = new Node();
    private int N = 0;
    
    private static class Node {
        char c;
        Object val;
        Node smaller, equal, larger;
    }
    
    public void put(String key, Value val) {
        if (!contains(key)) N++;
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)               x.smaller  = put(x.smaller,  key, val, d);
        else if (c > x.c)               x.larger = put(x.larger, key, val, d);
        else if (d < key.length() - 1)  x.equal   = put(x.equal,   key, val, d+1);
        else                            x.val   = val;
        return x;
    }
    
    @SuppressWarnings("unchecked")
    public Value get(String key) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value)x.val;
    }
    
    private Node get(Node x, String key, int d) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.smaller,  key, d);
        else if (c > x.c)              return get(x.larger, key, d);
        else if (d < key.length() - 1) return get(x.equal,   key, d+1);
        else                           return x;
    }
    
    public boolean contains(String key) {
        return get(key) != null;
    }
    
    public int size() {
        return N;
    }
    
    
    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        System.out.println(Arrays.toString(strings));
        
        TrenarySearchTrie<String> tst = new TrenarySearchTrie<String>();
       
        for (String s : strings)
            tst.put(s.toLowerCase(), s);
        
        System.out.println("TST size: " + tst.size());
        
        System.out.println(tst.get("shell"));
        System.out.println(tst.get("shells"));
        System.out.println(tst.get("ares"));  
        System.out.println(tst.get("are")); 
        
        
    }

}
