package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {


    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Cylinder c = new Cylinder(
                1,
                new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)),
                5);
        // TC01: Test for a normal to the cylinder in the middle
        // the result should be (0,1,0)
        assertEquals(
                new Vector(0, 1, 0),
                c.getNormal(new Point(1, 1, 0)),
                "ERROR: getNormal() wrong value");
        //TC02: Test for a normal to the cylinder at the bottom
        // the result should be (-1,0,0)
        assertEquals(
                new Vector(-1, 0, 0),
                c.getNormal(new Point(0, 0.5, 0)),
                "ERROR: getNormal() wrong value");
        //TC03: Test for a normal to the cylinder at the top
        // the result should be (1,0,0)
        assertEquals(
                new Vector(1, 0, 0),
                c.getNormal(new Point(5, 0.5, 0)),
                "ERROR: getNormal() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for a normal to the cylinder at the bottom in the center
        // the result should be (-1, 0, 0)
        assertEquals(
                new Vector(-1, 0, 0),
                c.getNormal(new Point(0, 0, 0)),
                "ERROR: getNormal() wrong value");
        // TC12: Test for a normal to the cylinder at the top in the center
        // the result should be (1, 0, 0)
        assertEquals(
                new Vector(1, 0, 0),
                c.getNormal(new Point(5, 0, 0)),
                "ERROR: getNormal() wrong value");
        // TC13: Test for a normal to the cylinder at the bottom in the edge
        // the result should be (-1, 0, 0)
        assertEquals(
                new Vector(-1, 0, 0),
                c.getNormal(new Point(0, 1, 0)),
                "ERROR: getNormal() wrong value");
        // TC14: Test for a normal to the cylinder at the top in the edge
        // the result should be (1, 0, 0)
        assertEquals(
                new Vector(1, 0, 0),
                c.getNormal(new Point(5, 1, 0)),
                "ERROR: getNormal() wrong value");
    }
}