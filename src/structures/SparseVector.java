package structures;

import java.util.Hashtable;

public class SparseVector {
    // non empty index - value
    private Hashtable<Integer, Double> v;
    
    public SparseVector(double[] a){
        v = new Hashtable<Integer, Double>();
        for (int i = 0; i < a.length; i++){
            if (a[i] != 0.0)
                put(i, a[i]);
        }
    }
    
    public void put(int i, double x){
        v.put(i,  x);
    }
    
    public double get(int i){
        if (!v.contains(i)) return 0.0;
        else return v.get(i);
    }
    
    public Iterable<Integer> indicies(){
        return v.keySet();
    }
    
    public double dotProduct(double[] that){
        double sum = 0.0;
        for (int i : indicies())
            sum += v.get(i) * that[i];
        return sum;
    }

    public static void main(String[] args) {
        SparseVector v = new SparseVector(new double[]{1.0, 0.0, 0.0, 0.5});
        System.out.println(v.dotProduct(new double[]{3.0, 0.0, 0.0, 2}));

    }

}
