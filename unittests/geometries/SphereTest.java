package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(1, new Point(0, 0, 0));
        // TC01: Test for a normal to the sphere
        // the result should be (1,0,0)
        assertEquals(new Vector(1, 0, 0), s.getNormal(new Point(1, 0, 0)), "ERROR: getNormal() wrong value");
    }


    /** A point used in some tests */
    private final Point p001 = new Point(0, 0, 1);
    /** A point used in some tests */
    private final Point p100 = new Point(1, 0, 0);
    /** A vector used in some tests */
    private final Vector v001 = new Vector(0, 0, 1);

    /** A vector used in some tests */
    private final Vector v100 = new Vector(1, 0, 0);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310));
        assertNotNull(result1, "Can't be empty list");
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(0.5,0.5,0), v310));
        assertNotNull(result2, "Can't be empty list");
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(gp2), result2, "Ray crosses sphere");
        // TC04: Ray starts after the sphere (0 points)
        final var result3 = sphere.findIntersections(new Ray(new Point(2, 1, 0), v310));
        assertNull(result3, "Ray's line out of sphere");


        // =============== Boundary Values Tests ==================
        // **** Group 1: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result4 = sphere.findIntersections(new Ray(gp1, v310));
        assertNotNull(result4, "Can't be empty list");
        assertEquals(1, result4.size(), "Wrong number of points");
        assertEquals(List.of(gp2), result4, "Ray crosses sphere");
        // TC12: Ray starts at sphere and goes outside (0 points)
        final var result5 = sphere.findIntersections(new Ray(gp2, v310));
        assertNull(result5, "Ray's line out of sphere");

        // **** Group 2: Ray's line goes through the center
        // TC21: Ray starts before the sphere (2 points)
        final var result6 = sphere.findIntersections(new Ray(p01, v100));
        assertNotNull(result6, "Can't be empty list");
        assertEquals(2, result6.size(), "Wrong number of points");
        assertEquals(List.of(Point.ZERO, new Point(2,0,0)), result6, "Ray crosses sphere");
        // TC22: Ray starts at sphere and goes inside (1 point)
        final var result7 = sphere.findIntersections(new Ray(Point.ZERO, v100));
        assertNotNull(result7, "Can't be empty list");
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,0)), result7, "Ray crosses sphere");
        // TC23: Ray starts inside (1 point)
        final var result8 = sphere.findIntersections(new Ray(new Point(0.5,0,0), v100));
        assertNotNull(result8, "Can't be empty list");
        assertEquals(1, result8.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,0)), result8, "Ray crosses sphere");
        // TC24: Ray starts at the center (1 point)
        final var result9 = sphere.findIntersections(new Ray(p100, v100));
        assertNotNull(result9, "Can't be empty list");
        assertEquals(1, result9.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,0)), result9, "Wrong points");
        // TC25: Ray starts at sphere and goes outside (0 points)

        // TC26: Ray starts after sphere (0 points)
        // **** Group 3: Ray's line is tangent to the sphere (all tests 0 points)
        // TC31: Ray starts before the tangent point
        // TC32: Ray starts at the tangent point
        // TC33: Ray starts after the tangent point
        // **** Group 4: Special cases
        // TC41: Ray's line is outside sphere, ray is orthogonal to ray start to sphere's center line
        // TC42: Ray's starts inside, ray is orthogonal to ray start to sphere's center line
    }
}