package strings;

import edu.princeton.cs.algs4.In;

/*
 *  How to beat BST and HashTable for String keys?
 *  
 *  Tries -- reTRIEval
 *  ------------------
 *  
 *  Store CHAR at the nodes of the tree
 *      - Each node has R kids, one for each possible char
 *      - Mark char if represents the end of the string key
 *  
 *  Search miss:
 *      - if reached a null link - no such word
 *      - if stopped down the tree - but no indicator of the word
 *      
 *  Insertion:
 *      - see null - create new node with char
 *      - see last char of the key - mark the node
 *  
 */
public class RWayTrie {

    
    public static void main(String[] args) {
        String filename = "src/strings/data/shells.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        
        for (String s : strings)
            System.out.println(s);
    }

}
