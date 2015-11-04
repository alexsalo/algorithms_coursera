package code_prep.bits;

public class isPowerOfTwo {
    
    public static boolean isPowerTwo(int x) {
        return (x & (x - 1)) == 0;
    }
    
    public static int bitCount(int x) {
        return x == 0 ? 0 : 1 + bitCount(x & (x - 1));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++)
            System.out.println(i + ": " + isPowerTwo(i) 
                    + ", set bits: " + bitCount(i));

    }

}
