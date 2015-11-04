package dynamic_programming;

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
    
    /*public static String printParens(int N) {

        int nl = 0;
        while (nl < N);
    }*/

    public static void main(String[] args) {
        //System.out.println(printParens(3));
    }

}
