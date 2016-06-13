package dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * Print all valid combinations of ( ) 
 * ex:
 * 1 ()
 * 
 * 2 ()() - concat two
 *   (()) - surround by
 * 
 * 3
 * ((())) - surround 2
 * ()()() - concat three 1
 * (())() - concat 2 and 1
 * ()(()) - concat 1 and 2
 * 
 * 4 
 * (((()))) - surround 3
 * ()()()() - concat 2 and 2
 * ()(())()
 * (())()()
 * 
 * left nl - can insert until <= N
 * right - can insert until <= nl
 */
public class ValidCombinations {
    public static int N = 13;
    public static int N2 = 2 * N;
    public static List<String> q = new ArrayList<String>((int)Math.pow(2, N));
    
    private static class Str {
        public int sz;
        public int a;
        
        public Str() {
            this.sz = 0;
            a = 0;
        }
        
        public Str append(boolean val) {
            Str s = new Str();
            int mask = 1;
            for (int i = 0; i < sz; i++) {
                if ((a & mask) > 0)
                    s.a |= mask;
                mask = mask << 1;
            }
            if (val)
                s.a |= (1 << (sz));
            s.sz = sz + 1;
            return s;
        }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N2; i++)
                if ((a & (1 << i)) > 0) sb.append('(');
                else      sb.append(')');
            return sb.toString();
        }
    }
    
    public static void genParethesis(Str prefix, int cnt) {
        if (prefix.sz == N2) 
            q.add(prefix.toString());
        else if (prefix.sz < N2){            
            if (cnt > 0) {
                genParethesis(prefix.append(false), cnt - 1);
            }
            if (cnt < N2 - prefix.sz)
            genParethesis(prefix.append(true), cnt + 1);
        }
    }
    

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        genParethesis(new Str(), 0);
        long end = System.currentTimeMillis();
        System.out.println("Spend: " + (end - start) * 1.0 / 1000);
    
        //System.out.println(q);
    }

}
