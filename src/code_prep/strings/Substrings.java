package code_prep.strings;

import java.util.Map;
import java.util.TreeMap;

public class Substrings {
    private static class Query implements Comparable<Query> {
        private int s;
        private int e;

        public Query(int s, int e) {
            this.s = s;
            this.e = e;
        }

        public String toString() {
            return s + "-" + e;
        }

        public int compareTo(Query that) {
            if (this.s < that.s)
                return -1;
            else if (this.s > that.s)
                return 1;
            else {
                if (this.e < that.e)
                    return -1;
                else if (this.e > that.e)
                    return 1;
                else
                    return 0;
            }
        }
    }

    //~N^2
    public static Map<Query, Integer> precomputeAllQueries(String s) {
        Map<Query, Integer> map = new TreeMap<Query, Integer>();
        for (int start = 0; start < s.length(); start++) {
            int cnt = 0;
            int nzeros = 0;
            for (int end = start + 1; end < s.length(); end++) {
                char c = s.charAt(end);
                if (c == '0')
                    nzeros++;
                else if (c == '1') {
                    cnt += nzeros;
                    map.put(new Query(start, end), cnt);
                }
            }
        }
        return map;
    }

    // ~N
    public static int subsFaster(String s, Query q) {
        // 1. get working string
        String ss = s.substring(q.s, q.e + 1);
        int cnt = 0;
        int nzeros = 0;
        for (int i = 0; i < ss.length(); i++) {
            if (ss.charAt(i) == '0')
                nzeros++;
            if (ss.charAt(i) == '1') {
                cnt += nzeros;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        String s = "1010001010010";
        System.out.println("0-6: " + subsFaster(s, new Query(0, 6)));
        
        Map<Query, Integer> map = precomputeAllQueries(s);
        System.out.println(map);
        System.out.println(map.get(new Query(0, 6)));
    }

}
