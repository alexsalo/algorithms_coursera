package numbers;

public class TaxiCab {

    public static void findTaxicabNumbers(int limit) {
        for (int i = 1; i * i * i < limit; i++)
            for (int j = i; j * j * j < limit; j++) {
                int sum = i * i * i + j * j * j;
                for (int k = 1; k * k * k < limit; k++) {
                    int rest = sum - k;
                    for (int m = k; m *m * m < limit; m++)
                        if (m *m * m == rest)
                            System.out.println(i+"^3 + " + j + "^3 = " + k + "^3 + " + m+"^3");
                }
            }
        
        
    }
    
    public static void main(String[] args) {
        int N = 1730;
        findTaxicabNumbers(N);

    }

}
