import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final MinPQ<SearchNode> queue;
    private final List<Board> boardStates;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.queue = new MinPQ<>();
        this.queue.insert(new SearchNode(initial));
        this.boardStates = new ArrayList<>();
        solve();
    }

    private void solve() {
        SearchNode current = queue.delMin();
        while (!current.board.isGoal()) {
            for (SearchNode neighbor : current.neighbors()) {
                queue.insert(neighbor);
            }
            current = queue.delMin();
            boardStates.add(current.board);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return boardStates.size();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return boardStates;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        Board board;
        private int moves;
        private final SearchNode previous;

        public SearchNode(Board board) {
            this.board = board;
            this.moves = 0;
            this.previous = null;
        }

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        public List<SearchNode> neighbors() {
            List<SearchNode> nodes = new ArrayList<>();
            for (Board b : board.neighbors()) {
                nodes.add(new SearchNode(b, 1, this));
            }
            return nodes;
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.board.manhattan(), o.board.manhattan());
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
