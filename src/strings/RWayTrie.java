package strings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
 *  Deletion:
 *      - easy - find node set val to null
 *      - if all links in the node is null - remove
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
 *      - To huge for Unicode: 65.356-Way Trie
 *      
 *  Fast search hit and even faster miss, but waste space
 *  
 *  Usage:
 *      - Spell checking
 *      
 *  More Char-based operations:
 *      - Prefix match: sh -> she shell shore
 *            AUTOCOMPLETE!
 *      - Wildcard match: .he -> she the
 *      - Longest prefix: shellsort -> shells
 *          T9 texting
 *          
 *  Patricia Trie (radix tree, crit-bit tree):
 *      - remove 1-way branching
 *      - each node has a sequence of the chars until branching.
 *      
 *      Usage:
 *          - XML parsing
 *          
 *  Suffix tree:
 *      - Patricia trie of suffixes of a string
 *     ~O(N) construction
 *     Usage:
 *          - longest common substring etc in linear time!
 *          - biology BLAST, FASTA
 *          
 *  Summary:
 *      with 100 bits we can get to any of the 2^100 possibilities!
 */
// like a SymbolTable
public class RWayTrie<Value> {
    private static final int R = 256; // ext ASCI
    private Node root = new Node();
    int N = 0;

    private static class Node {
        private Object val; // since no generic array creation
        private Node[] next = new Node[R];
    }
    
    public void put(String key, Value val) {
        if (!contains(key)) N++;
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
    
    public int size() {
        return N;
    }
    
    /*************************************************
     ********** PREFIX MATCHING **********************
     ************************************************/
    public Iterable<String> keySet() {
        Queue<String> keys = new LinkedList<String>();
        gatherKeys(root, keys, "");
        return keys;
    }    
    private void gatherKeys(Node x, Queue<String> keys, String prefix) {
        if (x == null)  return;    
        if (x.val != null) keys.add(prefix);
        
        for (char i = 0; i < R; i++)
            gatherKeys(x.next[i], keys, prefix + i);        
    }
    
    /**
     * Used in autocomplete function
     * @param prefix
     * @return all words with prefix
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> keys = new LinkedList<String>();
        Node x = get(root, prefix, 0);
        gatherKeys(x, keys, prefix);
        return keys;
    }
    
    
    /**
     * @return Wildcard match: .he -> she the
     * a.ek.and. - aleksandr
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new LinkedList<String>();
        gatherKeys(root, new StringBuilder(), pattern, results);
        return results;
    }
    private void gatherKeys(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null)  return;    
        int d = prefix.length();
        if (d == pattern.length()) {
            if (x.val != null)
                results.add(prefix.toString());
            return;
        }
        char c = pattern.charAt(d);
        if (c == '.') // do gathering for each char
            for (char ch = 0; ch < R; ch++) 
                gather(x, prefix, ch, pattern, results);
        else 
            gather(x, prefix, c, pattern, results);
    }
    private void gather(Node x, StringBuilder prefix, char c, String pattern, Queue<String> results) {
        prefix.append(c);
        gatherKeys(x.next[c], prefix, pattern, results);
        prefix.deleteCharAt(prefix.length() - 1);
    }
    
    // T9 Texting
    public String longestPrefixOf(String query) {
        int len = search(root, query, 0, 0);
        return query.substring(0, len);
    }    
    private int search(Node x, String query, int d, int len) {
        if (x == null) return len;
        if (x.val != null) len = d; // found a word - update
        if (d == query.length()) return len; // could not go futher
        
        char c = query.charAt(d);
        return search(x.next[c], query, d + 1, len);
    }
    
    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        System.out.println(Arrays.toString(strings));
        System.out.println("Array size: " + strings.length);
        
        RWayTrie<String> trie = new RWayTrie<String>();
        
        int name = 0;
        for (String s : strings)
            trie.put(s.toLowerCase(), s + name++);
        System.out.println("TST size: " + trie.size());
        
        System.out.println(trie.get("shell"));
        System.out.println(trie.get("shells"));
        System.out.println(trie.get("ares"));     
        System.out.println(trie.get("are"));     
        
        
        System.out.println("Keys: " + trie.keySet());
        System.out.println("Keys with prefix 'se': " + trie.keysWithPrefix("se"));
        System.out.println("Longest Prefix of 'seaweed' " + trie.longestPrefixOf("seaweed"));
        System.out.println("Wildcard for 'c.t' " + trie.keysThatMatch("c.t"));
        System.out.println("Wildcard for 'c.t' " + trie.keysThatMatch(".a.e."));
    }

}
