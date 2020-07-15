import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
                new Point(10000, 0),
                new Point(0, 10000)
        );
        assertEquals(expectedSegmentOne.toString(), impl.segments()[0].toString());
        LineSegment expectedSegmentTwo = new LineSegment(
                new Point(3000, 4000),
                new Point(20000, 21000)
        );
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

        FastCollinearPoints impl = new FastCollinearPoints(points);
        assertEquals(5, impl.numberOfSegments());
    }


    @Test
    void test4By4Grid() {
        Point[] points = {
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(1, 0),
                new Point(1, 1),
                new Point(1, 2),
                new Point(1, 3),
                new Point(2, 0),
                new Point(2, 1),
                new Point(2, 2),
                new Point(2, 3),
                new Point(3, 0),
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 3)
        };

        FastCollinearPoints impl = new FastCollinearPoints(points);
        assertEquals(10, impl.numberOfSegments());
        LineSegment[] segments = impl.segments();

        assertEquals(10, segments.length);
    }

    @Test
    void immutableDataCheck() {
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

        LineSegment[] segmentsOne = impl.segments();
        LineSegment[] segmentsOneOrigin = segmentsOne.clone();
        assertEquals(2, segmentsOne.length);

        LineSegment expectedSegmentOne = new LineSegment(
                new Point(10000, 0),
                new Point(0, 10000)
        );
        assertEquals(expectedSegmentOne.toString(), segmentsOne[0].toString());
        LineSegment expectedSegmentTwo = new LineSegment(
                new Point(3000, 4000),
                new Point(20000, 21000)
        );
        assertEquals(expectedSegmentTwo.toString(), segmentsOne[1].toString());

        points[1] = new Point(5000, 5000);
        segmentsOne[0] = new LineSegment(new Point(1, 0), new Point(0, 1));
        LineSegment[] segmentsTwo = impl.segments();

        assertEquals(2, segmentsTwo.length);
        assertEquals(expectedSegmentOne.toString(), segmentsTwo[0].toString());
        assertEquals(expectedSegmentTwo.toString(), segmentsTwo[1].toString());
        assertArrayEquals(segmentsOneOrigin, segmentsTwo);
    }

    @Test
    void input9() {
        Point[] points = {
                new Point(1000, 1000),
                new Point(2000, 2000),
                new Point(3000, 3000),
                new Point(4000, 4000),
                new Point(5000, 5000),
                new Point(6000, 6000),
                new Point(7000, 7000),
                new Point(8000, 8000),
                new Point(9000, 9000)
        };

        FastCollinearPoints impl = new FastCollinearPoints(points);
        assertEquals(1, impl.numberOfSegments());
        LineSegment[] segments = impl.segments();
        assertEquals(1, segments.length);
        assertEquals(new LineSegment(
                new Point(1000, 1000),
                new Point(9000, 9000)
        ).toString(), segments[0].toString());
    }
}
