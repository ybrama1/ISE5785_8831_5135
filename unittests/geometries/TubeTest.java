package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for
     * {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube t = new Tube(1, new Ray(new Point(0,0,0), new Vector(1,0,0)));
        // TC01: Test for a normal to the tube
        // the result should be true
        assertEquals(
                t.getNormal(new Point(1, 0, 1)),
                new Vector(0, 0, 1).normalize(),
                "ERROR: getNormal() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for a normal to the tube in the opposite direction
        // the result should be true
        assertEquals(
                t.getNormal(new Point(0, 0, 1)),
                new Vector(0, 0, 1).normalize(),
                "ERROR: getNormal() wrong value");
    }
}