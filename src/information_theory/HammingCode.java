package information_theory;
import java.util.Arrays;

/** 
 * @author Aleksandr Salo
 * Client to compute HammingCode
 * For class Advanced Computer Organization
 */
public class HammingCode {

    private static int countBits(int x) {
        return x == 0 ? 0 : countBits(x & (x - 1)) + 1;
    }
    
    public static String hammingCode(String input) {
        int M = input.length();
        
        // 1. read bits
        char[] inputchars = input.toCharArray();
        int[] bits = new int[M];
        for (int i = 0; i < M; i++)
            bits[i] = Character.getNumericValue(inputchars[i]);
        System.out.println("Input: " + Arrays.toString(bits));
        
        // 2. figure out redundancy bits length k
        int k = 2;
        while (Math.pow(2, k) - k - 1 < M)
            k++;
        System.out.println("Choose k = "+ k + 
                " to detect and correct single bit error");
        
        // 3. prepare resulting Hamming code
        int[] hamcode = new int[M+k];
        int m = 0;
        for (int i = 0; i < M+k; i++) //start with 2
            if (countBits(i + 1) == 1) // if power of 2
                hamcode[i] = 0; // init parity bits with 0
            else
                hamcode[i] = bits[m++];
        System.out.println("With Empty parity: " + Arrays.toString(hamcode));
        
        // 4. compute parity bits
        for (int parityBit = 0; parityBit < k; parityBit++) {
            int skip = 1 << parityBit;
            int cur = skip - 1;
            int parity = 0;
            while (cur < M + k) {
                //System.out.println(cur + " -> " + (cur + skip));
                for (int i = cur; i < cur + skip && i < M + k; i++)
                    parity += hamcode[i];
                cur += 2 * skip; // skip two
            }
            //System.out.println((skip - 1) + " -> " + parity % 2);
            hamcode[skip - 1] = parity % 2; // set bit if odd 
        }
        System.out.println("Hamming Code Word: " + Arrays.toString(hamcode));
        
        // 5. make resulting string
        String s = "";
        for (int i = 0; i < hamcode.length; i++)
            s += String.valueOf(hamcode[i]);
        return s;
    }
    
    // test client
    public static void main(String[] args) {
        String[] input = new String[] {
                "01101001",
                "00010001",
                "11111010",
                "10101001",
                "01010011"
        };
        
        // Compute Ham Codes
        String[][] hamCodes = new String[input.length][2];
        for (int i = 0; i < input.length; i++) {
            hamCodes[i][0] = input[i];
            hamCodes[i][1] = hammingCode(input[i]);
        }
        
        // print Message, CodeWord
        for (int i = 0; i < input.length; i++)
            System.out.println(Arrays.toString(hamCodes[i]));
    }
}
