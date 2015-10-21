package strings;

import java.text.DecimalFormat;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LRS;
import edu.princeton.cs.algs4.Quick3string;
import edu.princeton.cs.algs4.Stopwatch;

/*
 *  Key-word-in-context search
 *  --------------------------
 *  
 *  
 *  Idea: Suffix Search
 *      - Suffix generating
 *      - Binary search for key word
 *      
 *  Application:
 *      - Find the longest repeated substring (Bioinformatics, Crypto, Data Compression)
 *      - Find repetition in music
 *      
 *  Problem:
 *      - Time is a factor of longest prefix, hence with long prefixes - slow
 *      - Bad input: if you have duplicates - you have very long prefix match
 *  
 */
public class SuffixArrays {
    private static int opCount;
    private static Stopwatch timer2;
    
    /*********************************************************************
     ************************* SUBOPTIMAL *********************************
     ********************************************************************/    
    /*
     *  1. Build suffix arrays ~N
     *  2. Sort ~NlgN
     *  3. One scan down to compute longest common prefix
     *  
     *  The problem is: too much extra space
     *  
     *  Better Idea:
     *      1. Sort on the first char
     *      2. Create sorted array of suffixes by increasing size of cmp
     *      3. Terminate if no equal elems
     *      
     */
    public static String findLongestPrefix(String s) {
        opCount = 0;
        int N = s.length();
        
        // costly!
        String[] suffixArray = buildSuffixArray(s);
        
        timer2 = new Stopwatch();
        Quick3string.sort(suffixArray);
        
        /*for (String ss : suffixArray)
            System.out.println(ss);*/
        
        String maxprefix = "";
        for (int i = 1; i < N; i++) {
            int pos = 0;
            opCount++;
            String a = suffixArray[i - 1];
            String b = suffixArray[i];
            int maxlen = maxprefix.length();
            if (a.length() > maxlen && b.length() > maxlen)
                while (pos < a.length() && pos < b.length() &&
                        a.charAt(pos) == b.charAt(pos)) {
                    pos++;
                    opCount++;
                }
            if (pos > maxlen)
                maxprefix = a.substring(0, pos);
        }        
        
        double ops = 1.0 * opCount / N;
        System.out.println("Longest Prefix (" + opCount + " ops = " 
                + "N^" + log2(ops) + "): " + maxprefix);
        return maxprefix;
    }
    
    // ~N
    private static String[] buildSuffixArray(String s) {
        int N = s.length();
        String[] sa = new String[N];
        for (int i = 0; i < N; i++)
            sa[i] = s.substring(i);
        return sa;
    }
    
    
    /*********************************************************************
     ************************* BRUTE *********************************
     ********************************************************************/
    public static String findLongestPrefixBrute(String s) {
        opCount = 0;
        int N = s.length();
        String maxprefix = "";
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int prefixlength = 0;
                int ti = i;
                int tj = j;
                opCount++;
                while (tj < N && s.charAt(ti) == s.charAt(tj)) {
                    opCount++;
                    ti++;
                    tj++;
                    prefixlength++;
                }
                if (prefixlength > maxprefix.length())
                    maxprefix = s.substring(i, i + prefixlength);
            }
        }
        
        double ops = 1.0 * opCount / N;
        System.out.println("Longest Prefix (" + opCount + " ops = " 
                + "N^" + log2(ops) + "): " + maxprefix);
        return maxprefix;
    }
    
    private static double log2(double c) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        double result = Math.log(c) / Math.log(2);
        return Double.valueOf(df2.format(result));
    }

    public static void main(String[] args) {
        //String filename = "src/strings/data/white-space-test.txt";
        String filename = "src/strings/data/medTale.txt";
        //String filename = "src/strings/data/tale.txt";
        //String filename = "src/strings/data/tinyTale.txt";
        //String filename = "src/strings/data/baylor.txt";
        In in = new In(filename);
        String string = in.readAll();
        //System.out.println(string);
        System.out.println("Length: " + string.length() + "\n-----------------");
        
        Stopwatch timer = new Stopwatch();
        //findLongestPrefixBrute(string);
        System.out.println(timer.elapsedTime());
        
        System.out.println();
        timer = new Stopwatch();
        findLongestPrefix(string);
        System.out.println("Total run time: " + timer.elapsedTime());
        System.out.println("Run time without suffix creation: " + timer2.elapsedTime());
        
        System.out.println();
        timer = new Stopwatch();
        System.out.println(LRS.lrs(string));
        System.out.println(timer.elapsedTime());
    }

}
