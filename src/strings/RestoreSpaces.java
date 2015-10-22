package strings;

import edu.princeton.cs.algs4.In;

// appends unknown words to the next known word
public class RestoreSpaces {    
    private static int shift = Dict.shift;
    private static int N;
    
    // If all words are in dict
    public static String restoreSpaces(String s, Dict dict) {
        int pos = 0;
        N = s.length();
        StringBuilder sb = new StringBuilder(); // since we want to append alot
        while (pos < N) {
            int start = pos;
            pos = findNextWord(s, pos, dict);
            sb.append(s.substring(start, pos));
            sb.append(" ");
        }
        return sb.toString();
    }
    
    // returns the end of the next word
    private static int findNextWord(String s, int pos, Dict dict) {              
        // 1. Go down the tree while possible
        Dict.Node x = dict.root;

        int c = s.charAt(pos) - shift;;
        while (pos < N && x.next[c] != null) {
            x = x.next[c];
            pos++;
            if (pos < N)
                c = s.charAt(pos) - shift;
        } 
        
        // 2. Check if we have a search hit
        if (x.isWord)
            return pos;
        
        // 3. If not, backtrack for the nearest hit
        while (!x.isWord && x.parent != null) {
            x = x.parent;
            pos--;
        }
        
        // 4. Check maybe we are back in the root than we just had a new word
        if (x.parent == null) // find when next word starts
            pos = findNextWord(s, pos + 1, dict);
        
        // 5. Return hit string
        return pos;        
    }

    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        Dict dictionary = new Dict();
        while (!in.isEmpty()) {
            String s = in.readString();
            dictionary.put(s, true);
        }
        
        String s = "shellsbythesea";
        System.out.println(RestoreSpaces.restoreSpaces(s, dictionary));

        String s2 = "seashe";
        System.out.println(RestoreSpaces.restoreSpaces(s2, dictionary));
        
        String s3 = "seashellsseashe";
        System.out.println(RestoreSpaces.restoreSpaces(s3, dictionary));
        
        String s4 = "vasekseashellsseashe";
        System.out.println(RestoreSpaces.restoreSpaces(s4, dictionary));
    }

}
