import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private static final int[][] correctBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };
    private static final int[][] sampleDistance = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
    };

    @Test
    void testToString() {
        Board board = new Board(correctBoard);
        assertEquals("1 2 3\n4 5 6\n7 8 0\n", board.toString());
    }

    @Test
    void testDimension() {
        Board board = new Board(correctBoard);
        assertEquals(3, board.dimension());
    }

    @Test
    void hammingCorrectBoard() {
        Board board = new Board(correctBoard);
        assertEquals(0, board.hamming());
    }

    @Test
    void hammingSample() {
        Board board = new Board(sampleDistance);
        assertEquals(5, board.hamming());
    }

    @Test
    void manhattanCorrectBoard() {
        Board board = new Board(correctBoard);
        assertEquals(0, board.manhattan());
    }

    @Test
    void manhattanSample() {
        Board board = new Board(sampleDistance);
        assertEquals(10, board.manhattan());
    }

    @Test
    void checkIsGoal() {
        assertTrue(new Board(correctBoard).isGoal());
        assertFalse(new Board(sampleDistance).isGoal());
    }


    @Test
    void checkNeighbors() {
        int[][] board = {
                {1, 0, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        Board b = new Board(board);
        List<Board> neighbors = new ArrayList<>();
        b.neighbors().forEach(neighbors::add);
        assertEquals(3, neighbors.size());
        assertEquals(
                "0 1 3\n" +
                "4 2 5\n" +
                "7 8 6\n",
                neighbors.get(0).toString());
        assertEquals(
                "1 3 0\n" +
                        "4 2 5\n" +
                        "7 8 6\n",
                neighbors.get(1).toString());
        assertEquals(
                "1 2 3\n" +
                        "4 0 5\n" +
                        "7 8 6\n",
                neighbors.get(2).toString());
    }
}
