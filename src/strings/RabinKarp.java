package strings;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 *  Rabin-Karp Algorithm
 *  --------------------
 *  Idea: modular hashing
 *      1. computer a hash (% prime) of pattern chars
 *      2. for each i, compute hash of text char i..M+i-1
 *      3. if hash(pattern) = hash(text) - check for match
 *      
 *  Monte-Carlo - if hash match - return 
 *      ~randomized, could return wrong result with small prob
 *      if Q ~ M*N^2
 *      then P(false collision) = 1/N
 *      
 *      Practice: choose large Prime, P(collision) = 1/Prime
 *      
 *  Las-Vegas - check to make sure we have correct match
 *      - worst case in MN
 *      - likely linear
 *      
 *  Problem:
 *      - slow since ariphmetic ops
 *      - Las Vegas requires backups
 *      - Poor worst-case guarantee
 *      
 *  Benefits:
 *      - can do several patterns at the same time!
 *          Just keep hashes for all the patterns
 *      - can do 2-dim search
 */
public class RabinKarp {
    private static long patternHash;
    private static int M;
    private static int N;
    private static long Prime = 1999999997;
    private static int R = 256; //radix ~10 for decimal
    private static long RM; // radix mod prime: R^(M-1)%Prime    

    public static int indexOfRabinKarp(String text, String pattern) {
        N = text.length(); M = pattern.length();
        patternHash = hashString(pattern);

        RM = 1; // precompute RM
        for (int i = 1; i <= M - 1; i++)
            RM = (R * RM) % Prime;
        
        // search
        long textHash = hashString(text);
        if (textHash == patternHash) return 0;
        for (int i = M; i < N; i++) {
            textHash = hashNext(textHash, text.charAt(i - M), text.charAt(i));
            if (textHash == patternHash) return i - M + 1;
        }
        return -1;
    }
    // Horner's method to compute M-deg polynomial in Linear time
    private static long hashString(String key) {
        long hash = 0;
        for (int j = 0; j < M; j++)
            hash = (R * hash + key.charAt(j)) % Prime;
        return hash;
    }
    /**
     *          i ... 2 3 4 5 6 7 ...
     * hash         1 4 1 5 9 2 6 5                 
     *          
     *          h_i   4 1 5 9 2
     * Subtract lead -4 0 0 0 0
     *             =  0 1 5 9 2
     * Multiply by R *      1 0
     *             =  1 5 9 2 0
     * Add trailing  +        6
     *     h_(i+1) =  1 5 9 2 6    
     */
    private static long hashNext(long hash, char leadchar, char trailchar) {
        hash = (hash + Prime - RM * leadchar % Prime) % Prime;
        return (R * hash + trailchar) % Prime;
    }
    
    public static void main(String[] args) {
        Stopwatch timer;
        String filename = "src/strings/data/tale.txt";
        In in = new In(filename);
        String text = in.readAll();
        String pattern ="so well that my name"; 
        
        timer = new Stopwatch();
        System.out.print("Rabin-Karp: index of '"+ pattern + "': " + RabinKarp.indexOfRabinKarp(text, pattern));
        System.out.println(" in " + timer.elapsedTime());
    }
}
