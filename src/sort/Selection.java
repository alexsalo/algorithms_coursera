package sort;

import java.util.Arrays;

// ~N^2 comparisons, ~N of exchanges
// Insensitive to input
public class Selection { 
    private Selection() { }
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a){
        int N = a.length;
        for (int i = 0; i < N; i++){
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (Helper.less(a[j], a[min]))
                    min = j;
            Helper.exch(a, i, min);
        }
        assert Helper.isSorted(a);
    }

    public static void main(String[] args) {
        String[] a = new String[]{"alex", "stacey", "vasek", "forrest"};
        
        Selection.sort(a);
        System.out.println(Arrays.toString(a));
    }      
}
