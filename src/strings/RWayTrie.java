package strings;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

/*
 *  How to beat BST and HashTable for String keys?
 *  
 *  Tries -- reTRIEval
 *  ------------------
 *  
 *  Store CHAR at the nodes of the tree
 *      - Each node has R kids, one for each possible char
 *      - Mark char if represents the end of the string key
 *  
 *  Search miss:
 *      - if reached a null link - no such word
 *      - if stopped down the tree - but no indicator of the word
 *      
 *  Insertion:
 *      - see null - create new node with char
 *      - see last char of the key - mark the node
 *  
 *  Representation:
 *      - neither keys nor chars are explicitly stores
 *      - make kids as an array of Nodes
 *      - chars are defined by link index
 *      
 *  Perfomance:
 *      - Search hit: L char comparisons
 *      - Search miss: worst = L, typical - sublinear
 *      - Space: R^L. Sublinear if short string share common prefix
 *      
 *  Fast search hit and even faster miss, but waste space
 *  
 *  Usage:
 *      - Spell checking
 */
// like a SymbolTable
public class RWayTrie<Value> {
    private static final int R = 256; // ext ASCI
    private Node root = new Node();

    private static class Node {
        private Object val; // since no generic array creation
        private Node[] next = new Node[R];
    }
    
    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node(); // found appropriate empty position
        
        if (d == key.length()) { // update value
            x.val = val;
            return x;
        }
        
        char c = key.charAt(d); // get the index - the kid
        x.next[c] = put(x.next[c], key, val, d + 1); // go one level down
        
        return x;
    }
    
    public boolean contains(String key) {
        return get(key) != null;
    }
    
    @SuppressWarnings("unchecked")
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null) return null; // not found
        if (d == key.length()) return x; //hit
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }
    
    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        System.out.println(Arrays.toString(strings));
        
        RWayTrie<String> trie = new RWayTrie<String>();
        
        for (String s : strings)
            trie.put(s.toLowerCase(), s);
        
        System.out.println(trie.get("shell"));
        System.out.println(trie.get("shells"));
        System.out.println(trie.get("ares"));      
        
    }

}
