package code_prep.arrays;

import java.util.Arrays;

public class RotateMatrix {    
    /**
     * ~N^2 copies + N^2 space
     * @param a - assume N*N matrix
     */
    public static Object[][] rotate90cwBrute(Object[][] a) {
        int N = a.length;
        Object[][] rot = new Object[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                rot[j][i] = a[N - 1 - i][j];
        return rot;
    }
    
    /**
     * ~N^2 swaps + const space
     * @param a - assume N*N matrix
     */
    public static void rotate90cwInPlace(Object[][] a) {
        int N = a.length;
        // 1. Transpose ~ (N^2)/2
        for (int i = 0; i < N; i++)
            for (int j = 0; j < i; j++)
                swap(a, i, j, j, i);
        
        // 2. Rows mirror swam ~ (N ^ 2) / 2
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N / 2; j++)
                swap(a, i, j, i, N - 1 - j);
    }
    
    private static void swap(Object[][] a, int xfrom, int yfrom, int xto, int yto) {
        Object tmp = a[xfrom][yfrom];
        a[xfrom][yfrom] = a[xto][yto];
        a[xto][yto] = tmp;
    }
    
    private static void printMat(Object[][] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            System.out.println(Arrays.toString(a[i]));
        System.out.println();
    }

    public static void main(String[] args) {
        int N = 5;
        Object[][] a = new Object[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = i * N + j;
        
        printMat(a);
        printMat(rotate90cwBrute(a));
        
        rotate90cwInPlace(a);
        printMat(a);
    }

}
