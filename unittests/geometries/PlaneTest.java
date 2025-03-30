package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

class PlaneTest {

    /**
     * Test method for
     * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    void Plane() {
        Plane p = new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 2,3));

        Vector v1 = new Point(1, 1, 1).subtract(new Point(2, 2, 2));
        Vector v2 = new Point(1, 1, 1).subtract(new Point(3, 2, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a normal orthogonal to 2 vectors in the plane
        // the result should be true
        assertTrue(p.getNormal().dotProduct(v1) == 0 && p.getNormal().dotProduct(v2) == 0, "ERROR: Plane() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: The first point is the same as the second point
        // the result should be exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(3, 2, 3)), "ERROR: Plane() for the same points does not throw an exception");
        // TC12: The first point is the same as the third point
        // the result should be exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(1, 1, 1)), "ERROR: Plane() for the same points does not throw an exception");
        // TC13: The second point is the same as the third point
        // the result should be exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(2, 2, 2)), "ERROR: Plane() for the same points does not throw an exception");
        // TC14: The points are identical
        // the result should be exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 1, 1)), "ERROR: Plane() for the same points does not throw an exception");
        // TC15: The points are on the same line
        // the result should be exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)), "ERROR: Plane() for points on the same line does not throw an exception");
    }


    /**
     * Test method for
     * {@link geometries.Plane#getNormal()}.
     */
    @Test
    void testGetNormal() {
        Plane p = new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(1, 2, 3));

        Vector v1 = new Point(1, 1, 1).subtract(new Point(2, 2, 2));
        Vector v2 = new Point(1, 1, 1).subtract(new Point(1, 2, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a normal orthogonal to 2 vectors in the plane
        // the result should be true
        assertTrue(p.getNormal().dotProduct(v1) == 0 && p.getNormal().dotProduct(v2) == 0, "ERROR: getNormal() wrong value");

        //TC02: Test if the normal length is 1
        // the result should be 1
        assertEquals(1, p.getNormal().length(), "ERROR: getNormal() wrong value");
    }

    /**
     * Test method for
     * {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormalPoint() {
        Plane p = new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 2, 3));

        Vector v1 = new Point(1, 1, 1).subtract(new Point(2, 2, 2));
        Vector v2 = new Point(1, 1, 1).subtract(new Point(3, 2, 3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a normal orthogonal to 2 vectors in the plane
        // the result should be true
        assertTrue(p.getNormal(new Point(1, 1, 1)).dotProduct(v1) == 0 && p.getNormal(new Point(1, 1, 1)).dotProduct(v2) == 0, "ERROR: getNormal(Point) wrong value");

        //TC02: Test if the normal length is 1
        // the result should be 1
        assertEquals(1.0 , Util.alignZero(p.getNormal(new Point(1, 1, 1)).length()),1E-10, "ERROR: getNormal(Point) wrong value");
    }

}