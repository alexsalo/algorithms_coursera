package strings;

/*
 *  Def:
 *      - String - sequence of chars (genomic, program, email)
 *      - Alphabet - set of all possible chars *  
 *      - C char: 8 bit (1 byte) ASCI code - 256 chars
 *      - Unicode: 16 bit (2 byte) code U+0041 (A) = 0000 0000 0100 0001 
 *      
 *  Impl:
 *      String - immutable char[], len, offset 
 *          ~1 substr, len, charAt
 *          ~N concat
 *          
 *      StringBuilder  - resizing array
 *          ~1 concat (amortized cost), charAt, len
 *          ~ susbtr
 *          
 *      StringBuffer - same as SB but thread safe and slower          
 *      
 */

/*
 *  Uses 40 (class ref) + 2N (array len) bytes for a virgin string of length N!
 *  If critical to space - just use byte[]
 */
public class JString implements Comparable<JString> {
    private char[] value;
    private int len;
    private int offset;
    private int hash; // cache of hashCode()
    
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
        JString s = new JString();
        //System.out.println('U+0041');
    }

    @Override
    public int compareTo(JString o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
