package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    //constructor tests
    @Test
    void testGeometries() {
        // =============== Boundary Values Tests ==================
        // TC11: Test for a constructor with an empty list
        Geometries g1 = new Geometries(new java.util.LinkedList<>());
        assertNotNull(g1, "ERROR: Geometries() wrong value");

    }
    @Test
    void add() {
    }

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: some intersections, but not in all geometries
        Plane p = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Sphere s = new Sphere(1, new Point(0, 0, 0));
        Tube t = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        Geometries g = new Geometries(new java.util.LinkedList<>());
        g.add(p);
        g.add(s);
        g.add(t);
        Ray r = new Ray(new Point(0, 1, -1), new Vector(1, 0, 1));
        assertEquals(2, g.findIntersections(r).size(), "ERROR: findIntersections() wrong value");

        // =============== Boundary Values Tests ==================
        Plane p1 = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Tube t1 = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        // TC11: Test for a list with no intersections
        // the result should be an empty list
        Geometries g1 = new Geometries(new java.util.LinkedList<>());
        g1.add(p1);
        g1.add(t1);
        Ray r1 = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        assertNull(g1.findIntersections(r1), "ERROR: findIntersections() wrong value");
        // TC12: Test for a list with one intersection
        // the result should be 1
        Ray r2 = new Ray(new Point(0, 1, -1), new Vector(1, 0, 1));
        assertEquals(1, g1.findIntersections(r2).size(), "ERROR: findIntersections() wrong value");

        // TC13: all shapes intersect
        // the result should be 3
        Ray r3 = new Ray(new Point(0, 1, -1), new Vector(1, 0, 0));
        assertEquals(3, g1.findIntersections(r3).size(), "ERROR: findIntersections() wrong value");
    }
}