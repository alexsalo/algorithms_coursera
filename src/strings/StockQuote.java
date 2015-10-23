package strings;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StockQuote {
    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;

    // used to read the entire input. source:
    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

    private static String readFromUrl(String s) {
        String result = "";
        URL url;
        try {
            url = new URL(s);
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            Scanner scanner = new Scanner(new BufferedInputStream(is),
                    CHARSET_NAME);
            scanner.useLocale(LOCALE);
            result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String name = "https://www.google.com/finance?q=";
        String text = readFromUrl(name + args[0]);
        int start = text.indexOf("Range Price range (low - high) in the latest trading day.", 0);
        int from = text.indexOf("<td class=\"val\"", start);
        int to = text.indexOf("</td>", from);
        String price = text.substring(from + 16, to);
        System.out.println(price);
    }
}
