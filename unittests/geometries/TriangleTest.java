package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

class TriangleTest {

    /**
     * Test method for
     * {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Triangle t = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector v1 = new Point(0, 0, 0).subtract(new Point(1, 0, 0));
        Vector v2 = new Point(0, 0, 0).subtract(new Point(0, 1, 0));

        // TC01: Test for a normal to the triangle
        // the result should be 0
        assertTrue(t.getNormal(new Point(0.5,0.5,0)).dotProduct(v1) == 0 && t.getNormal(new Point(0.5,0.5,0)).dotProduct(v2) == 0, "ERROR: getNormal() wrong value");
    }

    /**
     * Test method for
     * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Point p1 = new Point(0.5, 0.5, 0);
        Point p001 = new Point(0, 0, -1);
        Triangle t = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray passes through the triangle (1 point)
        // the result should be (0.5,0.5,0)
        Ray ray1 = new Ray(p001, new Vector(0.5, 0.5, 1));
        assertEquals(List.of(p1), t.findIntersections(ray1), "ERROR: findIntersections() wrong value");
        // TC02: Ray passes outside the triangle against edge (0 points)
        // the result should be null
        Ray ray2 = new Ray(p001, new Vector(-1, 1, 1));
        assertNull(t.findIntersections(ray2), "ERROR: findIntersections() wrong value");
        // TC03: Ray passes outside the triangle against vertex (0 points)
        // the result should be null
        Ray ray3 = new Ray(p001, new Vector(-1, -1, 1));
        assertNull(t.findIntersections(ray3), "ERROR: findIntersections() wrong value");
        // =========== Boundary Values Tests ==================
        // TC11: Ray starts before and crosses the triangle in the side (1 point)
        // the result should be (0.5,0,0)
        Ray ray4 = new Ray(p001, new Vector(0.5, 0, 1));
        assertEquals(List.of(new Point(0.5, 0, 0)), t.findIntersections(ray4), "ERROR: findIntersections() wrong value");
        // TC12: Ray starts before and crosses the triangle in the vertex (1 point)
        // the result should be (1,0,0)
        Ray ray5 = new Ray(p001, new Vector(1, 0, 1));
        assertEquals(List.of(new Point(1, 0, 0)), t.findIntersections(ray5), "ERROR: findIntersections() wrong value");
        // TC13: Ray starts before and crosses the continuation of the edg (0 points)
        // the result should be (2,0,0)
        Ray ray6 = new Ray(p001, new Vector(2, 0, 1));
        assertNull(t.findIntersections(ray6), "ERROR: findIntersections() wrong value");


    }
}