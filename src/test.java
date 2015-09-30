import java.util.Arrays;

public class test {
    public static void mergeSort(int[] a){
        mergeSort(a, 0, a.length - 1);
    }

    private static void mergeSort(int[] a, int left, int right){
        if (left < right){
            int mid = left + (right - left) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    private static void merge(int[] a, int left, int mid, int right){
        int[] help = new int[a.length];
        for (int i = left; i <= right; i++)
            help[i] = a[i];

        int helpLeft = left;
        int current = left;
        int helpRight = mid + 1;

        while (helpLeft <= mid && helpRight <= right){
            if (help[helpLeft] < help[helpRight]){
                a[current] = help[helpLeft];
                helpLeft++;
            }else{
                a[current] = help[helpRight];
                helpRight++;
    }
    current++;
    }

    // copy left part over
    while (helpLeft <= left)
        a[current++] = help[helpLeft++];

    // rihgt part is already there
    } 
    
    public static void main(String[] args) {
        int[] a = new int[]{6,3,1,76,2,5,7,5};
        mergeSort(a);
        System.out.println(Arrays.toString(a));
    }

}
