package structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Binary Tree structure depends on the order at which keys come
 * Worst case: sorted order of keys
 * 
 * BST correspond to quicksort partition if no duplicate keys
 * meaning if insert N distinct keys in random order then Exp[~] = 1.4lnN
 * moreover, worst case at random order ~4.3lnN
 * 
 * Problem: if client provide keys - they may be ordered and we are fucked: ~N
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
        
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    // Overrides existing value on this key
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }
    
    // Smart internal recursive insert
    private Node put(Node x, Key key, Value val){
        if (x == null) return new Node(key, val);
        
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val = val;
        
        return x;
    }

    // return null if not present
    // cost: 1 + depth of the tree
    public Value get(Key key) {
        Node x = root;
        while (x != null){
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null; // not found
    }
    
    public boolean containsKey(Key key){
        return get(key) != null;
    }
    
    public Key minKey(){
        Node x = root;
        while (x.left != null)
            x = x.left;
        return x.key;
    }
    
    public Key maxKey(){
        Node x = root;
        while (x.right != null)
            x = x.right;
        return x.key;
    }
    
    public Iterable<Key> keySet(){
        ArrayList<Key> keys = new ArrayList<Key>();
        inOrderTraversal(root, keys);
        return keys;
    }
    
    private void inOrderTraversal(Node start, ArrayList<Key> keys){
        if (start == null) return;
        keys.add(start.key);
        inOrderTraversal(start.left, keys);
        inOrderTraversal(start.right, keys);
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        int minlen = Integer.parseInt(args[0]);
        //SymbolTable<String, Integer> st = new SymbolTable<String, Integer>();
        BST<String, Integer> st = new BST<String, Integer>();
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
        
        System.out.println(st.keySet());
        System.out.println("Max key: " + st.maxKey());
        System.out.println("Min key: " + st.minKey());
        
        String max = "";
        st.put(max, 0);
        for (String word : st.keySet())
            if (st.get(word) > st.get(max))
                max = word;
        System.out.println("Most popular word is:   \"" + max + 
                "\"   encountered: " + st.get(max) + " times.");
    }
}
