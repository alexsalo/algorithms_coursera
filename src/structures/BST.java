package structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
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
 * 
 * search, insert, min/max, florr/ rank/ select ~ height(bst) 
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
        
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.count = 1;
        }
    }
    
    /****************************************************************
     ************************* PUT GET DELETE ***********************
     ****************************************************************/    
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
        
        // maintain size
        x.count = 1 + size(x.left) + size(x.right);
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
    
    public void deleteMin(){
        root = deleteMin(root);
    }
    
    /*
     * 1. Go left until found a node with a null left link
     * 2. Replace node by its right link
     * 3. Update subtree counts
     */
    private Node deleteMin(Node x){
        if (x.left == null) {
            System.out.println("Deleting min... '" + x.key + "'");
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    public void deleteMax(){
        root = deleteMax(root);
    }
    
    private Node deleteMax(Node x){
        if (x.right == null) {
            System.out.println("Deleting max... '" + x.key + "'");
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    public void deleteKey(Key key){
        root = deleteKey(root, key);
    }
    
    /*
     * Hibbard Deletion
     * Case 1: no kids - return null
     * Case 2: only one kid - return that kid, garbage collector will remove node
     * Case 3: two kids - find the next smallest node in the right subtree
     *         then just deleteMin()
     * Problem: if you insert/delete many times at random - BST becomes much 
     *          less balanced, Height ~ sqrt(N)
     */
    private Node deleteKey(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        
        if (cmp < 0) x.left = deleteKey(x.left, key);
        if (cmp > 0) x.right = deleteKey(x.right, key);
        else{
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            
            // two kids
            Node tmp = x; 
            x = min(x.right); // replace x by its smallest successor (on the right) 
            // for the right subtree put tmp, in which 
            // delete that smallest succesor itself
            x.right = deleteMin(tmp.right); 
            x.left = tmp.left; // left part stays the same
            
            // Q: why successor, not predescessor? No reason - unsatisfactory.             
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    /****************************************************************
     ************************* BST API ******************************
     ****************************************************************/  
    public int size(){
        return size(root);
    }
    
    private int size(Node x){
        if (x == null)  return 0;
        return x.count;
    }
    
    private Node min(Node x){
        while (x.left != null)
            x = x.left;
        return x;
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
    
    public Key floor(Key key){
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }
    
    private Node floor(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        
        // if cmp > 0
        Node t = floor(x.right, key);
        if (t != null) return t;
        else           return x;
    }
    
    public int rank(Key key){
        return rank(root, key);
    }
    
    private int rank(Node x, Key key){
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        
        if (cmp == 0)       return size(x.left);
        else if (cmp < 0)   return rank(x.left, key);
        // all to the left + the node itself + whatever the res to the right
        else return 1 + size(x.left) + rank(x.right, key);
    }
    
    public Iterable<Key> keySet(){
        Queue<Key> keys = new LinkedList<Key>();
        inOrderTraversal(root, keys);
        return keys;
    }
    
    /****************************************************************
     ************************* Traversals ***************************
     ****************************************************************/    
    
    /*        *  
     *       / \
     *      /   \
     */
    private void inOrderTraversal(Node start, Queue<Key> keys){
        if (start == null) return;
        inOrderTraversal(start.left, keys);
        keys.add(start.key);
        inOrderTraversal(start.right, keys);
    }
    
    public Iterable<Key> keySetLevelOrder(){
        Queue<Key> keys = new LinkedList<Key>();
        levelOrderTraversal(root, keys);
        return keys;
    }    
    
    private void levelOrderTraversal(Node start, Queue<Key> keys){
        if (start == null) return;            
        keys.add(start.key);
        levelOrderTraversal(start.left, keys);
        levelOrderTraversal(start.right, keys);
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
    
    public static void main(String[] args) throws FileNotFoundException {
        int minlen = Integer.parseInt(args[0]);
        //SymbolTable<String, Integer> st = new SymbolTable<String, Integer>();
        BST<String, Integer> bst = new BST<String, Integer>();
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("text.txt")));
        Scanner sc = null;
        while (scanner.hasNext()){
            sc = new Scanner(scanner.nextLine());
            while (sc.hasNext()){
                String word = sc.next();
                if (word.length() < minlen) continue;  // ignore short words
                if (!bst.containsKey(word)) bst.put(word, 1);
                else                    bst.put(word, bst.get(word) + 1);
            }
        }
        scanner.close();
        sc.close();
        
        System.out.println(bst.keySet());
        System.out.println(bst.keySetLevelOrder());
        System.out.println(bst.toString());
        System.out.println("Max key: " + bst.maxKey());
        System.out.println("Min key: " + bst.minKey());
        
        String max = bst.keySet().iterator().next();
        for (String word : bst.keySet())
            if (bst.get(word) > bst.get(max))
                max = word;
        System.out.println("Most popular word is:   \"" + max + 
                "\"   encountered: " + bst.get(max) + " times.");
        
        System.out.println("BST size: " + bst.size());
        System.out.println("'Bottom' rank: " + bst.rank("bottom"));
        System.out.println("'answering' floor: " + bst.floor("answering"));
        
        bst.deleteMin();
        bst.deleteMin();
        bst.deleteMax();
        System.out.println("Min key deleted, BST size: " + bst.size());
        System.out.println(bst.keySet());
    }
}
