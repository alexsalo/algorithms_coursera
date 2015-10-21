package strings;

public class RestoreSpaces {
    private RWayTrie dictionary;
    
    // If all words are in dict
    public String restoreSpaces(String s) {
        int pos = 0;
        int N = s.length();
        StringBuilder sb = new StringBuilder(); // since we want to append alot
        while (pos < N) {
            int start = pos;
            pos = findNextWord(s, pos);
            sb.append(s.substring(start, pos));
        }
        return sb.toString();
    }
    
    // returns the end of the next word
    private int findNextWord(String s, int pos) {
        RWayTrie dict = dictionary;
        
        // 1. Go down the tree while possible
        while (dict.contains(s.charAt(pos))) {
            dict = dict.getChild(s.charAt(pos));
            pos++;
        }
        
        // 2. Check if we have a search hit
        if (dict.value() == true)
            pos;
        
        // 3. If not, backtrack for the nearest hit
        while (dict.value() == false) {
            dict = dict.parent();
            pos--;
        }
        
        // 4. Check maybe we are back in the root than we just had a new word
        
        // 5. Return hit string
        return pos;        
    }

    public static void main(String[] args) {

    }

}
