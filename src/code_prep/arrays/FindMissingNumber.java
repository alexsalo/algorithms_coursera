package code_prep.arrays;

/*
 * You are given a list of n-1 integers 
 * and these integers are in the range of 1 to n. 
 * There are no duplicates in list. 
 * One of the integers is missing in the list. 
 * Write an efficient code to find the missing integer.

    Example:
    I/P    [1, 2, 4,    6, 3, 7, 8]
    O/P    5
 */
public class FindMissingNumber {
    // 1. formula, ~N
    public static int findMissingNumber(int[] a) {
        int n = a.length + 2; // 1..n+1 numbers
        int sum = n * (n - 1) / 2;
        for (int i : a)
            sum -= i;
        return sum;
    }
    
    // 2. do XOR instead to avoid overflow
    // ~2N
    public static int findMissingNumberXOR(int[] a) {
        int n = a.length;
        int i = 1;
        int xor = i;
        while (++i <= n + 1)
            xor ^= i;
        
        int axor = a[0];
        for (int j = 1; j < n; j++)
            axor ^= a[j];
        
        return xor ^ axor;
    }
    
    // 3. array of booleans
    // ~2N + N space
    public static int findMissingNumberBool(int[] a) {
        int n = a.length;
        boolean[] seen = new boolean[n + 1];
        for (int i : a)
            seen[i - 1] = true;
        
        for (int i = 0; i < n + 1; i++)
            if (!seen[i])
                return i + 1;
        return -1;
    }
    
    // 4. bit vector
    public static int findMissingNumberBV(int[] a) {
        int n = a.length;
        int[] bv = new int[n/32 + 1];
        for (int i : a) {
            int p = i - 1;
            int mask = 1 << (p % 32);
            bv[p/32] |= mask;
        }
        
        for (int i = 0; i < n + 1; i++) {
            int mask = 1 << (i % 32);
            if ((bv[i/32] & mask) == 0)
                return i + 1;
        }
        return -1;
    }
    
    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 4, 6, 3, 7, 8};
        System.out.println(findMissingNumber(a));
        System.out.println(findMissingNumberXOR(a));
        System.out.println(findMissingNumberBool(a));
        System.out.println(findMissingNumberBV(a));
    }

}
