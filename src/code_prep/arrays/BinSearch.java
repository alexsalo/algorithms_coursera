package code_prep.arrays;

public class BinSearch {    
    public static int binarySearch(int[] a, int x) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == x)
                return mid;
            else if (x < a[mid])
                hi = mid - 1;
            else // x > a[mid]
                lo = mid + 1;
        }
        return -1;
    }
    
    public static int maxSubarrayKadaneSum(int[] a) {
        int sum = 0;
        int maxSum = 0;
        for (int i = 1; i < a.length; i++) {
            int newsum = sum + a[i];
            sum = newsum > 0 ? newsum : 0;
            if (sum > maxSum)
                maxSum = sum;
        }
        return maxSum;
    }
    
    public static void main(String[] args) {
        int[] a = new int[]{1,3,4,5,7,8,9,11};
        for (int i = -3; i < 13; i++)
            System.out.println(i + ": " + binarySearch(a, i));
        
        int[] b = new int[]{1,3,4,5,-12, 7,8, -5, 9,11, -4};
        System.out.println(maxSubarrayKadaneSum(b));
    }

}
