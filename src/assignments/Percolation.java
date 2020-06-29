import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final WeightedQuickUnionUF finder;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid cannot be less than zero");
        }
        finder = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        if (isOutside(row) || isOutside(col)) {
            throw new IllegalArgumentException("Index is outside of the grid");
        }
        final int i = row - 1;
        final int j = col - 1;
        if (grid[i][j]) {
            return;
        }
        grid[i][j] = true;
        int thisCell = flattenCoordinates(row, col);
        // upper
        if (row > 1 && isOpen(row - 1, col)) {
            int upperNeighbour = flattenCoordinates(row - 1, col);
            finder.union(thisCell, upperNeighbour);
        }
        // lower
        if (row < (grid.length - 1) && isOpen(row + 1, col)) {
            int lowerNeighbour = flattenCoordinates(row + 1, col);
            finder.union(thisCell, lowerNeighbour);
        }
        // left
        if (col > 1 && isOpen(row, col - 1)) {
            int leftNeighbour = flattenCoordinates(row, col - 1);
            finder.union(thisCell, leftNeighbour);
        }
        // right
        if (col < (grid.length - 1) && isOpen(row, col + 1)) {
            int rightNeighbour = flattenCoordinates(row, col + 1);
            finder.union(thisCell, rightNeighbour);
        }
        if (row == 1) {
            finder.union(0, thisCell);
        }
        if (row == grid.length) {
            finder.union(thisCell, grid.length + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutside(row) || isOutside(col)) {
            throw new IllegalArgumentException("Index is outside of the grid");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutside(row) || isOutside(col)) {
            throw new IllegalArgumentException("Index is outside of the grid");
        }
        int flatCoordinate = flattenCoordinates(row, col);
        return finder.find(0) == finder.find(flatCoordinate);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSites = 0;
        for (boolean[] row : grid) {
            for (int j = 0; j < grid.length; j++) {
                if (row[j]) {
                    openSites++;
                }
            }
        }
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return finder.find(0) == finder.find(grid.length + 1);
    }

    private int flattenCoordinates(int row, int col) {
        return (row - 1) * grid.length + col;
    }

    private boolean isOutside(int c) {
        return (c - 1) < 0 || c > grid.length;
    }
}
