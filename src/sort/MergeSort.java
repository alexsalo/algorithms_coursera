package sort;

import java.util.Arrays;

/**
 *  Typical "Divide and Conquer":
 *      1. Divide recursively ~lgN
 *      2. Merge ~N
 *  
 *  Properties:
 *      - stable NlogN + N space (NlgN compares and 6NlgN array access)
 *      - not in-place - so we need N extra space
 *      
 *  Improvements:
 *      - cutoff for insertion sort if <= 7 items ~20% faster
 *      - check if sorted before merging 
 *      - switch aux and at each recursive call to avoid copying
 *      
 *  Bottom up:
 *      - consider array as a array of N sorted subarray
 *      - merge by 1, 2, 4 .. N/2
 *  
 *  
 *  Any sort alg based on compares:
 *      - to sort 3 distinct items we need at least 2 compares.
 *      - there are N! leafs in decision tree
 *      - thus any alg should do lg(N!) = NlgN, so mergeSort is optimal
 *        (Stirling formula)
 *      
 *  Assert - check internal invariants
 *         - show intentions before and after
 */
public class MergeSort {
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a)  {
        // create here and pass around
        Comparable[] aux = new Comparable[a.length]; 
        sort(a, aux, 0, a.length - 1);
    }    
    @SuppressWarnings({ "rawtypes" })
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)  {
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid);
            sort(a, aux, mid + 1, hi);
            merge(a, aux, lo, mid, hi);
        }
    }    
    @SuppressWarnings("rawtypes")
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);
        
        for (int k = lo; k <= hi; k++) 
            aux[k] = a[k];
        
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                   break; // a[k] = aux[j++]; already there a[k] exhausted ;
            else if (j > hi)               a[k] = aux[i++]; // j exhausted
            else if (less(aux[j], aux[i])) a[k] = aux[j++]; // move less one
            else                           a[k] = aux[i++]; 
        }
        
        assert isSorted(a, lo, hi);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }
    
    @SuppressWarnings("rawtypes")
    static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = 1; i < a.length; i++) 
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }
    
    
    @SuppressWarnings("rawtypes")
    public static void sortWithoutRecursion(Comparable[] a)  {
        int N = a.length;
        Comparable[] aux = new Comparable[N]; 
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
    }

    public static void main(String[] args) {
        String[] words = new String[]{"given", "an", "english", "word", 
                "find", "all", "valid", "anagrams", "for", "that", 
                "string", "answer", "on", "the", "precomputing",
                "step", "go", "through", "each", "word", "in", "the", 
                "dictionary", "sort", "the", "letters", "of", "the", 
                "word", "in", "alphabetical", "order"};
        String[] ww = words.clone();
        
        System.out.println(Arrays.toString(words));
        sort(words);
        System.out.println(Arrays.toString(words));
        System.out.println("Sorted: " + isSorted(words, 0, words.length - 1));
        
        System.out.println("\nNow with no recursion bottom up:");
        sortWithoutRecursion(ww);
        System.out.println(Arrays.toString(ww));
        System.out.println("Sorted: " + isSorted(ww, 0, ww.length - 1));
    }

}
