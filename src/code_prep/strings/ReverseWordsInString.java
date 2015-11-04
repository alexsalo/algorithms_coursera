package code_prep.strings;

/*
 * Write   a function  to  reverse  the  order  of  words 
 *  in  a  string  in  place.
 */
public class ReverseWordsInString {
    
    public static String reverseWords(StringBuilder sb) {
        int n = sb.length();
        // 1. reverse all chars
        for (int i = 0; i < n / 2; i++) {
            char tmp = sb.charAt(n - 1 - i);
            sb.setCharAt(n - 1 - i, sb.charAt(i));
            sb.setCharAt(i, tmp);
        }
        
        // 2. reverse chars in words
        for (int i = n - 1; i >= 0; i--) {
            int offset = 0;
            while (i >= 0 && sb.charAt(i) != ' ') {
                offset++;
                i--;
            }
            
            for (int j = 0; j < offset / 2; j++)
            {
                char tmp = sb.charAt(i + offset - j);
                sb.setCharAt(i + offset - j, sb.charAt(i + 1 + j));
                sb.setCharAt(i + 1 + j, tmp);
            }
        }
        return sb.toString();
    }
    
    
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("alex salo played soccer");
        System.out.println(reverseWords(sb));
    }

}
