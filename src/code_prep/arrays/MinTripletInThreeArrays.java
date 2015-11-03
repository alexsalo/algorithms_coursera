package code_prep.arrays;

/*
 * You are given with three sorted arrays ( in ascending order),
 * you are required to find a triplet ( one element from each array)
 * such that distance is minimum.
 * 
 * Distance is defined like this : If a[i], b[j] and c[k] 
 * are three elements then 
 * distance=max(abs(a[i]-b[j]),abs(a[i]-c[k]),abs(b[j]-c[k]))” 
 * Please give a solution in O(n) time complexity
 */
public class MinTripletInThreeArrays {

    // N^3
    public static void findMinDistTripletBrute(int a[], int[] b, int[] c) {
        int minDist = Integer.MAX_VALUE, ma = 0, mb = 0, mc = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b.length; j++)
                for (int k = 0; k < c.length; k++) {
                    int dist = getDist(a[i], b[j], c[k]);
                    if (dist < minDist) {
                        minDist = dist;
                        ma = i;
                        mb = j;
                        mc = k;        
                    }
                }
        System.out.println(String.format("%d, %d, %d minDist = %d", 
                ma, mb, mc, minDist));
    }
    
    private static int getDist(int a, int b, int c) {
        int dist = max(dist(a,b), dist(a,c), dist(b,c));
        return dist;
    }
    private static int dist(int a, int b) {
        int dif = a - b;
        return dif > 0 ? dif : -dif;
    }
    private static int max(int a, int b, int c) {
        if (a > b) return a > c ? a : c;
        else return b > c ? b : c;
    }
    private static int minIndex(int[] index, int a[], int[] b, int[] c) {
        if (a[index[0]] < b[index[1]]) return a[index[0]] < c[index[2]] ? 0 : 2;
        else return b[index[1]] < c[index[2]] ? 1 : 2;
    }
    
    // O(n)
    public static void findMinDistTripletLinear(int a[], int[] b, int[] c) {
        int ma = 0, mb = 0, mc = 0;
        int[] index = new int[3]; // i, j, k
        for (int i = 0; i < 3; i++)
            index[i] = 0;
        int dist = getDist(a[index[0]], b[index[1]], c[index[2]]);
        int minDist = dist;
        index[minIndex(index, a,b,c)]++;
        while (index[0] < a.length && index[1] < b.length && index[2] < c.length) {
            dist = getDist(a[index[0]], b[index[1]], c[index[2]]);
            if (dist < minDist) {
                minDist = dist;
                ma = index[0];
                mb = index[1];
                mc = index[2];
            }

            index[minIndex(index, a,b,c)]++;
        }
        System.out.println(String.format("%d, %d, %d minDist = %d", 
                ma, mb, mc, minDist));
    }
    
    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,4};
        int[] b = new int[]{-500, 100, 500, 1000};
        int[] c = new int[]{8000, 9000, 15000};
        
        findMinDistTripletBrute(a, b, c);
        findMinDistTripletLinear(a, b, c);
    }

}
