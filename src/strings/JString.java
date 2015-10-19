package strings;

/*
 *  Def:
 *      - String - sequence of chars (genomic, program, email)
 *      - Alphabet - set of all possible chars *  
 *          - DNA - 4
 *          - lowercase - 26
 *          - asci - 128
 *          - Ext_asci - 256
 *          - unicode - 65536
 *      - C char: 8 bit (1 byte) ASCI code - 256 chars
 *      - Unicode: 16 bit (2 byte) code U+0041 (A) = 0000 0000 0100 0001 
 *      
 *  Impl:
 *      String - immutable char[], len, offset - use for substring
 *          ~1 substr, len, charAt
 *          ~N concat
 *          
 *          - gives ~N^2 time for reverse() loop (based on concat)
 *          - gives ~N for array of suffixes (based on substring)
 *          
 *          
 *      StringBuilder  - resizing array - use for concat
 *          ~1 concat (amortized cost), charAt, len
 *          ~ susbtr
 *          
 *          - gives ~N time for reverse() loop (based on concat)
 *          - gives ~N^2 for array of suffixes (based on substring)
 *          
 *      StringBuffer - same as SB but thread safe and slower        
 *      
 *      
 *  Time:
 *      - reverse ~N
 *      - prefix array ~N
 *      - longest prefix ~ compareTo ~|prefix| ~sublinear -O(N) 
 *      
 *  Algs:
 *      - Radix - take advantage of the short alphabet size
 */

/*
 *  Uses 40 (class ref) + 2N (array len) bytes for a virgin string of length N!
 *  If critical to space - just use byte[]
 */
public class JString implements Comparable<JString> {
    private char[] value;
    private int len;
    private int offset;
    //private int hash; // cache of hashCode()
    
    public JString() {
        
    }
    
    // ~constant time! Since only copy ref
    public JString substring(int from, int to) {
        //                                           copy of the reference
        return new JString(offset + from, to - from, value);
    }
    
    private JString(int offset, int len, char[] val) {
        this.offset = offset;
        this.len = len;
        this.value = val;
    }
    
    // ~1
    public char chatAt(int i) {
        return value[i + offset];
    }
    
    // ~1
    public int len() {
        return len;
    }
    
    // N
    public JString concat(JString that) {
        char[] newval = new char[len + that.len];
        
        for (int i = 0; i < len; i++)
            newval[i] = value[offset + i];
        
        for (int i = 0; i < that.len; i++)
            newval[i] = that.value[that.offset + i];
        
        return new JString(0, newval.length, newval);
    }
    
    public static void main(String[] args) {
        //JString s = new JString();
        //System.out.println('U+0041');
    }

    @Override
    public int compareTo(JString o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
