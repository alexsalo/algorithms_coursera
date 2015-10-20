package strings;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

/*
 *  - Stably sort dth char in the string via key-indexed counting.
 *  - move right to left
 *  (assume equal length)
 *  
 *  ~ takes N * |N|
 */
public class LSDRadix {
    private static int opCount;
    
    /*
     *  ~2WN (array moves) - linear!
     *  + (N + R) space
     *  
     *  if need to sort int64[] - represent them as 4 chars (16bit) 
     *      - then W=4 => ~4N sort in 4 passes.
     *      
     *  How to sort (most efficiently) 1M 32 bit integers? 
     *  
     *  Ok, so the true time complexity is:
     *      2*W*(N + R) // 2 - for aux cost, W - # of passes, 
     *                  // N - for each elem, R - # of buckets
     *                  
     *  To sort a 1M 32 bit integers:
     *  LSD:
     *      1  pass   of 32 bit: ~2*1*(2^20 + 2^32) = ~2^33
     *      2  passes of 16 bit: ~2*2*(2^20 + 2^16) = ~2^22
     *      4  passes of 8  bit: ~2*4*(2^20 + 2^8 ) = ~2^23
     *      8  passes of 4  bit: ~2*8*(2^20 + 2^4 ) = ~2^24
     *      16 passes of 2 bit: ~2*16*(2^20 + 2^2 ) = ~2^25
     *      32 passes of 1 bit: ~2*32*(2^20 + 2^1 ) = ~2^26
     *     
     *  MergeSort:
     *      N*lgN = 2^20 * lg(2^20) = 20 * 2^20     = ~2^24
     */
    
    public static void sort(String[] a, int w) {
        opCount = 0;
        
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];
        
        // for each column of chars perform key-index counting
        for (int d = w - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            
            // count up chars at dth position in strings in order
            // ~N
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            
            // sum them up to make cumulative sum - start index
            // ~R
            for (int r = 1; r < R; r++)
                count[r] += count[r - 1];
            
            // move strings according to their computed index
            // ~4N array access + N move
            for (int i = 0; i < N; i++) {
                char key = a[i].charAt(d); // 
                int pos = count[key]; // where we are now for this char
                aux[pos] = a[i]; // put string to that index
                opCount++; // testing
                count[key]++; // move up - eventually till next char 
                // aux[count[a.charAt(d)]++] = a[i];
            }
            
            // N moves
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
                opCount++; // testing
            }
        }

        System.out.println("Exch in array (LSD): " + opCount);
    }

    public static void main(String[] args) {
        String filename = "src/strings/data/words3.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        
        System.out.println(Arrays.toString(strings));
        LSDRadix.sort(strings, 3);
        System.out.println(Arrays.toString(strings));
    }

}
