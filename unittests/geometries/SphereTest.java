package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;

class SphereTest {

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(1, new Point(0, 0, 0));
        // TC01: Test for a normal to the sphere
        // the result should be (1,0,0)
        assertEquals(new Vector(1, 0, 0), s.getNormal(new Point(1, 0, 0)), "ERROR: getNormal() wrong value");
    }
}