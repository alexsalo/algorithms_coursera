package code_prep;

import java.util.Arrays;

public class RearrangeCharsInts {
    
    public static Object swap(Object[] a, Object o, int to) {
        Object tmp = a[to];
        a[to] = o;        
        return tmp;
    }
    
    public static void rearrange(Object[] a) {
        int N = a.length / 2;
        
        // get first element to start the sequence of exchanges
        int pos = 1;
        Object tmp = a[pos];
        a[pos] = null; // mark the start        
        
        while (tmp != null) { // until reached where we started
            if (pos < N) { // ints should move by +pos
                pos += pos;
                // put tmp into its place, and get what was there
                // for the next iteration, keep track of its pos.
                tmp = swap(a, tmp, pos); 
            }
            else { // chars should move like this lol :)
                pos = 2 * (pos - N) + 1;
                tmp = swap(a, tmp, pos);
            }
        }
    }

    public static void main(String[] args) {
        // create array of N ints + N chars
        int N = 10;
        char c = 'a';
        Object[] a = new Object[N * 2];
        for (int i = 0; i < N; i++) {
            a[i] = i + 1;
            a[N + i] = c++;
        }
        System.out.println(Arrays.toString(a));
        
        // Rearrange in 1 a 2 b .. n z
        rearrange(a);
        System.out.println(Arrays.toString(a));        
    }

}
