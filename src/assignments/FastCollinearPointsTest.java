import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FastCollinearPointsTest {

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

        FastCollinearPoints impl = new FastCollinearPoints(points);
        assertEquals(1, impl.numberOfSegments());
        LineSegment expectedSegment = new LineSegment(
                new Point(14000, 10000),
                new Point(32000, 10000)
        );
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

        FastCollinearPoints impl = new FastCollinearPoints(points);
        assertEquals(2, impl.numberOfSegments());

        LineSegment expectedSegmentOne = new LineSegment(
                new Point(3000, 4000),
                new Point(20000, 21000)
        );
        assertEquals(expectedSegmentOne.toString(), impl.segments()[0].toString());
        LineSegment expectedSegmentTwo = new LineSegment(
                new Point(0, 10000),
                new Point(10000, 0)
        );
        assertEquals(expectedSegmentTwo.toString(), impl.segments()[1].toString());
    }

}
