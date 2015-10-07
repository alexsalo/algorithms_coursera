package sort;

import java.util.Random;

// 1. Generate n uniformly random doubles and sort ~NlgN
// 2. Knuth: swap a[i] and a[r], where i - rand from unseen, r - rand from seen ~N
public class Shuffle {
    // Knuth ~N
    public static void shuffle(Object[] a){
        Random r = new Random();
        for (int i = 0; i < a.length; i++)
            Helper.exch(a, i, r.nextInt(i + 1));
    }

    public static void main(String[] args) {

    }

}
