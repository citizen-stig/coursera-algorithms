import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// % cat input6.txt        % cat input8.txt
// 6                       8
// 19000  10000             10000      0
// 18000  10000                 0  10000
// 32000  10000              3000   7000
// 21000  10000              7000   3000
//  1234   5678             20000  21000
// 14000  10000              3000   4000
//                          14000  15000
//                           6000   7000


public class BruteForceCollinearPointsTest {

    @Test
    void noPoints() {
        BruteCollinearPoints impl = new BruteCollinearPoints(new Point[0]);
        assertEquals(0, impl.numberOfSegments());
    }

    @Test
    void testInput6() {
        Point[] points = {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };

        BruteCollinearPoints impl = new BruteCollinearPoints(points);
        assertEquals(1, impl.numberOfSegments());
        LineSegment expectedSegment = new LineSegment(points[5], points[2]);
        assertEquals(expectedSegment.toString(), impl.segments()[0].toString());
    }

    @Test
    void testInput8() {
        Point[] points = {
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000)
        };

        BruteCollinearPoints impl = new BruteCollinearPoints(points);
        assertEquals(2, impl.numberOfSegments());

        LineSegment expectedSegmentOne = new LineSegment(points[0], points[1]);
        assertEquals(expectedSegmentOne.toString(), impl.segments()[0].toString());
        LineSegment expectedSegmentTwo = new LineSegment(points[5], points[4]);
        assertEquals(expectedSegmentTwo.toString(), impl.segments()[1].toString());
    }

    @Test
    void fiveRandomLineSegments() {
        Point[] points = {
                new Point(10780, 11709),
                new Point(8182, 14492),
                new Point(12124, 11709),
                new Point(8788, 3697),
                new Point(8182, 15150),
                new Point(11488, 6523),
                new Point(8233, 5327),
                new Point(9935, 7443),
                new Point(6753, 1070),
                new Point(7840, 11709),
                new Point(13043, 11307),
                new Point(10630, 6523),
                new Point(13360, 6523),
                new Point(9772, 11709),
                new Point(8182, 15197),
                new Point(10548, 5969),
                new Point(9778, 4975),
                new Point(8182, 11343),
                new Point(6605, 3303),
                new Point(13438, 6523)
        };

        BruteCollinearPoints impl = new BruteCollinearPoints(points);
        assertEquals(5, impl.numberOfSegments());
    }
}
