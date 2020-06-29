import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PercolationTest {

    @Test
    void shouldNotPercolateOnInit() {
        Percolation p = new Percolation(1);
        assertFalse(p.percolates());
    }

    @Test
    void smallestGrid() {
        Percolation p = new Percolation(1);
        assertFalse(p.percolates());
        assertEquals(0, p.numberOfOpenSites());
        p.open(1, 1);
        assertEquals(1, p.numberOfOpenSites());
        assertTrue(p.percolates());
    }

    @Test
    void shouldPercolateAfterConnectingStraightFromTop() {
        Percolation p = new Percolation(5);
        int col = 2;
        int openSites = 0;
        for (int row = 1; row <= 5; row++) {
            assertFalse(p.percolates());
            p.open(row, col);
            assertTrue(p.isFull(row, col));
            openSites += 1;
            assertEquals(openSites, p.numberOfOpenSites());
        }
        assertTrue(p.percolates());
    }

    @Test
    void shouldPercolateAfterConnectingFromBottom() {
        int n = 5;
        Percolation p = new Percolation(n);
        int col = 2;
        int openSites = 0;
        for (int row = n; row >= 1; row--) {
            assertFalse(p.percolates());
            p.open(row, col);

            openSites += 1;
            assertEquals(openSites, p.numberOfOpenSites());
        }
        assertTrue(p.percolates());
    }

    @Test
    void shouldNotPercolateDiagonally() {
        int n = 5;
        Percolation p = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            p.open(i, i);
            assertFalse(p.percolates());
        }
    }

    @Test
    void isOpenShouldThrowProperly() {
        Percolation p = new Percolation(10);
        assertThrows(IllegalArgumentException.class, () -> p.open(-2147483648, -2147483648));
        assertThrows(IllegalArgumentException.class, () -> p.open(5, 0));
    }
}