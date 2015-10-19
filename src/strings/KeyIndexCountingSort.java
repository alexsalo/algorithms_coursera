package strings;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/*
 *  Any compareTo algs requires ~NlgN ops
 *      - We can do better if we do not compare keys!
 *      
 *  If keys are 0..R-1
 *      Use key as an array index
 *      
 *  If just keys - count them up
 *  If also index - 
 *      
 *  Application:
 *      - sort strings by a first letter
 *      - sort phone numbers by area code
 *      - subroutine in a sort alg
 */
public class KeyIndexCountingSort {
    
    public static void sortChars(char[] characters) {
        int R = 256;
        int[] freq = new int[R];
        
        // init freqs
        for (int r = 0; r < R; r++) // ~R
            freq[r] = 0;
        
        // compute freqs
        for (char c : characters) // ~N
            freq[c]++;
        
        // convert to sorted array of chars
        int apos = 0;
        for (int r = 0; r < freq.length; r++) // ~N
            for (int i = 0; i < freq[r]; i++)
                characters[apos++] = (char)r;
    }
    
    public static char[] getChars(String filename) {
        In in = new In(filename);
        ArrayList<Character> chars = new ArrayList<Character>();
        while (!in.isEmpty())
            chars.add(in.readChar());
        
        char[] ch = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++)
            ch[i] = chars.get(i);
        
        return ch;
    }

    public static void main(String[] args) {
        String filename = "src/strings/data/charstosort.txt";
        char[] ch = KeyIndexCountingSort.getChars(filename);        
        
        System.out.println(ch.length + ": " + Arrays.toString(ch));
        KeyIndexCountingSort.sortChars(ch);
        System.out.println(ch.length + ": " + Arrays.toString(ch));
        
        filename = "src/strings/data/10000chars.txt";
        ch = KeyIndexCountingSort.getChars(filename); 
        
        Stopwatch timer = new Stopwatch();
        KeyIndexCountingSort.sortChars(ch);
        System.out.println(timer.elapsedTime());
        
        ch = KeyIndexCountingSort.getChars(filename); 
        
        timer = new Stopwatch();
        Arrays.sort(ch);
        System.out.println(timer.elapsedTime());
    }
}
