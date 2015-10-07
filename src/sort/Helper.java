package sort;

public class Helper {
    public Helper() { }

    /***************************************************************************
     * Helper sorting functions.
     ***************************************************************************/
    @SuppressWarnings({ "unchecked", "rawtypes" })
    static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) <= 0;
    }

    static void exch(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    // is the array a[] sorted?
    @SuppressWarnings({ "rawtypes" })
    static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    @SuppressWarnings("rawtypes")
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }
}
