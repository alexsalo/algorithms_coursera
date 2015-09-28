import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twinpq;
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        pq = new MinPQ<Solver.SearchNode>();
        pq.insert(new SearchNode(0, initial, null));

        twinpq = new MinPQ<Solver.SearchNode>();
        twinpq.insert(new SearchNode(0, initial.twin(), null));

        solve();
    }

    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private Board board;
        private SearchNode prev;

        public SearchNode(int moves, Board board, SearchNode prev) {
            this.moves = moves;
            this.board = board;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            int p1 = moves + board.manhattan();
            int p2 = o.moves + o.board.manhattan();
            if (p1 < p2)
                return -1;
            else
                return 1;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        else
            return pq.min().moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> stack = new Stack<Board>();
        while (solution.prev != null) {
            stack.push(solution.board);
            solution = solution.prev;
        }
        stack.push(solution.board);
        return stack;
    }

    private void solve() {
        while (!pq.min().board.isGoal() && !twinpq.min().board.isGoal()) {
            // pq
            SearchNode current = pq.delMin();
            for (Board board : current.board.neighbors()) {
                if (current.prev == null) {
                    pq.insert(new SearchNode(current.moves + 1, board, current));
                } else if (!board.equals(current.prev.board))
                    pq.insert(new SearchNode(current.moves + 1, board, current));
            }

            // twinpq
            current = twinpq.delMin();
            for (Board board : current.board.neighbors()) {
                if (current.prev == null) {
                    twinpq.insert(new SearchNode(current.moves + 1, board,
                            current));
                } else if (!board.equals(current.prev.board))
                    twinpq.insert(new SearchNode(current.moves + 1, board,
                            current));
            }
        }
        if (pq.min().board.isGoal())
            solution = pq.min();
        else
            solution = null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
