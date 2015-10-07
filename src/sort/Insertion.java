package sort;

import java.util.Arrays;

// Best case - ~N
// Worst case 1/2 * N^2 comparisons and exchanges
// Nice for partially sorted - if has multiple inversions
// Runs in linear time of number of inversions B A D C F E = ~3
public class Insertion {
    public Insertion() {
    }

    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--)
                if (Helper.less(a[j], a[j - 1]))
                    Helper.exch(a, j, j - 1);
                else
                    break;
        }
        assert Helper.isSorted(a);
    }

    public static void main(String[] args) {
        String[] a = new String[] { "alex", "stacey", "vasek", "forrest" };
        Insertion.sort(a);
        System.out.println(Arrays.toString(a));
    }

}
