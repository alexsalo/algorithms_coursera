package digraphs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebCrawler {
    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;

    // used to read the entire input. source:
    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    private static final Pattern EVERYTHING_PATTERN
        = Pattern.compile("\\A");
    
    Queue<String> queue;
    Set<String> discovered; 
    Scanner scanner;
    String root;
    
    public void crawl(String root) {
        queue = new LinkedList<String>();
        discovered = new HashSet<String>();  
        
        queue.add(root);
        discovered.add(root);
        
        while (!queue.isEmpty()) {
            String v = queue.poll();
            System.out.println(v);
            
            try {
            URL url = new URL(v);
            URLConnection site;
            site = url.openConnection();            

            // in order to set User-Agent, replace above line with these two
            // HttpURLConnection site = (HttpURLConnection) url.openConnection();
            // site.addRequestProperty("User-Agent", "Mozilla/4.76");

            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);        
               
            } catch (IOException e) {
            }
                
            String input = "";
                if (scanner.hasNextLine())
                    input = scanner.useDelimiter(EVERYTHING_PATTERN).next();
            
            String regexp = "http://(\\w+\\.)*(\\w+)";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(input);
            
            while (matcher.find()) {
                String w = matcher.group();
                if (!discovered.contains(w)) {
                    discovered.add(w);
                    queue.add(w);
                }
            }
        }
    }

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        //crawler.crawl("http://www.baylor.edu");
        //crawler.crawl("http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/");
        crawler.crawl(args[0]);
    }

}
