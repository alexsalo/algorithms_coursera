package code_prep;

public class TicTacToe {
    public static short[] winCombinations = new short[]{
      7, 7 << 3, 7 << 6, // horizontal
      73, 73 << 1, 73 << 2, // vertical
      273, // diagonal
      84   // anti-diagonal
    };
    
    public static void printX(short X) {
        System.out.println(X);
        String s = String.format("%9s", 
                Integer.toBinaryString(X)).replace(' ', '0');
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = s.charAt(i * 3 + j) == '1' ? 'X' : ' ';
                out.append(c);
                if (j < 2) out.append('|');
            }
            if (i < 2) out.append('\n');
        }
        System.out.println(out.toString());
    }
    
    // O(8)
    private static boolean isWinner(short X) {
        for (int i = 0; i < 8; i++)
            if ((X & winCombinations[i]) == winCombinations[i])
                return true;
        return false;
    }
    public static void main(String[] args) {
        /*for (short s : winCombinations)
            System.out.println(String.format("%9s", 
                    Integer.toBinaryString(s)).replace(' ', '0'));*/
        for (short X = 0; X < 511; X++) {
            printX(X);
            System.out.println(isWinner(X));
        }
    }

}
