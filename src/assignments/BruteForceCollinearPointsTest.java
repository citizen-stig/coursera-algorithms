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
    void testInput6() {
        Point[] points = {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234,5678),
                new Point(14000, 10000)
        };

        BruteCollinearPoints impl = new BruteCollinearPoints(points);
        assertEquals(1, impl.numberOfSegments());
        LineSegment expectedSegment = new LineSegment(points[5], points[2]);
        assertEquals(expectedSegment.toString(), impl.segments()[0].toString());
    }

}
