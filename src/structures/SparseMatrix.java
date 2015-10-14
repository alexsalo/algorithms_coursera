package structures;

import java.util.Arrays;

public class SparseMatrix {
    int N;
    SparseVector[] mat;
    
    public SparseMatrix(double[][] a){
        N = a.length; 
        mat = new SparseVector[N];
        for (int i = 0; i < N; i++){
            double[] row = new double[N];
            for (int j = 0; j < N; j++)
                row[j] = a[i][j];
            mat[i] = new SparseVector(row);
        }
    }
    
    public double[] dotProduct(double[] that) {
        double[] res = new double[N];
        for (int i = 0; i < N; i++)
            res[i] = mat[i].dotProduct(that);
        return res;
    }

    public static void main(String[] args) {
        SparseMatrix mat = new SparseMatrix(new double[][]{
                {1.0, 0.0, 0.0, 0.5},
                {2.0, 0.0, 0.0, 1.5},
                {3.0, 0.0, 0.0, 2.5},
                {4.0, 0.0, 0.0, 3.5}
        });
        System.out.println(Arrays.toString(mat.dotProduct(new double[]{3.0, 0.0, 0.0, 2})));
    }

}
