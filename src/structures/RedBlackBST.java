package structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import edu.princeton.cs.algs4.Stopwatch;

/*
 *  ~ Left Leaning - left link is a connection between 3-node values
 *  - Try to represent 2-3 tree as a BST
 *  - Use "internal" left-leaning links as "glue" for 3-nodes 
 *  
 *  1. No node has two red links connected to it
 *  2. Every path from root to null links has same # of black links 
 *     (Perfect black balance)
 *  3. Red links lean left
 *  
 *  Search, ceiling etc are the same code - just ignore color
 *      but runs faster since well balanced
 *      
 *  Height of the tree is guaranteed to be <= 2lgN since every path from root to 
 *      nulls has the same #black links & never two red links in a row.
 *  Typically, h(rbt) ~lgN
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {   
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    private Node root;
    
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;
        private boolean color;  // Color of the parent link

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.count = 1;
            this.color = color;
        }
    }
    
    /****************************************************************
     ************************* Red Black Rotations ******************
     *** Maintain Symmetric Order and Perfect Black Balance *********
     ****************************************************************/   
    // given RED __right__ link - rotate so i'd become left RED link
    private Node rotateLeft(Node h){
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        h.count = size(h.left) + size(h.right) + 1;
        return x;
    }
    
    private Node rotateRight(Node h){
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        h.count = size(h.left) + size(h.right) + 1;
        return x;
    }
    
    // temporary 4-node resolution
    private void flipColors(Node h){
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
    
    /****************************************************************
     ************************* PUT GET DELETE ***********************
     ****************************************************************/    
    // Overrides existing value on this key
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }
    
    /*
     * 1. Right child RED, left BLACK - rotate left
     * 2. Left, left-left both RED: rotate right
     * 3. Both kids RED: flip colors
     */
    private Node put(Node h, Key key, Value val){
        if (h == null) return new Node(key, val, RED);
        
        // binary search the position
        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else              h.val = val; // update
        
        // handle rotations
        if (isRed(h.right))                      h = rotateLeft(h);  // lean left
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h); // balance 4-node
        if (isRed(h.left) && isRed(h.right))     flipColors(h);      // split 4-node
        
        // maintain size
        h.count = 1 + size(h.left) + size(h.right);
        return h;
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
    
    /****************************************************************
     *************** Red Black BST API ******************************
     ****************************************************************/  
    public int size(){
        return size(root);
    }
    
    private int size(Node x){
        if (x == null)  return 0;
        return x.count;
    }
    
    // is glued to the parent by red link?
    private boolean isRed(Node x){
        if (x == null) return false;
        return x.color == RED;
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
    
    /****************************************************************
     ************************* Traversals ***************************
     ****************************************************************/    
    public Iterable<Key> keySet(){
        Queue<Key> keys = new LinkedList<Key>();
        inOrderTraversal(root, keys);
        return keys;
    }

    private void inOrderTraversal(Node start, Queue<Key> keys){
        if (start == null) return;
        inOrderTraversal(start.left, keys);
        keys.add(start.key);
        inOrderTraversal(start.right, keys);
    }
    
    
    /****************************************************************
     ************************* String Helpers ***********************
     ****************************************************************/    
    public String toString() {
        String sb = "";
        for (int i = 0; i < 5; i++) {
            sb += "Level " + String.valueOf(i) + ": ";
            sb += printLevel(root, i);
            sb += "\n";
        }
        return sb;
    }

    // dig down x levels and collect the row values (keys)
    private String printLevel(Node node, int level) {
        if (node == null)
            return "";
        if (level <= 0)
            return String.valueOf(node.key) + " ";
        else { // level > 0
            String leftStr = printLevel(node.left, level - 1);
            String rightStr = printLevel(node.right, level - 1);
            return leftStr + rightStr;
        }
    }

    /*
     * For the random input of the book's words Red Black Tree doesn't really gain 
     *      any benefits. 
     * Usefulness kicks in when there is deletions mixed up. Or arbitrary not random
     *      order of insertion.
     */     
    public static void main(String[] args) throws FileNotFoundException {
        Stopwatch timer = new Stopwatch();
        //BST<String, Integer> bst = new BST<String, Integer>();
        RedBlackBST<String, Integer> bst = new RedBlackBST<String, Integer>();
        //HashMap<String, Integer> bst = new HashMap<String, Integer>();
        //String filename = "mysterious_island_txt.txt";
        String filename = "bukowsky_women.txt";
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
        Scanner sc = null;
        int wordCount = 0;
        while (scanner.hasNext()){
            sc = new Scanner(scanner.nextLine());
            while (sc.hasNext()){
                String word = sc.next();
                if (word.length() < 5) continue;
                wordCount++;
                word = word.toLowerCase();
                if (!bst.containsKey(word)) bst.put(word, 1);
                else                    bst.put(word, bst.get(word) + 1);
            }
        }
        scanner.close();
        sc.close();
        
        //System.out.println(bst.toString());
        String max = bst.keySet().iterator().next();
        for (String word : bst.keySet())
            if (bst.get(word) > bst.get(max))
                max = word;
        
        System.out.println("Total word count: " + wordCount);
        System.out.println("Distinct words: " + bst.size());
        double wordFreq = (double) wordCount / bst.size();
        System.out.println("Average word frequency: " + wordFreq);
        System.out.println("Most popular word is:   \"" + max + 
                "\"   encountered: " + bst.get(max) + " times.");
        
        /*System.out.println(bst.maxKey() + ": " + bst.get(bst.maxKey()));
        System.out.println(bst.minKey() + ": " + bst.get(bst.maxKey()));*/
        
        System.out.println(timer.elapsedTime());
    }

}
