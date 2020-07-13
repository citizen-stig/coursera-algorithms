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

    @Test
    void slopeToVerticalLine() {
        Point zero = new Point(0, 0);
        Point above = new Point(0, 10);

        assertEquals(Double.NEGATIVE_INFINITY, zero.slopeTo(above));
        assertEquals(Double.POSITIVE_INFINITY, above.slopeTo(zero));
    }

    @Test
    void anotherVerticalLine() {
        Point p = new Point(444, 431);
        Point q = new Point(444, 364);

        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q));
    }

    @Test
    void slopeToHorizontalLine() {
        Point zero = new Point(0, 0);
        Point onTheRight = new Point(10, 0);

        assertEquals(-0.0, zero.slopeTo(onTheRight));
        assertEquals(+0.0, onTheRight.slopeTo(zero));
    }

    @Test
    void anotherHorizontalLine() {
        Point p = new Point(435, 330);
        Point q =  new Point(79, 330);
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
        assertEquals(1,  p.slopeOrder().compare(q, r));
        assertEquals(1,  p.slopeOrder().compare(r, s));
        assertEquals(1,  p.slopeOrder().compare(q, s));
    }
}
