package strings;

import edu.princeton.cs.algs4.StdIn;

public class GREP {

    public static void main(String[] args) {
        String regexp = "(.*" + args[0] + ".*)";
        NFA nfa = new NFA(regexp);
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            if (nfa.recognizes(line))
                System.out.println(line);
        }

    }

}
