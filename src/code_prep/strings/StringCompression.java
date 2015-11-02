package code_prep.strings;

public class StringCompression {
    
    public static String compressString(String s) {
        int N = s.length();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < N) {
            int cnt = 1;
            char c = s.charAt(i++);
            while (i < N && c == s.charAt(i)) {
                cnt++;
                i++;
            }
            sb.append(c);
            if (cnt > 1)
                sb.append(cnt);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "aaabbcdddk";
        System.out.println(s);
        System.out.println(compressString(s));
    }

}
