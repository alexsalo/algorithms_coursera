package dynamic_programming;

public class KadaneSubarraySum {

    /**
     * Ingenious and simple algorithm. ~N
     */
    public static int maxSubarraySum(int[] a) {
        int max = 0, maxSum = 0;
        for (int i = 0; i < a.length; i++) {
            // here is the key: we keep track of the best subsequence always
            // when we see new elem: either it's going to improve best subseq
            // of we reset it to zero. Since the subseq was the best (invariant)
            // we don't need to keep it if with next elem it gives negative.
            int next = max + a[i]; // compute best_seq + next elem
            max = next > 0 ? next : 0; // choose it if better than 0
            if (max > maxSum) // update global max if
                maxSum = max;
        }
        return maxSum;
    }

    /**
     * Return subarray borders
     */
    public static void maxSubarrayBorders(int[] a) {
        int max = 0, maxSum = 0, N = a.length, l = 0, r = N;
        for (int i = 0; i < N; i++) {
            int next = max + a[i]; // compute best_seq + next elem
            if (next > 0) // choose it if better than 0
                max = next;
            else {
                max = 0;
                l = i + 1; // reset left to current position
            }
            if (max > maxSum) { // update global max if
                maxSum = max;
                r = i;
            }
        }
        for (int i = l; i <= r; i++)
            System.out.print(a[i] + ", ");
        System.out.println("  Max Sum: " + maxSum);
    }

    public static void main(String[] args) {
        int[] a = new int[] { 3, 1, -5, 2, 1, -1, 3 };
        int[] b = new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        
        System.out.println("Max: " + maxSubarraySum(a));
        maxSubarrayBorders(a);

        System.out.println();
        
        System.out.println("Max: " + maxSubarraySum(b));
        maxSubarrayBorders(b);
    }
}
