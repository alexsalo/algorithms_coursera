package structures;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class FileIndex {

    public static void main(String[] args) {
        Hashtable<String, Set<File>> st = new Hashtable<String, Set<File>>();
        
        //String filename = "*.txt";
        for (String s : args){
            File file = new File(s);
            In in = new In(file);
            while (!in.isEmpty()){
                String key = in.readString();
                if (!st.containsKey(key))
                    st.put(key, new HashSet<File>());
                Set<File> set = st.get(key);
                set.add(file);
            }
        }
        
        while (!StdIn.isEmpty()){
            String query = StdIn.readString();
            System.out.println(st.get(query));
        }
    }

}
