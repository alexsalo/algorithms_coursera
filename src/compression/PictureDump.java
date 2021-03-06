package compression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdDraw;

public class PictureDump {

    // Do not instantiate.
    private PictureDump() { }

    /**
     * Reads in a sequence of bytes from standard input and draws
     * them to standard drawing output as a width-by-height picture,
     * using black for 1 and white for 0 (and red for any leftover
     * pixels).
     */
    public static void main(String[] args) {
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        Picture picture = new Picture(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                picture.set(col, row, StdDraw.RED);
                if (!BinaryStdIn.isEmpty()) {
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) picture.set(col, row, StdDraw.BLACK);
                    else     picture.set(col, row, StdDraw.WHITE);
                }
            }
        }
        picture.show();
    }
}