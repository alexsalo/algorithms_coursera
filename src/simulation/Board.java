package simulation;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int N;
    private int[][] board;
    private int zi;
    private int zj;

    // construct a board from an N-by-N array of blocks (where blocks[i][j] =
    // block in row i, column j)
    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.board = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                this.board[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zi = i;
                    zj = j;
                }
            }
    }

    public int dimension() // board dimension N
    {
        return N;
    }

    public int hamming() // number of blocks out of place
    {
        return wrong();
    }

    public int manhattan() // sum of Manhattan distances between blocks and goal
    {
        int dist = 0;
        int n = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j] == 0)
                    n++;
                else {
                    int val = board[i][j] - 1;
                    int idist = Math.abs(n / N - val / N);
                    int jdist = Math.abs(n % N - val % N);
                    dist += idist + jdist;
                    n++;
                }
        return dist;
    }

    public boolean isGoal() // is this board the goal board?
    {
        return wrong() == 0;
    }

    public Board twin() // a board that is obtained by exchanging any pair of
                        // blocks
    {
        int[][] newboard = cloneBoard();
        int newi = 0;
        if (zi == 0)
            newi = 1;
        exch(newboard, newi, 0, newi, 1);
        return new Board(newboard);
    }

    public boolean equals(Object y) // does this board equal y?
    {
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        int[][] b = ((Board) y).board;
        if (b.length != N)
            return false;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (b[i][j] != board[i][j])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors() // all neighboring boards
    {
        Stack<Board> stack = new Stack<Board>();
        for (int i = -1; i < 2; i += 2) {
            int[][] newblocks = cloneBoard();
            int newi = zi + i;
            if (newi >= 0 && newi < N) {
                exch(newblocks, zi, zj, newi, zj);
                stack.push(new Board(newblocks));
            }

            newblocks = cloneBoard();
            int newj = zj + i;
            if (newj >= 0 && newj < N) {
                exch(newblocks, zi, zj, zi, newj);
                stack.push(new Board(newblocks));
            }
        }
        return stack;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.valueOf(N) + '\n');
        for (int[] line : board) {
            for (int b : line) {
                s.append(" ");
                s.append(String.valueOf(b));
            }
            s.append('\n');
        }
        return s.toString();
    }

    private int wrong() {
        int wrong = 0;
        int n = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j] == 0)
                    n++;
                else if (board[i][j] != n++)
                    wrong++;
        return wrong;
    }

    private void exch(int[][] b, int i, int j, int ii, int jj) {
        int tmp = b[i][j];
        b[i][j] = b[ii][jj];
        b[ii][jj] = tmp;
    }

    private int[][] cloneBoard() {
        int[][] b = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                b[i][j] = board[i][j];
        return b;
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        Board board = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 },
                { 7, 6, 5 } });
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
    }
}
