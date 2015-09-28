import java.util.Arrays;


// Pros: in-place, NlgN worst case
// Cons: long inner loop, poor use of cache since low spatial locality, not stable
public class HeapSort {
    // This class should not be instantiated.
    private HeapSort() { }
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] pq){
        // Construct heap ~2N
        int N = pq.length;
        for (int k = N /2; k >= 1; k--)
            sink(pq, k, N);
        
        // Sortdown: Get max and sink ~NlgN
        while (N > 1){
            exch(pq, 1, N--);
            sink(pq, 1, N);
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static void sink(Comparable[] pq, int k, int N) {
        while (2 * k <= N) {
            // left kid
            int j = 2 * k;

            // choose larger kid if right one exists
            if (j < N && less(pq, j, j + 1))
                j++;

            // if larger kid is no bigger than father - done here
            if (!less(pq, k, j))
                break;

            // otherwise - exch
            exch(pq, k, j);
            k = j;
        }
    }
    
    // Helpers to deal with the array
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    @SuppressWarnings("rawtypes")
    private static void exch(Comparable[] pq, int i, int j) {
        Comparable<?> tmp = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = tmp;
    }
    
    @SuppressWarnings("rawtypes")
    private static boolean isSorted(Comparable[] a) {
        for (int i = 2; i < a.length; i++)
            if (less(a, i, i-1)) return false;
        return true;
    }

    public static void main(String[] args) {
        String[] a = new String[]{"vasek","alex", "stacey"};
        HeapSort.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(isSorted(a));
    }

}
