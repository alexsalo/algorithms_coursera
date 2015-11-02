package compression;

import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Compression ratio = C(B)/B
 * 
 * 1. Run length compression: 111110000 = 54
 *    How to choose the size of counts? ~lg(R)
 * 
 * 2. Huffman Compression
 *    - use different number of bits to encode different chars
 *    - ensure no codeword is a prefix of another
 *    - use frequency sorted array of tries
 *    
 *    - appl: jpg, mp3, pdf
 *
 *    - drawback: visit data twice - first get the freqs, then compress
 *    
 *    - time: N + R * logR, R - alphabet size
 *    
 * 3. LWZ
 * 
 *    Tradeoff:
 *    - static model for all texts:
 *          - not optimal since different text have different freqs
 *          - fast and no need to transmit codes
 *          (Morse, ASCII)
 *          
 *    - dynamic 
 *          - need preliminary pass
 *          - need to transmit the model
 *          (Huffman code)
 *    
 *    - adaptive:
 *          - progressively learn the model as we read text
 *          - has both accurate modeling and better compression
 *          (LWZ)
 *          
 *   Summary:
 *      - represent fixed-len symbols with variable len codes (Huffman)
 *      - represent variable-len symbols with fixed len codes (LWZ)
 */
public class BitStreamCompression {
    
    public static void writeDate(int day, int month, int year) {
        BinaryStdOut.write(month, 4);
        BinaryStdOut.write(day, 5);
        BinaryStdOut.write(year, 12);
    }

    public static void main(String[] args) {
    }

}
