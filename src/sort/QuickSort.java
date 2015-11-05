package sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Plan (Hoare):
 *      1. Shuffle
 *      2. Partition
 *      3. Sort each piece recursively
 * 
 * Properties:
 *      - in-place
 *      - tricky implementation
 *      - need to shuffle to guarantee perfomance
 *      - can stop pointers on duplicate 
 *      - best case: NlgN
 *      - worst case: 1/2 N^2
 *
 * Run time:
 *     ~1.39*NlgN
 *     more compares, but less moves than MergeSort
 *     
 * Improvements:
 *      - insertion sort for <= 10
 *      - or do one pass of insertion in the end
 *      - choose pivot as median of 3 random pivots +~10%
 *      
 * Duplicates:
 *      Often have we sort to find duplicates
 *      Typical: huge file, small number of possible values
 *      - MergeSort always uses NlgN
 *      - Use 3-way - to avid N^2 on dups
 *      
 * Problem of Selection:
 *      - find k-th largest in arbitrary array (max, min, median or arbitrary k-th).
 */
public class QuickSort {
    @SuppressWarnings("rawtypes")
    private static Comparator comp;
    
    @SuppressWarnings("rawtypes")
    public static void sort(Object[] a, Comparator comparator) {
        comp = comparator;
        Shuffle.shuffle(a); // ~N
        sort(a, 0, a.length - 1);
    }
    public static void sort(Object[] a, int lo, int hi) {
        if (lo < hi) {
            int j = partition(a, lo, hi);
            sort(a, lo, j - 1);
            sort(a, j + 1, hi);
        }
    }    
    
    private static int partition(Object[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            Object pivot = a[lo]; // since we shuffled just take first
            while (less(a[++i], pivot)) // find next out of place on the left
                if (i == hi) break;
            while (less(pivot, a[--j])); // find next out of place on the right
                // redundant step since we check with pivot if (j == lo) break; 
            if (i >= j) break;
            exch(a, i, j); // swap to restore invariant
        }
        exch(a, lo, j); // put pivot where it belongs
        return j; // index of where item is known to be in place
    }
    
    @SuppressWarnings("rawtypes")
    public static void sort3way(Object[] a, Comparator comparator) {
        comp = comparator;
        Shuffle.shuffle(a); // ~N
        sort3way(a, 0, a.length - 1);
    }
    @SuppressWarnings("unchecked")
    private static void sort3way(Object[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        Object pivot = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = comp.compare(a[i], pivot);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else              i++;
        }
        
        sort3way(a, lo, lt - 1);
        sort3way(a, gt + 1, hi);
    }
    
    @SuppressWarnings("unchecked")
    private static boolean less(Object a, Object b) {
        return comp.compare(a, b) < 0;
    }
    private static void exch(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;        
    }

    @SuppressWarnings("rawtypes")
    public static Object select(Object[] a, int k, Comparator comparator) {
        comp = comparator;
        Shuffle.shuffle(a); // ~N
        int lo = 0; int hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if      (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else            return a[k];
        }
        return a[k];
    }

    public static void main(String[] args) {
        String[] words = new String[]{"given", "an", "english", "word", 
                "find", "all", "valid", "anagrams", "for", "that", 
                "string", "answer", "on", "the", "precomputing",
                "step", "go", "through", "each", "word", "in", "the", 
                "dictionary", "sort", "the", "letters", "of", "the", 
                "word", "in", "alphabetical", "order"};
        String[] ww = words.clone();
        String[] w3 = words.clone();
        
        System.out.println(Arrays.toString(words));
        sort(words, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(words));
        System.out.println("Sorted: " + MergeSort.isSorted(words, 0, words.length - 1));
        
        System.out.println("\nSelect k-th:");
        System.out.println(select(ww, 4, String.CASE_INSENSITIVE_ORDER));        
        
        System.out.println("\n3 Way: ");
        sort3way(w3, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(w3));
        System.out.println("Sorted: " + MergeSort.isSorted(w3, 0, w3.length - 1));
    }

}
