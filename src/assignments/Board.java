import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private int zeroI;
    private int zeroJ;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    this.zeroI = i;
                    this.zeroJ = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String firstColTemplate = "%1$2s";
        String regularTemplate = "%1$3s";
        builder.append(String.format("%s\n", dimension()));
        for (int[] row : tiles) {
            for (int j = 0; j < tiles.length; j++) {
                String template;
                if (j == 0) {
                    template = firstColTemplate;
                } else {
                    template = regularTemplate;
                }
                builder.append(String.format(template, row[j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        int n = tiles.length;
        int limit = n * n;
        for (int tile = 1; tile < limit; tile++) {
            int i = (tile - 1) / n;
            int j = (tile - 1) % n;
            if (tiles[i][j] != tile) {
                result++;
            }
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int expectedTile = i * n + j + 1;
                int actualTile = tiles[i][j];
                if (actualTile == 0) {
                    continue;
                }

                if (expectedTile != actualTile) {
                    int ai = (actualTile - 1) / n;
                    int aj = (actualTile - 1) % n;
                    int d = Math.abs(ai - i) + Math.abs(aj - j);
                    result += d;
                    // System.out.println(String.format(
                    //         "[%s;%s] Expected: %s; Actual: %s (%s:%s) d=%s",
                    //         i, j,
                    //         expectedTile,
                    //         actualTile,
                    //         ai, aj,
                    //         d
                    // ));
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> boards = new ArrayList<>();
        int n = tiles.length;
        for (int i = zeroI - 1; i <= zeroI + 1; i++) {
            if (i < 0 || i >= n) {
                continue;
            }
            for (int j = zeroJ - 1; j <= zeroJ + 1; j++) {
                if (j < 0 || j >= n || (i != zeroI && j != zeroJ) || (i == zeroI && j == zeroJ)) {
                    continue;
                }
                boards.add(swapTiles(zeroI, zeroJ, i, j));
            }
        }
        return boards;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int fromI, fromJ, toI, toJ;
        do {
            fromI = StdRandom.uniform(dimension());
            fromJ = StdRandom.uniform(dimension());
            toI = StdRandom.uniform(dimension());
            toJ = StdRandom.uniform(dimension());
        } while (!(fromI != toI || fromJ != toJ));
        return swapTiles(fromI, fromJ, toI, toJ);
    }

    private Board swapTiles(int fromI, int fromJ, int toI, int toJ) {
        int[][] newBoard = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, newBoard[i], 0, tiles.length);
        }
        int tile = tiles[toI][toJ];
        newBoard[toI][toJ] = 0;
        newBoard[fromI][fromJ] = tile;
        return new Board(newBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Tests are in separate file
    }
}
