package code_prep.strings;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Set;
import java.util.TreeSet;

public class Gen5LetterNames {

    public static void main(String[] args) {
        char start = 'a', end = 'z';
        Set<Character> vowels = new TreeSet<Character>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("company_names_5_letters.txt", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        for (char c1 = start; c1 <= end; c1++)
            if (!vowels.contains(c1))
        for (char c2 = start; c2 <= end; c2++)
            if (vowels.contains(c2))
        for (char c3 = start; c3 <= end; c3++)
            if (!vowels.contains(c3))
        for (char c4 = start; c4 <= end; c4++)
            if (vowels.contains(c4))
        for (char c5 = start; c5 <= end; c5++)
            if (!vowels.contains(c5))
                writer.println("" + c1 + c2 + c3 + c4 + c5);
        
        writer.close();
    }

}
