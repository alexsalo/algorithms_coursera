package sort;

import java.util.Arrays;

// run insertion sort with stride h several times decreasing h
// based on the fact that if you take h-sorted array and then k-sort, k < h
// it still stays h-sorted
// for h use 3n+1

// O(N^(3/2))
public class Shell {
    public Shell() { }

    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a){
        int N = a.length;
        
        int h = 1;
        while (h < N/3)  // find first h stride
            h = 3 * h + 1; // 1, 4, 13, 40, 121
        
        while (h >= 1){ // h-sort the array
            for (int i = h; i < N; i++){ // insertion sort with lag h
                for (int j = i; j >= j && Helper.less(a[j], a[j - h]); j-= h)
                    Helper.exch(a, j, j - h);
            }
            h /= 3;
        }
        
        assert Helper.isSorted(a);
    }

    public static void main(String[] args) {
        String[] a = new String[]{"alex", "stacey", "vasek", "forrest"};
        
        Shell.sort(a);
        System.out.println(Arrays.toString(a));
    }   

}
