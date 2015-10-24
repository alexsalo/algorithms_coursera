package strings;

import java.util.Arrays;
import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Input:
 *      - String pattern  M
 *      - String s        N >> M
 *      
 * Application:
 *      Find string between <b> and </b> after first occurrence of 'pattern'
 *    
 * Want: <b>linear time</b> guarantee and <b> avoid backup in text stream.</b> 
 *
 */
public class SubstringSearch {
    
    /**
     * Brute Force Substring Search
     * Worst Case: ~MN
     * Problem: need to backup - can't use one char only once!
     * @param text
     * @param pattern
     * @return index
     */
    public static int indexOfBrute(String text, String pattern) {
        int N = text.length();
        int M = pattern.length();
        for (int i = 0; i < N - M; i++) {
            int pos = 0;
            while (text.charAt(i + pos) == pattern.charAt(pos++)) 
                if (pos == M)
                    return i;            
        }
        return -1;
    }    
    /**
     * Brute Force with Skip
     * Doesn't guarantee the correct result
     */
    public static int indexOfBruteSkip(String text, String pattern) {
        int N = text.length();
        int M = pattern.length();
        for (int i = 0; i < N - M; i++) {
            int pos = 0;
            while (text.charAt(i + pos) == pattern.charAt(pos++)) 
                if (pos == M)
                    return i;
            i += pos - 1; // skip what we've seen
        }
        return -1;
    }
    /**
     * Brute Force with char array
     */
    public static int indexOfBruteChar(String text, String pattern) {
        return indexOfBruteChar(text.toCharArray(), pattern);
    }
    private static int indexOfBruteChar(char[] text, String pattern) {
        int N = text.length;
        int M = pattern.length();
        for (int i = 0; i < N - M; i++) {
            int pos = 0;
            while (text[i + pos] == pattern.charAt(pos++)) 
                if (pos == M)
                    return i;            
        }
        return -1;
    }
    
    
    /**
     * Knuth-Morris-Prat Force Substring Search
     * Clever method to always avoid backup.
     * - Build DFA of the pattern
     * ~N - inspects each char only once!
     * How to compile pattern? 
     * 
     * No memory of what the text is!
     */
    private static int indexOfKnuthMorrisPrat(char[] text, String pattern) {
        int N = text.length;
        int i, j, M = pattern.length();
        int[][] dfa = compilePattern(pattern);
        // stop if N or j become a halt state
        for (i = 0, j = 0; i < N && j < M; i++) 
            j = dfa[text[i]][j];
        // check if we stopped because of halt
        if (j == M) return i - M;
        return -1;
    }
    public static int indexOfKnuthMorrisPrat(String text, String pattern) {
        return indexOfKnuthMorrisPrat(text.toCharArray(), pattern);
    }
    /**
     * Precompute DFA from pattern
     * ~RM
     *  - could be done in M time with NDFA
     */
    private static int[][] compilePattern(String pattern, int R, int shift) {
        int M = pattern.length();
        int[][] dfa = new int[R][M];
        
        //init
        for (int r = 0; r < R; r++) {
            dfa[r][0] = 0;
            dfa[r][1] = 0;
        }
        dfa[pattern.charAt(0) - shift][0] = 1; // first char leads to state 1  (0->1)
        dfa[pattern.charAt(1) - shift][1] = 2; // second char leads to state 2 (1->2)
        dfa[pattern.charAt(0) - shift][1] = 1; // first char leads to state 1  (1->1)
        
        // for the rest construct transitions dynamically
        for (int j = 2; j < M; j++) {
            for (int r = 0; r < R; r++)
                if (r != pattern.charAt(j) - shift) // backup  to what it was before
                    dfa[r][j] = dfa[r][j - 2];
                else // proper transition       
                    dfa[r][j] = j + 1; // straight path
        }
        
        return dfa;
    }
    public static int[][] compilePattern(String pattern) {
        return compilePattern(pattern, 256, 0); 
    }
    
    
    /**
     *  Boyer-Moore
     *  -----------
     *  Scan chars in the pattern from right to left
     *  if mismatch - then skip the entire pattern length
     *          or whatever the skip is - easy to precompute
     */
    private static int indexOfBoyerMoore(char[] text, char[]  pattern) {
        int N = text.length, M = pattern.length, i = M - 1, j = i;
        
        int[] skip = precomputeSkip(pattern);
        
        while(i < N - M) {
            int ti = i;
            while (text[ti--] == pattern[j--]) 
                if (j == 0)
                    return i - M + 1;
            j = M - 1;
            i += skip[text[i]] + 1; //  skip                 
        }
        return -1;
    }
    public static int indexOfBoyerMoore(String text, String pattern) {
        return indexOfBoyerMoore(text.toCharArray(), pattern.toCharArray());
    }
    // ~max(R, M)
    private static int[] precomputeSkip(char[] pattern){
        int R = 256;
        int M = pattern.length;
        int[] skip = new int[R];
        for (int r = 0; r < R; r++)
            skip[r] = M;            // default skip is the entire pattern length
        for (int j = 0; j < M; j++) // find the max skip - so many chars needed before
            skip[pattern[j]] = j; 
        return skip;
    }
    
    
    /**
     * Copied from Java impl
     */
    static int indexOfJava(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount,
            int fromIndex) {

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }
    

    public static void main(String[] args) {
        Stopwatch timer;
        String filename = "src/strings/data/tale.txt";
        //String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String text = in.readAll();
        //String pattern = "faltering";
        String pattern ="so well that my name"; 
        //String pattern = "seashell";
        
        
        HashSet<Character> set = new HashSet<Character>();
        for (char c : text.toCharArray())
            set.add(c);
        System.out.println(set);
        
        for (int[] row : compilePattern("ababac", 3, 97))
            System.out.println(Arrays.toString(row));
        
        timer = new Stopwatch();
        System.out.print("Java:       index of '"+ pattern + "': " + text.indexOf(pattern));
        System.out.println(" in " + timer.elapsedTime());
        
        timer = new Stopwatch();
        System.out.print("Java man  : index of '"+ pattern + "': " + 
                indexOfJava(text.toCharArray(), 0, text.length(), 
                pattern.toCharArray(), 0, pattern.length(), 0));
        System.out.println(" in " + timer.elapsedTime());
        
        timer = new Stopwatch();
        System.out.print("Brute     : index of '"+ pattern + "': " + indexOfBrute(text, pattern));
        System.out.println(" in " + timer.elapsedTime());
        
        timer = new Stopwatch();
        System.out.print("Brute Skip: index of '"+ pattern + "': " + indexOfBruteSkip(text, pattern));
        System.out.println(" in " + timer.elapsedTime());
        
        timer = new Stopwatch();
        System.out.print("Brute Char: index of '"+ pattern + "': " + indexOfBruteChar(text, pattern));
        System.out.println(" in " + timer.elapsedTime());
        
        timer = new Stopwatch();
        System.out.print("Knuth-Prat: index of '"+ pattern + "': " + indexOfKnuthMorrisPrat(text, pattern));
        System.out.println(" in " + timer.elapsedTime());
        
        timer = new Stopwatch();
        System.out.print("BoyerMoore: index of '"+ pattern + "': " + indexOfBoyerMoore(text, pattern));
        System.out.println(" in " + timer.elapsedTime());
    }

}
