package code_prep.strings;

import java.util.Set;
import java.util.TreeSet;

public class CharsInTwoStrings {

    /*
     * Brute realization
     * ~N^2
     */
    public static Set<Character> charsInBothStringsBrute(String a, String b) {
        Set<Character> result = new TreeSet<Character>();
        for (char ca : a.toCharArray())
            for (char cb : b.toCharArray())
                if (ca == cb)
                    result.add(ca);
        return result;
    }
    
    /*
     * take advantage of the small bucket size
     * ~2N + ~R space
     */
    public static Set<Character> charsInBothStringsLinear(String a, String b) {
        Set<Character> result = new TreeSet<Character>();
        int R = 256; // extended ASCI
        boolean[] seen = new boolean[R];
        for (char c : a.toCharArray())
            seen[c] = true;
        for (char c : b.toCharArray())
            if (seen[c]) {
                result.add(c);
                seen[c] = false;
            }
        return result;        
    }
    
    public static void main(String[] args) {
        String a = "anastasiia shchuchkina";
        String b = "aleksandr salo";
        System.out.println(charsInBothStringsBrute(a, b));
        System.out.println(charsInBothStringsLinear(a, b));
    }

}
