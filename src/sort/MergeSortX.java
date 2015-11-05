package sort;

import java.util.Arrays;

/**
 * Using all Improvements:
 *      - cutoff for insertion sort if <= 7 items ~20% faster
 *      - check if sorted before merging  *           
 *      - switch aux and at each recursive call to avoid copying
 *
 */
public class MergeSortX {
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a)  {
        Comparable[] aux = a.clone(); 
        sort(aux, a, 0, a.length - 1);
    }
    
    @SuppressWarnings({ "rawtypes" })
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)  {
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            sort(aux, a, lo, mid);
            sort(aux, a, mid + 1, hi);
            merge(a, aux, lo, mid, hi);
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {        
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                   aux[k] = a[j++]; 
            else if (j > hi)               aux[k] = a[i++]; // j exhausted
            else if (less(a[j], a[i])) aux[k] = a[j++]; // move less one
            else                           aux[k] = a[i++]; 
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void main(String[] args) {
        String[] words = new String[]{"given", "an", "english", "word", 
                "find", "all", "valid", "anagrams", "for", "that", 
                "string", "answer", "on", "the", "precomputing",
                "step", "go", "through", "each", "word", "in", "the", 
                "dictionary", "sort", "the", "letters", "of", "the", 
                "word", "in", "alphabetical", "order"};
        
        System.out.println(Arrays.toString(words));
        sort(words);
        System.out.println(Arrays.toString(words));
        System.out.println("Sorted: " + MergeSort.isSorted(words, 0, words.length - 1));
    }
}
