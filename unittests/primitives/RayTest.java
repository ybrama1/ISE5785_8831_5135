package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for
     * {@link primitives.Ray#equals(java.lang.Object)}.
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        Ray r2 = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        Ray r3 = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Ray r4 = new Ray(new Point(2, 2, 3), new Vector(1, 0, 0));
        // TC01: Test for a correct comparison of two rays with
        // the result should be true
        assertEquals(
                r1,
                r2,
                "ERROR: equals() wrong value");

        // TC02: Test for a comparison of two rays with different directions
        // the result should be false
        assertNotEquals(
                r1,
                r3,
                "ERROR: equals() wrong value");

        //TC03: Test for a comparison of two rays with different starting points
        //the result should be false
        assertNotEquals(
                r1,
                r4,
                "ERROR: equals() wrong value");
    }

    /**
     * Test method for
     * {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        // TC01: Test for positive distance
        // the result should be (2,2,3)
        assertEquals(
                new Point(2, 2, 3),
                r1.getPoint(1),
                "ERROR: getPoint() wrong value");
        // TC02: Test for negative distance
        // the result should be (0,2,3)
        assertEquals(
                new Point(0, 2, 3),
                r1.getPoint(-1),
                "ERROR: getPoint() wrong value");

        // =========== Boundary Values Tests ==================
        // TC10: Test for zero distance
        // the result should be (1,2,3)
        assertEquals(
                new Point(1, 2, 3),
                r1.getPoint(0),
                "ERROR: getPoint() for zero distance does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Ray#findClosestPoint(java.util.List)}.
     */
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));
        // TC01: Test for a closest point in the center of the list
        // the result should be (2,2,3)
        assertEquals(
                new Point(2,2,3),
                r1.findClosestPoint(
                        List.of(new Point(3, 2, 3),
                                new Point(2, 2, 3),
                                new Point(4, 2, 3))),
                "ERROR: findClosestPoint() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for an empty list
        // the result should be null
        assertNull(
                r1.findClosestPoint(List.of()),
                "ERROR: findClosestPoint() for itself does not throw an exception");
        // TC12: Test for the closest point at the beginning of the list
        // the result should be (2,2,3)
        assertEquals(
                new Point(2, 2, 3),
                r1.findClosestPoint(
                        List.of(new Point(2, 2, 3),
                                new Point(3, 2, 3),
                                new Point(4, 2, 3))),
                "ERROR: findClosestPoint() wrong value");
        // TC13: Test for the closest point at the end of the list
        // the result should be (4,2,3)
        assertEquals(
                new Point(2, 2, 3),
                r1.findClosestPoint(
                        List.of(new Point(3, 2, 3),
                                new Point(4, 2, 3),
                                new Point(2, 2, 3))),
                "ERROR: findClosestPoint() wrong value");
    }

}