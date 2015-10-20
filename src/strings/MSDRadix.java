package strings;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

/*
 *  1. Go from right to left
 *  2. Sort by cur dth letter
 *  3. Sort arrays recursively
 *  
 *  Works for various lengths.
 *  
 *  Problem:
 *      - we need to keep count[] array for each recusrion call
 *      ASCI - 100x slower for N=2
 *      Unicode - 65000x slower for N=2
 *      
 *      - Make insertion sort for small subarrays 
 *      - use substr(d) to compare strings 
 *      
 *  Time Complexity:
 *      - Worst case: linear (2NW)
 *      - Random words: sublinear (Nlog_R(N) since we don't have to examine all the letters
 *              we can stop after comparing first several chars
 *              
 *  Disadvantages of LSD:
 *      - Access memory randomly (cache inefficient) (unlike quicksort)
 *      - inner loop has a lot of instructions
 *      - extra space for count[] (unlike LSD)
 *      - extra space for aux[] (like in LSD)
 *      
 *  Disadvantage of quicksort:
 *      - linearithmic number of string compares
 *      - has to rescan many chars in keys with long prefix match
 *      
 *  Combine advantages of both???
 */
public class MSDRadix {
    private static final int DEFAULT_CUTTOF = 15;
    private static int CUTOFF; // 1 for testing (comparison) purposes
    private static int opCount;
    
    public static void sort(String[] a) {
        sort(a, DEFAULT_CUTTOF);
    }
    
    public static void sort(String[] a, int cutoff) {
        opCount = 0;
        CUTOFF = cutoff;
        
        int R = 256;
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0, R);

        System.out.println("Exch in array (MSD + cutoff=" + CUTOFF + "): " 
                + opCount + " ~" + opCount / a.length + "N"
                + "; N*log_256(N) = N*" + String.format("%.2f", Math.log(a.length) / Math.log(256) ) );
    }
    
    // recursive subarray sort
    private static void sort(String[] a, String[] aux, int lo, int hi, int d, int R) {
        if (hi - lo < CUTOFF) {
            insertionSort(a, lo, hi, d);
            return;
        }
        
        // 1 for cumsum, 1 for the last char (-1)
        // put last char at pos 1 (-1 + 2 = 1)
        int[] count = new int[R + 2];
        // 1. count chars
        for (int i = lo; i <= hi; i++) 
            count[charAt(a[i], d) + 2]++;
        
        // 2. make cumsum - transform count to indicies
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];
        
        // 3. copy into aux in the right order
        for (int i = lo; i <= hi; i++)
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        
        // 4. move them back into original array in the correct order
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];
        
        opCount += 2*(hi - lo + 1); // two loops of copying
        
        // sort R arrays recursively on the next char
        for (int r = 0; r < R; r++)
            sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1, R);
    }
    
    // use this charAt to put short strings first
    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1; // to put short strings first
    }
    
    private static void insertionSort(String[] a, int lo, int hi, int d) {
        for (int i = 0; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j - 1);
    }
    
    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }
    
    private static void exch(String[] a, int i, int j) {
        opCount++;
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        
        System.out.println(Arrays.toString(strings));
        MSDRadix.sort(strings);
        System.out.println(Arrays.toString(strings));

    }

}
