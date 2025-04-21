package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    //constructor tests
    @Test
    void testGeometries() {
        // =============== Boundary Values Tests ==================
        // TC11: Test for a constructor with an empty list
        Geometries g1 = new Geometries();
        assertNotNull(
                g1,
                "ERROR: Geometries() wrong value");

    }

    @Test
    void findIntersections() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0,0));
        Sphere sphere1 = new Sphere(1, new Point(3, 0,0)); // 2 intersections
        Sphere sphere2 = new Sphere(1, new Point(0,10,0)); // 0 intersections
        Triangle triangle1 = new Triangle(
                new Point(2, -1, 1),
                new Point(2, -1, -1),
                new Point(2, 1, 0)); // 1 intersection
        Triangle triangle2 = new Triangle(
                new Point(0,5,0),
                new Point(1,5,1),
                new Point(3, 5, 1)); // 0 intersections
        Plane plane1 = new Plane(
                new Point(10, 0, 0),
                new Vector(1,0,0)); // 1 intersection
        Plane plane2 = new Plane(
                new Point(-1, 0, 0),
                new Vector(1, 0, 0)); // 0 intersection
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for some shapes that intersect with the ray and some that don't
        Geometries geometries = new Geometries(
                sphere1,
                sphere2,
                triangle1,
                triangle2,
                plane1,
                plane2);
        assertEquals(
                4,
                geometries.findIntersections(ray).size(),
                "ERROR: findIntersections() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for an empty list
        Geometries geometries1 = new Geometries();
        assertNull(
                geometries1.findIntersections(ray),
                "ERROR: findIntersections() wrong value");
        // TC12: Test for a list with one shape that intersects with the ray
        Geometries geometries2 = new Geometries(sphere2, triangle1, plane2);
        assertEquals(
                1,
                geometries2.findIntersections(ray).size(),
                "ERROR: findIntersections() wrong value");
        // TC13: Test for a list with no shapes that intersect with the ray
        Geometries geometries3 = new Geometries(sphere2, triangle2, plane2);
        assertNull(
                geometries3.findIntersections(ray),
                "ERROR: findIntersections() wrong value");
        // TC14: Test for a list with all shapes that intersect with the ray
        Geometries geometries4 = new Geometries(sphere1, triangle1, plane1);
        assertEquals(
                4,
                geometries4.findIntersections(ray).size(),
                "ERROR: findIntersections() wrong value");

    }
}