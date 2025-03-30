package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;
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
}