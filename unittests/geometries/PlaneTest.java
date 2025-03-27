package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;

class PlaneTest {

    /**
     * Test method for
     * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    void Plane() {
        Plane p = new Plane(new Point (1,1,1), new Point(2,2,2), new Point(3,3,3));

        Vector v1 = new Point(1, 1, 1).subtract(new Point(2, 2, 2));
        Vector v2 = new Point(1, 1, 1).subtract(new Point(3, 3, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a normal orthogonal to 2 vectors in the plane
        // the result should be true
        assertTrue(p.getNormal().dotProduct(v1) == 0 && p.getNormal().dotProduct(v2) == 0, "ERROR: Plane() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: The first point is the same as the second point
        // the result should be exception
        try {
            new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(3, 3, 3));
            fail("ERROR: Plane() for the same points does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("ERROR: Plane() for the same points throws wrong exception");
        }
        // TC12: The first point is the same as the third point
        // the result should be exception
        try {
            new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(1, 1, 1));
            fail("ERROR: Plane() for the same points does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("ERROR: Plane() for the same points throws wrong exception");
        }
        // TC13: The second point is the same as the third point
        // the result should be exception
        try {
            new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(2, 2, 2));
            fail("ERROR: Plane() for the same points does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("ERROR: Plane() for the same points throws wrong exception");
        }
        // TC14: The points are identical
        // the result should be exception
        try {
            new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 1, 1));
            fail("ERROR: Plane() for the same points does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("ERROR: Plane() for the same points throws wrong exception");
        }
        // TC15: The points are on the same line
        // the result should be exception
        try {
            new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3));
            fail("ERROR: Plane() for the same points does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("ERROR: Plane() for the same points throws wrong exception");
        }
    }


    /**
     * Test method for
     * {@link geometries.Plane#getNormal()}.
     */
    @Test
    void testGetNormal() {
        Plane p = new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3));

        Vector v1 = new Point(1, 1, 1).subtract(new Point(2, 2, 2));
        Vector v2 = new Point(1, 1, 1).subtract(new Point(3, 3, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a normal orthogonal to 2 vectors in the plane
        // the result should be true
        assertTrue(p.getNormal().dotProduct(v1) == 0 && p.getNormal().dotProduct(v2) == 0, "ERROR: getNormal() wrong value");
    }

    /**
     * Test method for
     * {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormalPoint() {
        Plane p = new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3));

        Vector v1 = new Point(1, 1, 1).subtract(new Point(2, 2, 2));
        Vector v2 = new Point(1, 1, 1).subtract(new Point(3, 3, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a normal orthogonal to 2 vectors in the plane
        // the result should be true
        assertTrue(p.getNormal(new Point(1, 1, 1)).dotProduct(v1) == 0 && p.getNormal(new Point(1, 1, 1)).dotProduct(v2) == 0, "ERROR: getNormal(Point) wrong value");
    }

}