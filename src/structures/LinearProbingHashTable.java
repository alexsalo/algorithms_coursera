package structures;

import edu.princeton.cs.algs4.In;

/*
 *  Displacement - how many times you need to rehash before you find empty slot? 
 *  Load factor = M / N < 1
 *  
 *  Mean displacement = md
 *  
 *  md(0.5) = ~ 3/2
 *  md(1) = ~sqrt(pi*M/8)
 *  
 *  md ~ (1/2 * (1 + 1/(1-a)) - hit
 *  md ~ (1/2 * (1 + 1/(1-a)^2) - miss
 *  
 *  Result: keep table 1/2 full.
 */

@SuppressWarnings("unchecked")
public class LinearProbingHashTable<Key, Value> implements HashTable<Key, Value> {
    private int M = 15;
    private int N = 0;
    private Key[]   keys = (Key[]) new Object[M];
    private Value[] vals = (Value[]) new Object[M];
    
    private int hash (Key key) {
        if (key == null) return 0;
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (key.equals(keys[i]))
                return vals[i];
        return null;
    }
    
    public void put(Key key, Value val) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                break;

        keys[i] = key;
        vals[i] = val;
        N++;
    }
    
    public String toString() {
        String s = "";
        for (int i = 0; i < M; i++)
            s += "[" + i + "] " + keys[i] + "(hash: " + hash(keys[i]) + "): " + vals[i] + '\n';
        s += "Load factor: " + 1.0 * N / M;
        return s;
    }

    public static void main(String[] args) {
        LinearProbingHashTable<String, Integer> ht = new LinearProbingHashTable<String, Integer>();
        String filename = "hashtable-test.txt";
        In in = new In(filename);
        while (in.hasNextChar())
            ht.put(in.readString(), 0);
        
        System.out.println(ht);
        System.out.println(ht.get("vasek"));
        System.out.println(ht.get("nothing"));
    }

}
