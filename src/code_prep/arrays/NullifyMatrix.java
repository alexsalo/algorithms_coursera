package code_prep.arrays;

import java.util.Arrays;
import java.util.Random;

public class NullifyMatrix {
    
    // ~const space, ~3*N*M + 2 * (N + M)
    public static void nullifyMat(int[][] a) {
        int N = a.length;
        int M = a[0].length;
        
        // 1. check first row ~M
        boolean firstRowHasZero = false;
        for (int i = 0; i < M; i++)
            if (a[0][i] == 0) {
                firstRowHasZero = true;
                break;
            }
        
        // 2. check first col ~N
        boolean firstColHasZero = false;
        for (int i = 0; i < N; i++)
            if (a[i][0] == 0) {
                firstColHasZero = true;
                break;
            }
        
        // 3. check the rest of the rows for zeros ~N*M
        for (int row = 1; row < N; row++)
            for (int col = 1; col < M; col++)
                if (a[row][col] == 0) {
                    a[row][0] = 0;
                    a[0][col] = 0;
                }
        
        // 4. nullify rows ~N*M
        for (int row = 0; row < N; row++)
            if (a[row][0] == 0)
                for (int col = 1; col < M; col++)
                    a[row][col] = 0;
        
        // 5. nullify cols ~N*M
        for (int col = 0; col < M; col++)
            if (a[0][col] == 0)
                for (int row = 1; row < N; row++)
                    a[row][col] = 0;
        
        // 6. handle first row and col ~N + M 
        if (firstColHasZero)
            for (int row = 0; row < N; row++)
                a[row][0] = 0;
        if (firstRowHasZero)
            for (int col = 0; col < M; col++)
                a[0][col] = 0;
    }

    private static void printMat(int[][] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            System.out.println(Arrays.toString(a[i]));
        System.out.println();
    }

    public static void main(String[] args) {
        int N = 5; int M = 10;
        Random r = new Random();
        int[][] a = new int[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                a[i][j] = r.nextInt(M);
        
        printMat(a);
        nullifyMat(a);
        printMat(a);
    }

}
