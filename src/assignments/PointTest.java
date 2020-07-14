import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointTest {
    // Compare
    @Test
    void compareToRightUp() {
        Point zero = new Point(0, 0);
        Point rightUp = new Point(3, 3);
        assertTrue(zero.compareTo(rightUp) < 0);
        assertTrue(rightUp.compareTo(zero) > 0);
    }

    @Test
    void compareSameY() {
        Point zero = new Point(0, 1);
        Point rightUp = new Point(3, 1);
        assertTrue(zero.compareTo(rightUp) < 0);
        assertTrue(rightUp.compareTo(zero) > 0);
    }

    @Test
    void compareSameX() {
        Point zero = new Point(3, 0);
        Point rightUp = new Point(3, 3);
        assertTrue(zero.compareTo(rightUp) < 0);
        assertTrue(rightUp.compareTo(zero) > 0);
    }

    // SLOPE
    @Test
    void slopeToRightUp() {
        Point zero = new Point(0, 0);
        Point rightUp = new Point(3, 3);

        assertEquals(1.0, zero.slopeTo(rightUp));
        assertEquals(1.0, rightUp.slopeTo(zero));
    }


    /**
     * positive infinite slope, where p and q have coordinates in [0, 32768)
     *      Failed on trial 1 of 100000
     *      p             = (9842, 18181)
     *      q             = (9842, 2528)
     *      p.slopeTo(q)  = -Infinity
     */
    @Test
    void slopeToVerticalLine() {
        Point p = new Point(9842, 18181);
        Point q = new Point(9842, 2528);

        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q));
        assertEquals(Double.POSITIVE_INFINITY, q.slopeTo(p));
    }


    // @Test
    // void anotherVerticalLine() {
    //     Point p = new Point(122, 282);
    //     Point q = new Point(122, 113);
    //
    //     assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(q));
    //     assertEquals(Double.POSITIVE_INFINITY, q.slopeTo(p));
    // }

    /**
     * positive infinite slope, where p and q have coordinates in [0, 500)
     *      Failed on trial 1 of 100000
     *      p             = (368, 151)
     *      q             = (368, 105)
     *      p.slopeTo(q)  = -Infinity
     */
    @Test
    void yetAnotherVerticalLine() {
        Point p = new Point(368, 151);
        Point q = new Point(368, 105);

        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q));
        assertEquals(Double.POSITIVE_INFINITY, q.slopeTo(p));
    }


    /**
     * * positive infinite slope, where p and q have coordinates in [0, 500)
     *      Failed on trial 1 of 100000
     *      p             = (368, 151)
     *      q             = (368, 105)
     *      p.slopeTo(q)  = -Infinity
     */
    @Test
    void positiveInfiniteSlope() {
        Point p = new Point(368, 151);
        Point q = new Point(368, 105);
        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q));
    }

    /**
     * symmetric for random points p and q with coordinates in [0, 500)
     *      Failed on trial 793 of 100000
     *      p               = (159, 391)
     *      q               = (159, 218)
     *      p.slopeTo(q)  = Infinity
     *      q.slopeTo(p)  = -Infinity
     */
    @Test
    void symmetricForRandomPoints() {
        Point p = new Point(159, 391);
        Point q = new Point(159, 218);
        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q));
        assertEquals(Double.POSITIVE_INFINITY, q.slopeTo(p));
    }

    /**
     *  positive zero     slope, where p and q have coordinates in [0, 500)
     *      Failed on trial 1 of 100000
     *      p             = (457, 23)
     *      q             = (235, 23)
     *      p.slopeTo(q)  = -0.0
     */
    @Test
    void positiveZeroSlopeSmallCoordinates() {
        Point zero = new Point (457, 23);
        Point onTheRight = new Point(235, 23);

        assertEquals(+0.0, zero.slopeTo(onTheRight));
        assertEquals(+0.0, onTheRight.slopeTo(zero));
    }

    /**
     * positive zero     slope, where p and q have coordinates in [0, 32768)
     *      Failed on trial 3 of 100000
     *      p             = (31978, 10339)
     *      q             = (28953, 10339)
     *      p.slopeTo(q)  = -0.0
     */
    @Test
    void positiveZeroSlope() {
        Point p = new Point(31978, 10339);
        Point q = new Point(28953, 10339);
        assertEquals(+0.0, p.slopeTo(q));
    }

    @Test
    void toItself() {
        Point p = new Point(319, 57);
        Point q = new Point(319, 57);

        assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(q));
    }

    @Test
    void slopeToDecliningLine() {
        Point zero = new Point(0, 0);
        Point rightBelow = new Point(5, -5);

        assertEquals(-1.0, zero.slopeTo(rightBelow));
        assertEquals(-1.0, rightBelow.slopeTo(zero));
    }

    // Comparator

    @Test
    void comparatorFromZero() {
        Point zero = new Point(0, 0);
        Comparator<Point> comparator = zero.slopeOrder();

        Point one = new Point(5, 1);
        Point two = new Point(3, 5);
        assertTrue(comparator.compare(one, two) < 0);
    }

    @Test
    void transitiveSlopeOrder() {
        Point p = new Point(41, 434);
        Point q = new Point(298, 190);
        Point r = new Point(259, 44);
        Point s = new Point(79, 349);
        assertEquals(1, p.slopeOrder().compare(q, r));
        assertEquals(1, p.slopeOrder().compare(r, s));
        assertEquals(1, p.slopeOrder().compare(q, s));
    }

    /**
     * sign of compare(), where p, q, and r have coordinates in [0, 500)
     *      Failed on trial 1608 of 100000
     *      p                         = (88, 378)
     *      q                         = (88, 15)
     *      r                         = (28, 4)
     *      student   p.compare(q, r) = -1
     *      reference p.compare(q, r) = 1
     *      reference p.slopeTo(q)    = Infinity
     *      reference p.slopeTo(r)    = 6.233333333333333
     */
    @Test
    void signOfCompare() {
        Point p = new Point(88, 378);
        Point q = new Point(88, 15);
        Point r = new Point(28, 4);

        assertEquals(1, p.slopeOrder().compare(q, r));
        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q));
        assertEquals(6.233333333333333, p.slopeTo(r));
    }

    /**
     * * sign of compare(), where p, q, and r have coordinates in [0, 32768)
     *      Failed on trial 31506 of 100000
     *      p                         = (18857, 18587)
     *      q                         = (24713, 12218)
     *      r                         = (18857, 9858)
     *      student   p.compare(q, r) = 1
     *      reference p.compare(q, r) = -1
     *      reference p.slopeTo(q)    = -1.0876024590163935
     *      reference p.slopeTo(r)    = Infinity
     */
    @Test
    void signOfCompareBigger() {
        Point p = new Point(18857, 18587);
        Point q = new Point(24713, 12218);
        Point r = new Point(18857, 9858);

        assertEquals(-1, p.slopeOrder().compare(q, r));
        assertEquals(-1.0876024590163935, p.slopeTo(q));
        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(r));
    }
}
