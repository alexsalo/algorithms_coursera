package strings;

import java.text.DecimalFormat;

import edu.princeton.cs.algs4.In;

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
 *  
 *  
 */
public class SuffixArrays {
    private static int opCount;
    
    /*
     *  1. Build suffix arrays ~N
     *  2. Sort ~NlgN
     *  3. One scan down to compute longest common prefix
     */
    public static String findLongestPrefix(String s) {
        opCount = 0;
        
        String[] suffixArray = buildSuffixArray(s);
        Quick3waySort.sort(suffixArray);               
        
        String maxprefix = "";
        int N = suffixArray.length;
        for (int i = 1; i < N; i++) {
            int pos = 0;
            opCount++;
            while (pos < suffixArray[i - 1].length() && pos < suffixArray[i].length() &&
                    suffixArray[i - 1].charAt(pos) == suffixArray[i].charAt(pos)) {
                pos++;
                opCount++;
            }
            if (pos > maxprefix.length())
                maxprefix = suffixArray[i].substring(0, pos);
        }        
        
        double ops = 1.0 * opCount / N;
        System.out.println("Longest Prefix (" + opCount + " ops = " 
                + "N^" + log2(ops) + "): " + maxprefix);
        return maxprefix;
    }
    
    private static String[] buildSuffixArray(String s) {
        int N = s.length();
        String[] sa = new String[N];
        for (int i = 0; i < N; i++)
            sa[i] = s.substring(i);
        return sa;
    }
    
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
        String filename = "src/strings/data/tale.txt";
        //String filename = "src/strings/data/tinyTale.txt";
        //String filename = "src/strings/data/baylor.txt";
        In in = new In(filename);
        String string = in.readAll();
        System.out.println(string);
        System.out.println("Length: " + string.length() + "\n-----------------");
        
        //findLongestPrefixBrute(string);
        
        System.out.println();
        findLongestPrefix(string);
    }

}
