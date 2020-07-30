import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final List<Board> boardStates;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.boardStates = getSolution(initial);
    }

    private List<Board> getSolution(Board initial) {
        // Preparation
        List<Board> solutionBoards = new ArrayList<>();
        solutionBoards.add(initial);
        MinPQ<SearchNode> queue = new MinPQ<>();
        queue.insert(new SearchNode(initial));

        // Solution
        SearchNode current = queue.delMin();
        while (!current.board.isGoal()) {
            boolean isSolvable = true;
            for (SearchNode neighbor : current.neighbors()) {
                if (neighbor.board.twin().isGoal()) {
                    isSolvable = false;
                    break;
                }
                queue.insert(neighbor);
            }
            if (!isSolvable) {
                break;
            }
            current = queue.delMin();
            solutionBoards.add(current.board);
        }
        return solutionBoards;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return boardStates.get(boardStates.size() - 1).isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return boardStates.size() - 1;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return boardStates;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int manhattan;

        public SearchNode(Board board) {
            this(board, 0, null);
        }

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.manhattan = board.manhattan();
            this.moves = moves;
            this.previous = previous;
        }

        public List<SearchNode> neighbors() {
            List<SearchNode> nodes = new ArrayList<>();
            for (Board neighborBoard : board.neighbors()) {
                if (previous != null && previous.board.equals(neighborBoard)) {
                    continue;
                }
                nodes.add(new SearchNode(neighborBoard, this.moves + 1, this));
            }
            return nodes;
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(
                    this.manhattan + this.moves,
                    other.manhattan + other.moves
            );
        }

        @Override
        public String toString() {
            return "SearchNode{" +
                    "board=" + board +
                    '}';
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
