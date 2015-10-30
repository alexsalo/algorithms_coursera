package dynamic_programming;

import java.util.Arrays;

/**
 * Given a[] of arbitrary integers Find: out[], where out[i] = product of all
 * elems except i
 * 
 */
public class ProductExceptI {

    // ~N^2
    public static int[] productExceptIBrute(int[] a) {
        int N = a.length;
        int[] out = new int[N];
        for (int i = 0; i < N; i++) {
            int prod = 1;
            for (int j = 0; j < N; j++)
                if (j != i)
                    prod *= a[j];
            out[i] = prod;
        }
        return out;
    }
    
    // ~N with N extra space
    public static int[] productExceptILinear(int[] a) {
        int N = a.length;
        int[] out = new int[N];
        int products[] = new int[N];
        // ~N
        products[0] = a[0];
        for (int i = 1; i < N; i++) // compute cum-product
            products[i] = products[i - 1] * a[i];
        
        // ~N
        int prodFromTheRight = 1;
        for (int i = N - 1; i > 0; i--) {            
            out[i] = products[i - 1] * prodFromTheRight;
            prodFromTheRight *= a[i];
        }
        out[0] = prodFromTheRight;
        return out;
    }    
     
    /**
     * ~N without extra space for products
     *   a:     [ 2    3      1      4     5 ]
     *   out:   [R3  R2*L0  R1*L1  R0*L2  L3
     *   where R(M) = a[M]*a[M+1]*..*a[N-1]
     *         L(M) = a[N-1]*..*a[M]
     */
    public static int[] productExceptILinearNoExtraSpace(int[] a) {
        int N = a.length;
        int[] out = new int[N];
        //~N
        for (int i = 0; i < N; i++) 
            out[i] = 1;

        // ~N        
        int R = 1, L = 1;
        for (int i = 0; i < N; i++) {            
            out[i] *= R;
            out[N - 1 - i] *= L;
            R *= a[i];
            L *= a[N - 1 - i];
        }
        return out;
    }

    public static void main(String[] args) {
        int[] a = new int[] { 2, 3, 1, 4, 5 };
        System.out.println("Brute: " + Arrays.toString(productExceptIBrute(a)));
        System.out.println("Linear: " + Arrays.toString(productExceptILinear(a)));
        System.out.println("Linear + no extra space: " 
                + Arrays.toString(productExceptILinearNoExtraSpace(a)));
    }

}
