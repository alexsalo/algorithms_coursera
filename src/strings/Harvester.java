package strings;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import edu.princeton.cs.algs4.In;

/**
 * alex@apc:~/github/algorithms_coursera/bin$ java -cp
 * ~/github/algorithms_coursera/src/algs4.jar:. strings.Harvester
 * "\\w*@baylor.edu" http://www.ecs.baylor.edu/index.php?id=862982 | uniq* 
 * 
 */
public class Harvester {
    public static void main(String[] args) {
        String regexp = args[0];
        In in = new In(args[1]);
        String input = in.readAll();
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
            System.out.println(matcher.group());
    }
}
