package structures;

import edu.princeton.cs.algs4.In;

/*
 * With Prob -> 1: list size is within a constant factor of N/M
 */
public class SeparateChainingHashTable<Key, Value> implements HashTable<Key, Value> {
    private int M = 1000;
    private Node[] table = new Node[M];
    
    private static class Node{
        private Object key;
        private Object val;
        private Node next;
        
        public Node(Object key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
    
    private int hash (Key key) {
        if (key == null) return 0;
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    @SuppressWarnings("unchecked")
    public Value get(Key key) {
        int hash = hash(key);
        for (Node x = table[hash]; x != null; x = x.next)
            if (x.key.equals(key)) return (Value) x.val;
        return null;
    }
    
    public void put(Key key, Value val) {
        int hash = hash(key);
        for (Node x = table[hash]; x != null; x = x.next)
            if (key.equals(x.key)){ // update 
                x.val = val;
                return;
            }
        // insertFirst
        table[hash] = new Node(key, val, table[hash]);
    }
    
    public String toString() {
        String s = "";
        for (int i = 0; i < M; i++){
            s += "[" + i + "] ";
            for (Node x = table[i]; x !=null; x = x.next)
                s += x.key + ": " + x.val + " -> ";
            s += '\n';
        }
        return s;
    }

    public static void main(String[] args) {
        SeparateChainingHashTable<String, Integer> ht = new SeparateChainingHashTable<String, Integer>();
        String filename = "hashtable-test.txt";
        In in = new In(filename);
        while (in.hasNextChar())
            ht.put(in.readString(), 0);
        
        System.out.println(ht);
        System.out.println(ht.get("vasek"));
    }

}
