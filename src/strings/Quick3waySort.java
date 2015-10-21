package strings;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

/*
 *  Do 3-way partitioning on the dth char (rather than R-way in MSD)
 *  (buckets: less than, equal to, more than)
 *      - Less overhead
 *      - Doesn't reexamine chars again and again
 *      - cache friendly
 *      - in-place
 *      - short inner loop
 *   So, the best way to sort strings!
 *      
 *  !NOT STABLE!
 *  
 *  Time complexity:
 *      - regular quicksort  ~2NlnN STRING comparison 
 *              (costly for keys with long common prefixes)
 *      - 3wayChar quciksort ~2NlnN CHARS comparisons
 *              (avoid re-comparing commong prefixes)
 *      - MSD - cache inefficient + too much memory storing count[]        
 *        
 */
public class Quick3waySort {
    private static final int CUTOFF =  15;   // cutoff to insertion sort
    private static int opCount;
    
    public static void sort(String[] a) {
        
        opCount = 0;
        sort(a, 0, a.length - 1, 0);
        System.out.println("Exch in array (3WayQuick): " 
                + opCount + " ~" + opCount / a.length + "N");
    }
    
    private static void sort(String[] a, int lo, int hi, int d) {
        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertionSort(a, lo, hi, d);
            return;
        }
        
        int lt = lo, gt = hi; // indicies of where we now put partitioned elems
        int pivot = charAt(a[lo], d);
        int i = lo + 1; // start index
        
        // we grow     i++ if we put into less than bucket
        // or decrease gt-- if we put i into more than bucket, and move gt into i
        while (i <= gt) {
            int curchar = charAt(a[i], d);
            if (curchar < pivot) exch(a, lt++, i++); // put at the beginning and move on
            if (curchar > pivot) exch(a, i, gt--); // throw back, try one from back (loose stability)
            else                 i++; // it belongs here anyways - skip
        }
        
        sort(a, lo, lt - 1, d); // sort lower part on the same dth char
        if (pivot >= 0) sort(a, lt, gt, d + 1); // dth char sorted -> move to d+1 th char
        sort(a, gt + 1, hi, d); // sort higher part on the same dth char
    }
    
    // use this charAt to put short strings first
    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1; // to put short strings first
    }
    
    private static void exch(String[] a, int i, int j) {
        opCount++;
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    private static void insertionSort(String[] a, int lo, int hi, int d) {
        for (int i = 0; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j - 1);
    }
    
    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        
        System.out.println(Arrays.toString(strings));
        Quick3waySort.sort(strings);
        System.out.println(Arrays.toString(strings));
    }

}
