package strings;


public class Dict {    
    protected static final int R = 26; // ASCI
    protected static final int shift = 97; // 'a' 
    protected Node root = new Node();

    static class Node {
        protected Boolean isWord = false; // since no generic array creation
        protected Node parent = null;
        protected Node[] next = new Node[R];
    }
    
    public void put(String key, Boolean val) {
        root = put(root, key, val, 0, null);
    }
    
    private Node put(Node x, String key, Boolean val, int d, Node parent) {
        if (x == null) {
            x = new Node(); // found appropriate empty position
            x.parent = parent;
        }
        
        if (d == key.length()) { // update value
            x.isWord = val;
            return x;
        }
        
        int c = key.charAt(d) - shift; // get the index - the kid
        x.next[c] = put(x.next[c], key, val, d + 1, x); // go one level down
        
        return x;
    }     
}
