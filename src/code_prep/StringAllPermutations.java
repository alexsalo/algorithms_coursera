package code_prep;

public class StringAllPermutations {
    public static int cnt;
    
    public static void printAllPermutations(String s) {
        cnt = 0;
        permute("", s);
        System.out.println("Total should be N!: " + fact(s.length())); 
    }
    
    private static void permute(String prefix, String s) {
        int n = s.length();
        if (n == 0) 
            System.out.println(prefix + " " + cnt++);
        else{
            for (int i = 0; i < n; i++) {
                char curchar = s.charAt(i);
                String beforeCurchar = s.substring(0, i);
                String afterCurchar  = s.substring(i + 1, n);
                permute(prefix + curchar, beforeCurchar + afterCurchar);
            }
        }
    }
    
    private static int fact(int x) {
        return x == 0 ? 1 : x * fact(x - 1);
    }
    
    
    public static void main(String[] args) {
        printAllPermutations("alex");
    }

}
