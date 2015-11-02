package information_theory;

public class BoothMultiplication {
    private static void pf(int num) {
        System.out.println(String.format("%14s", 
                Integer.toBinaryString(num)).replace(' ', '0'));
    }

    public static void main(String[] args) {
        int mcand = 240;
        pf(mcand);
        
        int mlier = 15;
        pf(mlier);
        
        int prod = mcand * mlier;
        pf(prod);
        System.out.println(prod);
        
        

    }

}
