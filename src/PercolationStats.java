import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) { // ~N^2 * T
        Percolation percolation;
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();

        results = new double[T];
        for (int t = 0; t < T; t++) {
            percolation = new Percolation(N);
            int i, j;
            int count = 0;
            while (!percolation.percolates()) {
                do {
                    i = 1 + StdRandom.uniform(N);
                    j = 1 + StdRandom.uniform(N);
                } while (percolation.isOpen(i, j));
                percolation.open(i, j);
                count++;
            }
            double ratio = 1.0 * count / (N * N);
            results[t] = ratio;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    public static void main(String[] args) { // test client (described below)
        int N = 10;
        int T = 10;
        if (args.length == 2) {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo()
                + ", " + stats.confidenceHi());
    }
}
