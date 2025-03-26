package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    /**
     * Test method for
     * {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        //TC01: Test for a correct subtraction of two points
        // the result should be the vector (1,2,3)
        assertEquals(new Vector(1, 2, 3), p2.subtract(p1), "ERROR: subtract() wrong value");

        // =============== Boundary Values Tests ==================
        //TC11: Test for a subtraction of a point from itself
        //the result should be exception
        try {
            p1.subtract(p1);
            fail("ERROR: subtract() for point from itself does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("ERROR: subtract() for point from itself throws wrong exception");
        }
    }

    /** Test method for
     * {@link primitives.Point#add(primitives.Point)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        Vector v = new Vector(1, 2, 3);
        //TC01: Test for a correct addition of two points
        // the result should be the point (3,6,9)
        assertEquals(new Point(3, 6, 9), p1.add(p2), "ERROR: add() wrong value");

        // =============== Boundary Values Tests ==================
        //TC11: add vector to the point
        //the result should be the point (2,4,6)
        assertEquals(new Point(2, 4, 6), p1.add(v), "ERROR: add() wrong value");
    }

    /**
     * Test method for
     * {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        //TC01: Test for a correct squared distance between two points
        // the result should be 14
        assertEquals(14, p1.distanceSquared(p2), "ERROR: distanceSquared() wrong value");

        // =============== Boundary Values Tests ==================
        //TC11: Test for a squared distance between a point and itself
        // the result should be 0
        assertEquals(0, p1.distanceSquared(p1), "ERROR: distanceSquared() for point from itself is not zero");
    }

    /**
     * Test method for
     * {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 4, 6);
        //TC01: Test for a correct distance between two points
        // the result should be the square root of 14
        assertEquals(Math.sqrt(14), p1.distance(p2), "ERROR: distance() wrong value");

        // =============== Boundary Values Tests ==================
        //TC11: Test for a distance between a point and itself
        // the result should be 0
        assertEquals(0, p1.distance(p1), "ERROR: distance() for point from itself is not zero");
    }
}