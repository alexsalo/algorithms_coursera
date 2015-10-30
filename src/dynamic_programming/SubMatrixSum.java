package dynamic_programming;

public class SubMatrixSum {
    int[][] mat;
    int N;
    int[][] matLin;
    int[][] matSq;
    
    public SubMatrixSum(int[][] mat) {
        N = mat.length;
        this.mat = mat;
    }
    
    /**
     * Naive Brute ~N^2
     * Return sum of submatrix: from left upper corner to x, y
     */
    public int subMatrixSumBrute(int x, int y) {
        int sum = 0;
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                sum += mat[i][j];
        return sum;
    }
    
    /**
     * Linear ~N
     * (compile matrix in ~N^2)
     */
    public int subMatrixSumLinear(int x, int y) {
        if (matLin == null) compileLinearSums();
        
        int sum = 0;
        for (int i = 0; i < x; i++)
            sum += matLin[i][y - 1];
        return sum;
    }
    //compile matrix - cumsums ~N^2   
    private void compileLinearSums() { 
        matLin = new int[N][N];
        for (int i = 0; i < N; i++) {
            matLin[i][0] = mat[i][0]; // first elem stays same
            for (int j = 1; j < N; j++)
                matLin[i][j] = matLin[i][j - 1] + mat[i][j];
        }
    }
    // ~4N arbitrary shape and position matrix
    public int arbitrarySubMatrixSumLinear(int x0, int y0, int x1, int y1) {        
        return subMatrixSumLinear(x1, y1) - subMatrixSumLinear(x0, y1)
             + subMatrixSumLinear(x0, y0) - subMatrixSumLinear(x1, y0);
    }
    
    
    /**
     * Constant time ~1
     * (compile matrix in ~N^)
     */
    public int subMatrixSumConst(int x, int y) {
        if (matSq == null) compileConstSums();
        return matSq[x - 1][y - 1];
    }
    //compile matrix - full cumsums ~N^4 
    private void compileConstSums() { 
        // first get linears
        if (matLin == null) compileLinearSums();
        
        matSq = new int[N][N];
        for (int i = 0; i < N; i++) {
            matSq[0][i] = matLin[0][i]; // first elem stays same
            for (int j = 1; j < N; j++)
                matSq[j][i] = matSq[j - 1][i] + matLin[j][i];
        }
    }
    // ~4 arbitrary shape and position matrix
    public int arbitrarySubMatrixSumConst(int x0, int y0, int x1, int y1) {        
        return subMatrixSumConst(x1, y1) - subMatrixSumConst(x0, y1)
             + subMatrixSumConst(x0, y0) - subMatrixSumConst(x1, y0);
    }

    public static void main(String[] args) {
        int[][] mat = new int[][] {
                {1,2,3}, // 6
                {4,5,6}, // 21
                {7,8,9}  // 45
             //12 27 45 
        };
        SubMatrixSum sbs = new SubMatrixSum(mat);
        
        System.out.println("Brute: " + sbs.subMatrixSumBrute(2, 3));
        
        System.out.println("Linear 2, 3: " + sbs.subMatrixSumLinear(2, 3));
        System.out.println("Linear (1,1) - (3, 3): " 
                + sbs.arbitrarySubMatrixSumLinear(1,1,3,3));
        
        System.out.println("Const 2, 3: " + sbs.subMatrixSumConst(2, 3));
        System.out.println("Const (1,1) - (3, 3): " 
                + sbs.arbitrarySubMatrixSumLinear(1,1,3,3));
        
    }

}
