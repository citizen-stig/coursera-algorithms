import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private SearchNode lastNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board can't be null");
        }
        this.lastNode = getSolution(initial);
    }

    private SearchNode getSolution(Board initial) {
        // Preparation
        MinPQ<SearchNode> queue = new MinPQ<>();
        SearchNode node = new SearchNode(initial);

        queue.insert(node);

        // Solution
        while (!node.board.isGoal()) {
            boolean isSolvable = true;
            node = queue.delMin();

            for (SearchNode neighbor : node.neighbors()) {
                if (neighbor.board.twin().isGoal()) {
                    isSolvable = false;
                    break;
                }
                queue.insert(neighbor);
            }
            if (!isSolvable) {
                break;
            }
        }

        return node;

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return lastNode.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return lastNode.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return lastNode.path();
        }
        return null;
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

        public List<Board> path() {
            List<Board> boards = new ArrayList<>();
            SearchNode node = this;
            while (node != null) {
                boards.add(node.board);
                node = node.previous;
            }
            Collections.reverse(boards);
            return boards;
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
