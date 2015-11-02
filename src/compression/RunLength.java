package compression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLength {
    private final static int lgR = 8; // bits per counter
    
    private RunLength() { }
    
    private static void compress() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = 0;
            while (!BinaryStdIn.isEmpty() & BinaryStdIn.readBoolean() == bit)
                run++;
            
            BinaryStdOut.write(run, lgR);
            bit = !bit;
        }
        BinaryStdOut.close();
    }
    
    private static void expand() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(lgR);
            for (int i = 0; i < run; i++)
                BinaryStdOut.write(bit);
            bit = !bit;
        }
        BinaryStdOut.close();
    }
    
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
