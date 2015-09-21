import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private boolean[][] grid; // false - closed, tru - opened
    private int N;

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop; // to avoid backwash
    private int enter;
    private int exit;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) { // ~N^2
        if (N <= 0)
            throw new IllegalArgumentException();
        this.N = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = false;

        enter = N * N;
        exit = enter + 1;

        uf = new WeightedQuickUnionUF(N * N + 2);
        ufTop = new WeightedQuickUnionUF(N * N + 1);
    }

    private int getObjId(int i, int j) {
        return (i - 1) * N + (j - 1);
    }

    private void fillNeighbor(int p, int i, int j) {
        if (!(i < 1 || j < 1 || i > N || j > N)) { // private use may try over
                                                   // border
            if (isOpen(i, j)) {
                uf.union(p, getObjId(i, j));
                ufTop.union(p, getObjId(i, j));
            }
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) { // ~1
        checkBorders(i, j);
        grid[i - 1][j - 1] = true; // opened

        // treat top row
        if (i == 1) {
            uf.union(getObjId(i, j), enter);
            ufTop.union(getObjId(i, j), enter);
        }

        // Union with open neighbors
        int p = getObjId(i, j);
        fillNeighbor(p, i - 1, j);
        fillNeighbor(p, i + 1, j);
        fillNeighbor(p, i, j - 1);
        fillNeighbor(p, i, j + 1);

        // Treat bottow row
        if (i == N)
            uf.union(getObjId(i, j), exit);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) { // ~1
        checkBorders(i, j);
        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) { // ~1
        checkBorders(i, j);
        return ufTop.connected(getObjId(i, j), enter);
    }

    private void checkBorders(int i, int j) { // ~1
        if (i < 1 || j < 1 || i > N || j > N)
            throw new IndexOutOfBoundsException();
    }

    // does the system percolate?
    public boolean percolates() { // ~1
        return uf.connected(enter, exit);
    }

    private void drawGrid() {
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        int x = N / 2;
        double r = 1.002 * N / 2;
        StdDraw.filledSquare(x, x, r);
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= N; j++) {
                if (grid[i - 1][j - 1]) {
                    if (ufTop.connected(enter, getObjId(i, j)))
                        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    else
                        StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledSquare(j - 1, N - i, .49);
                }
            }
    }

    public static void main(String[] args) {
        int n = 25;
        Percolation percolation = new Percolation(n);
        int i, j;
        int count = 0;
        while (!percolation.percolates()) { // ~N*lgN
            do {
                i = 1 + StdRandom.uniform(n);
                j = 1 + StdRandom.uniform(n);
            } while (percolation.isOpen(i, j));
            percolation.open(i, j);
            count++;
        }

        System.out.println("Steps to percolate: " + String.valueOf(count));
        System.out.println("Fill ratio to percolate: "
                + String.valueOf(1.0 * count / (n * n)));
        percolation.drawGrid();
    }
}
