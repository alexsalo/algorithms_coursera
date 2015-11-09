package dynamic_programming;

import java.util.LinkedList;
import java.util.Queue;

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
    
    public static Queue<String> genParethesis(String prefix, int N, Queue<String> q, int cnt) {
        if (prefix.length() >= 2 * N) {
            if (cnt == 0)
                q.add(prefix);
            return q;
        } 
        
        if (cnt > 0)
            q = genParethesis(prefix + ')', N, q, cnt - 1);
        q = genParethesis(prefix + '(', N, q, cnt + 1);
        
        return q;
    }
    

    public static void main(String[] args) {
        System.out.println(genParethesis("", 4, new LinkedList<String>(), 0));
    }

}
