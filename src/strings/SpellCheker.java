package strings;

import edu.princeton.cs.algs4.In;

public class SpellCheker {
    private static final int R = 26; // ASCI
    private static final int shift = 97; // 'a' 
    private Node root = new Node();

    private static class Node {
        private Boolean val = false; // since no generic array creation
        private Node[] next = new Node[R];
    }
    
    public void put(String key, Boolean val) {
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, Boolean val, int d) {
        if (x == null) x = new Node(); // found appropriate empty position
        
        if (d == key.length()) { // update value
            x.val = val;
            return x;
        }
        
        int c = key.charAt(d) - shift; // get the index - the kid
        x.next[c] = put(x.next[c], key, val, d + 1); // go one level down
        
        return x;
    }

    public Boolean get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.val;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null) return null; // not found
        if (d == key.length()) return x; //hit
        int c = key.charAt(d) - shift;
        return get(x.next[c], key, d + 1);
    }
    
    public static void main(String[] args) {
        String filename = "src/strings/data/web2.txt";
        In in = new In(filename);
        
        SpellCheker checker = new SpellCheker();
        
        while (!in.isEmpty()) {
            String s = in.readString();            
            checker.put(s.toLowerCase(), true);
        }
        
        in = new In("src/strings/data/spell-checker-test.txt");
        while (!in.isEmpty()) {
            String s = in.readString();            
            if (!checker.get(s.toLowerCase()))
                s = "--" + s + "--";
            System.out.print(s + " ");
        }
        
        // how to make return closest option instead:
        // - have a misspell dictionary
        // - go up the tree
    }
}
