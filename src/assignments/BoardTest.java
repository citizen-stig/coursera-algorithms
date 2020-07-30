import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertEquals("3\n 1  2  3\n 4  5  6\n 7  8  0\n", board.toString());
    }

    @Test
    void testToString100() {
        int n = 10;
        int[][] b = generateBoard(n);
        Board board = new Board(b);
        assertEquals("10\n 1  2  3  4  5  6  7  8  9 10\n" +
                "11 12 13 14 15 16 17 18 19 20\n" +
                "21 22 23 24 25 26 27 28 29 30\n" +
                "31 32 33 34 35 36 37 38 39 40\n" +
                "41 42 43 44 45 46 47 48 49 50\n" +
                "51 52 53 54 55 56 57 58 59 60\n" +
                "61 62 63 64 65 66 67 68 69 70\n" +
                "71 72 73 74 75 76 77 78 79 80\n" +
                "81 82 83 84 85 86 87 88 89 90\n" +
                "91 92 93 94 95 96 97 98 99  0\n", board.toString());
    }

    @Test
    void testToString127() {
        int n = 12;
        int[][] b = generateBoard(n);
        Board board = new Board(b);
        assertEquals("12\n" +
                "  1   2   3   4   5   6   7   8   9  10  11  12\n" +
                " 13  14  15  16  17  18  19  20  21  22  23  24\n" +
                " 25  26  27  28  29  30  31  32  33  34  35  36\n" +
                " 37  38  39  40  41  42  43  44  45  46  47  48\n" +
                " 49  50  51  52  53  54  55  56  57  58  59  60\n" +
                " 61  62  63  64  65  66  67  68  69  70  71  72\n" +
                " 73  74  75  76  77  78  79  80  81  82  83  84\n" +
                " 85  86  87  88  89  90  91  92  93  94  95  96\n" +
                " 97  98  99 100 101 102 103 104 105 106 107 108\n" +
                "109 110 111 112 113 114 115 116 117 118 119 120\n" +
                "121 122 123 124 125 126 127 128 129 130 131 132\n" +
                "133 134 135 136 137 138 139 140 141 142 143   0\n", board.toString());
    }

    @Test
    void testToStringSuperLarge() {
        int n = 105;
        int[][] b = generateBoard(n);
        Board board = new Board(b);
        assertEquals("105\n" +
                "    1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16 ",
                board.toString().substring(0, 100));
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
                "3\n 0  1  3\n" +
                " 4  2  5\n" +
                " 7  8  6\n",
                neighbors.get(0).toString());
        assertEquals(
                "3\n 1  3  0\n" +
                        " 4  2  5\n" +
                        " 7  8  6\n",
                neighbors.get(1).toString());
        assertEquals(
                "3\n 1  2  3\n" +
                        " 4  0  5\n" +
                        " 7  8  6\n",
                neighbors.get(2).toString());
    }

    @Test
    void testTwin() {

        Board source = new Board(generateBoard(100));
        for (int i = 0; i < 1000 ; i++) {
            Board twin = source.twin();
            assertNotEquals(source, twin);
        }
    }

    int[][] generateBoard(int n) {
        int limit = n * n;
        int[][] board = new int[n][n];
        for (int tile = 1; tile <= limit; tile++) {
            int i = (tile - 1) / n;
            int j = (tile - 1) % n;
            board[i][j] = tile % limit;
        }
        return board;
    }
}
