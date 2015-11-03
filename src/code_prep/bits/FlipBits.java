package code_prep.bits;

/*
 * Write a function that flips the bits inside a byte.
 */
public class FlipBits {
    
    public static byte flipBits(byte b) {
        for (int i = 0; i < 8; i++) {
            if (getBit(b, i))
                b = clearBit(b, i);
            else
                b = setBit(b, i);
        }
        return b;
    }
    
    private static boolean getBit(byte b, int pos) {
        return (b & (1 << pos)) > 0;
    }
    
    private static byte setBit(byte b, int pos) {
        return (byte) (b | (1 << pos));
    }
    
    private static byte clearBit(byte b, int pos) {
        return (byte) (b & ~(1 << pos));
    }
    

    private static void pf(byte b) {
        String s = String.format("%8s",
                Integer.toBinaryString(b)).replace(' ', '0');
        int N = s.length();
        s = s.substring(N - 8, N);
        System.out.println(s);
    }
    
    public static void main(String[] args) {
        byte b = 14;
        pf(b);
        b = flipBits(b);
        pf(b);
    }

}
