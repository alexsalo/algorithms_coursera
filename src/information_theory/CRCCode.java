package information_theory;

public class CRCCode {
    // To print 32-bit integers in consistent format
    private static void pf(int num, String prefix) {
        System.out.println(prefix + String.format("%32s", 
                Integer.toBinaryString(num)).replace(' ', '0'));
    }
    
    public static int computeCRC(int input, int[] codePowers, boolean verbose) {
        int n = codePowers.length;
        
        // 1. get the binary representation of the poly's powers
        int poly = 1;
        for (int i = 0; i < n; i++)
            poly += 2 << codePowers[i] - 1;
        int tmpCode = poly;

        // 2. align poly with the MSB
        int leadingZeros = Integer.numberOfLeadingZeros(input);
        poly = poly << (31 - codePowers[0] - leadingZeros);     
        
        int moveLater = 0;
        if (verbose) pf(input, "msg : ");
        
        // 3. Divide until input is less than a divider
        while (input > 2 << codePowers[0] || input < 0) {
            // 3.1 XOR
            input = input ^ poly;
            
            // 3.2 print
            if (verbose) {
                pf(poly, "poly: ");      
                System.out.println("--------------------------------");  
                pf(input, "msg : ");
            }
            
            // 3.3 align poly with MSB
            int newLeadZ = Integer.numberOfLeadingZeros(input);
            moveLater = newLeadZ - leadingZeros;
            if (input > 2 << codePowers[0]) {                
                poly = poly >> moveLater;    
                if (poly < 0)
                    poly = tmpCode << (31 - codePowers[0] - moveLater);
                leadingZeros = newLeadZ;
            } 
        }
        
        // 4. Can't divide anymore, finish by finding remainder
        
        // 4.1 Move poly and input by the length of the poly
        input = input << codePowers[0]; // make some space for a quoitet
        poly = (poly << codePowers[0]) >> moveLater;
        leadingZeros = Integer.numberOfLeadingZeros(input);
        if (verbose) {
            System.out.println("\nAppending Space for finding remainder...\n");
            pf(input, "msg : ");
        }
        
        // 4.2 Divide the same way
        while (input > 1 << codePowers[0]) {
            input = input ^ poly;
            if (verbose) {
                pf(poly, "poly: ");
                System.out.println("--------------------------------");              
                pf(input, "msg : ");
            }
            int newLeadZ = Integer.numberOfLeadingZeros(input);
            poly = poly >> (newLeadZ - leadingZeros);
            leadingZeros = newLeadZ;
        }
        
        // 5. Collect the remainder - CRC code
        return input;
    }
    
    // Client
    public static void main(String[] args) {  
        int[] Poly = new int[]{8, 6, 5, 1};
        int[][] hexNums = new int[][]{
            {0x376034ae, 0},
            {0x1a659bdf, 0},
            {0xa284c439, 0},
            {0xb084006a, 0},
            {0xa1daee92, 0},
        };
        
        for (int i = 0; i < hexNums.length; i++) {
            hexNums[i][1] = computeCRC(hexNums[i][0], Poly, false);
            System.out.println(Integer.toHexString(hexNums[i][0]) 
                    + ", CRC code: " + Integer.toHexString(hexNums[i][1]));
        }
        
        //System.out.println(computeCRC(0x376034ae, new int[]{8, 6, 5, 1}, true));
        //System.out.println(Integer.toHexString(computeCRC(0xa284c439, new int[]{8, 6, 5, 1}, true)));
        
        System.out.println(Integer.toHexString(computeCRC(0x1a659bdf, new int[]{8, 6, 5, 1}, true)));
        //System.out.println(computeCRC(13548, new int[]{3, 1}));
        //System.out.println(computeCRC(682, new int[]{4}));
    }

}
