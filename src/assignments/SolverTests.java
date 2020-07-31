import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolverTests {
    private final int[][] puzzle07 = {
            {1, 2, 3},
            {0, 7, 6},
            {5, 4, 8},
    };

    private final int[][] puzzle08 = {
            {2, 3, 5},
            {1, 0, 4},
            {7, 8, 6},
    };

    private final int[][] puzzle11 = {
            {1, 0, 2},
            {7, 5, 4},
            {8, 6, 3},
    };

    private final int[][] puzzle12 = {
            {1, 2, 3, 4, 5},
            {12, 6, 8, 9, 10},
            {0, 7, 13, 19, 14},
            {11, 16, 17, 18, 15},
            {21, 22, 23, 24, 20},
    };

    private void checkSolvableMoves(int[][] puzzle, int expectedMoves) {
        Board board = new Board(puzzle);
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(expectedMoves, solver.moves());
    }

    @Test
    void solvePuzzle07() {
        checkSolvableMoves(puzzle07, 7);
    }

    @Test
    void solvePuzzle08() {
        checkSolvableMoves(puzzle08, 8);
    }

    @Test
    void solvePuzzle11() {
        checkSolvableMoves(puzzle11, 11);
    }

    @Test
    void solvePuzzle12() {
        checkSolvableMoves(puzzle12, 12);
    }
}
