package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
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

    /**
     * Test method for
     * {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Plane p = new Plane(new Point(1, 0,0), new Point(0,1,0), new Point(0,0,0));
        Point p100 = new Point(1, 0, 0);
        Point p010 = new Point(0, 1, 0);
        Point p001 = new Point(0, 0, 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the plane (0 points)
        Ray r1 = new Ray(p001, new Vector(1, 1, 1));
        assertNull(p.findIntersections(r1), "Ray's line out of plane");

        // TC02: Ray starts before and crosses the plane (1 point)
        Ray r2 = new Ray(p001, new Vector(-1,-1,-1));
        assertNotNull(p.findIntersections(r2), "Ray's line crosses plane");
        assertEquals(1, p.findIntersections(r2).size(), "Wrong number of points");

        // =========== Boundary Values Tests ==================
        // group 1: the ray in parallel to the plane
        // TC11: The ray is outside the plane (0 points)
        Ray r3 = new Ray(p001, new Vector(1, 1, 0));
        assertNull(p.findIntersections(r3), "Ray's line out of plane");
        // TC12: The ray is in the plane (0 points)
        Ray r4 = new Ray(p100, new Vector(0, 1, 0));
        assertNull(p.findIntersections(r4), "Ray's line in plane");
        // group 2: the ray is orthogonal to the plane
        // TC13: The ray starts at the plane (1 point)
        Ray r5 = new Ray(p100, new Vector(0, 0, 1));
        assertNotNull(p.findIntersections(r5), "Ray's line in plane");
        assertEquals(1, p.findIntersections(r5).size(), "Wrong number of points");
        assertEquals(p100, p.findIntersections(r5).getFirst(), "Wrong point");
        // TC14: The ray starts before the plane (1 point)
        Ray r6 = new Ray(new Point(1,0,-1), new Vector(0, 0, 1));
        assertNotNull(p.findIntersections(r6), "Ray's line in plane");
        assertEquals(1, p.findIntersections(r6).size(), "Wrong number of points");
        assertEquals(new Point(1, 0, 0), p.findIntersections(r6).getFirst(), "Wrong point");
        // TC15: The ray starts after the plane (0 points)
        Ray r7 = new Ray(new Point(1,0,1), new Vector(0, 0, 1));
        assertNull(p.findIntersections(r7), "Ray's line out of plane");
        // group 3: the ray is in the plane
        // TC16: The ray starts at the plane (0 points)
        Ray r8 = new Ray(p100, new Vector(0, 1, 1));
        assertNull(p.findIntersections(r8), "Ray's line in plane");
        // TC17: The ray starts in the plane at the spacial point (0 points)
        Ray r9 = new Ray(new Point(1, 0, 0), new Vector(1, 1, 1));
        assertNull(p.findIntersections(r9), "Ray's line in plane");
    }
}