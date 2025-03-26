package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    /**
     * Test method for
     * {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void subtract() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        fail("Test not implemented");
    }

    /**
     * Test method for
     * {@link primitives.Vector#normalize()}.
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 2);
        // TC01: Test for a correct normalization of a vector
        // the result should be the vector (1/3, 2/3, 2/3)
        assertEquals(new Vector(1.0/3, 2.0/3, 2.0/3), v.normalize(), "ERROR: normalize() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for a zero vector
        // the result should be an exception
        try {
            new Vector(0, 0, 0).normalize();
            fail("ERROR: normalize() for zero vector does not throw an exception");
        } catch (IllegalArgumentException ignore) {}

    }
}