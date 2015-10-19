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
    
    /*
     *  ~2WN (array moves) - linear!
     *  + (N + R) space
     *  
     *  if need to sort int64[] - represent them as 4 chars (16bit) 
     *      - then W=4 => ~4N sort in 4 passes.
     *      
     *  How to sort (most efficiently) 1M 32 bit integers? 
     *  
     */
    public static void sort(String[] a, int w) {
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
                count[key]++; // move up - eventually till next char 
                // aux[count[a.charAt(d)]++] = a[i];
            }
            
            // N moves
            for (int i = 0; i < N; i++) 
                a[i] = aux[i];
        }
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
