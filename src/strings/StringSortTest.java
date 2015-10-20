package strings;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

public class StringSortTest {

    public static void main(String[] args) {
        String filename = "src/strings/data/words3.txt";
        In in = new In(filename);
        String[] strings = in.readAllStrings();
        
        System.out.println("Fixed length... N = " + strings.length);
        System.out.println(Arrays.toString(strings));
        LSDRadix.sort(strings, 3);
        
        in = new In(filename);
        strings = in.readAllStrings();
        MSDRadix.sort(strings);

        in = new In(filename);
        strings = in.readAllStrings();
        Quick3waySort.sort(strings);
        
        System.out.println("\nRandom length... N = " + strings.length);
        filename = "src/strings/data/shells.txt";
        in = new In(filename);
        strings = in.readAllStrings();
        System.out.println(Arrays.toString(strings));
        MSDRadix.sort(strings);

        in = new In(filename);
        strings = in.readAllStrings();
        Quick3waySort.sort(strings);
        
        filename = "src/strings/data/women_words.txt";
        in = new In(filename);
        strings = in.readAllStrings();
        System.out.println("\nRandom length on real book (Women)... N = " + strings.length);
        System.out.println(Arrays.toString(strings));
        MSDRadix.sort(strings, 1);
        
        in = new In(filename);
        strings = in.readAllStrings();
        MSDRadix.sort(strings);

        in = new In(filename);
        strings = in.readAllStrings();
        Quick3waySort.sort(strings);
    }

}
