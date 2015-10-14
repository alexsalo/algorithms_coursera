package structures;

/*
 * Fast search, but sacrifice range search compare to BST
 * 
 * Issues:
 *      1. Compute Hash Func
 *      2. Equality Test
 *      3. Collision resolution
 *      
 * Spate-Time tradeoff:
 *      1. If unlimited space - use trivial hash func
 *      2. If unlimited time - trivial collision resolution with sequential search
 *      3. Real word - hashing tradeoff
 *      
 * Result:
 *      Do not use in critical applications where you need GUARANTEES!
 *      - Aircrafts
 *      - Nuclear reactors
 *      
 *      Think of uniform assumptions - if attacker knows that hash - can generate
 *          sequence of keys with the same hash and cause a failure
 *          
 *  One way functions:
 *      - nice for fingerprints (SHA-2)
 *      - too slow for symbol table
 *      
 *  Improvement:
 *      - Two probe hashing
 *      - Double hashing
 *      - Cuckoo hashing 
 *      
 *  Hash Table vs BST
 *      - simpler to code (if nor worries about hash code)
 *      - faster for simpler keys
 *      - better for strings in java
 *      
 *      - no BST performance guarantee
 *      - no BST ordered ST ops
 *      - equals is hard to impl
 *      
 *      Red-Black BST: TreeMap, TreeSet
 *      Hash Table   : HashMap, IdentityHashMap
 *      
 *  Applications:
 *      - Sets: whitelist or blacklist items
 *      - Dictionary: DNS Lookup - inverse DNS lookup
 *      - Indexing
 *      - Concordance
 *      - Sparse matrix-vector multiplication NxN with ~10 non zero values/N
 */
public interface HashTable<Key, Value> {
    //private int M;
    
    /*
     *  1. Efficiently computable
     *  2. Uniformly distributed
     *  3. if x.equals(y) then x.hashCode() = y.hashCode()
     *     if !equal WANT: hash != hash
     *     
     *  Default impl: object address in memory (ok, but not uniformly distr)
     *  
     *  Every class need its own hash. Primitives already have.
     *  
     *  Arrays: Arrays.deepHashCode
     *  Null  : 0
     *  
     *  @return int hashCode 32 bit Integer (could be negative)
     */
    /*public int hashCode(Key key) {
        return 0; 

    
    /* Brithday problem: how many times you throw the ball before the
     *      first collision? ~ sqrt(pi*M/2)
     * 
     * Collector proble: how many time before expect every bin has
     *      at least one bal: ~ M*lnM
     *      
     * Load balancing:  after M balls, expect most loaded to be:
     *      ~ lgM / lglgM
     * 
     * @return int table index 0..M-1
     */
    //private int hash(Key key); 
        // doesn't work since first bit could be 1 = negative
        //return Math.abs(key.hashCode()) % M;
        // return (key.hashCode() & 0x7fffffff) % M;
    
    public abstract void put(Key key, Value val);
    public abstract Value get(Key key);


}
