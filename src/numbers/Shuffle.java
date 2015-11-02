package numbers;

import java.util.Arrays;
import java.util.Random;

public class Shuffle {    
    public static void shuffle(double[] a) {
        Random r = new Random();
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int pos = i + r.nextInt(N - i);
            double tmp = a[i];
            a[i] = a[pos];
            a[pos] = tmp;
        }
                
    }

    public static void main(String[] args) {
        double[] a = new double[]{1.0,2.0,3.0,4.0,5.0};
        System.out.println(Arrays.toString(a));
        shuffle(a);
        System.out.println(Arrays.toString(a));
        shuffle(a);
        System.out.println(Arrays.toString(a));
        shuffle(a);
        System.out.println(Arrays.toString(a));
        shuffle(a);
        System.out.println(Arrays.toString(a));
    }

}
