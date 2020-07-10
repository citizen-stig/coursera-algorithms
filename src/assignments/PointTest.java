import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointTest {
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

        assertEquals(Double.POSITIVE_INFINITY, zero.slopeTo(above));
        assertEquals(Double.NEGATIVE_INFINITY, above.slopeTo(zero));
    }

    @Test
    void slopeToHorizontalLine() {
        Point zero = new Point(0, 0);
        Point onTheRight = new Point(10, 0);

        assertEquals(+0.0, zero.slopeTo(onTheRight));
        assertEquals(-0.0, onTheRight.slopeTo(zero));
    }

    @Test
    void slopeToDecliningLine() {
        Point zero = new Point(0, 0);
        Point rightBelow = new Point(5, -5);

        assertEquals(-1.0, zero.slopeTo(rightBelow));
        assertEquals(-1.0, rightBelow.slopeTo(zero));
    }
}