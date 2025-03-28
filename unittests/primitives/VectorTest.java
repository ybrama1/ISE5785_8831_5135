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
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1, -2, -3);
        // TC01: Test for a correct addition of two vectors
        // the result should be the vector (3,6,9)
        assertEquals(new Vector(3, 6, 9), v1.add(v2), "ERROR: add() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for a result of zero vector
        // the result should be an exception of IllegalArgumentException
        try {
            v1.add(v3);
            fail("ERROR: add() for opposite vectors does not throw an exception");
        } catch (IllegalArgumentException e) {assertTrue(true);}
        catch (Exception e) {fail("ERROR: add() for opposite vectors throws wrong exception");}
    }

    /**
     * Test method for
     * {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        // TC01: Test for a correct subtraction of two vectors
        // the result should be the vector (-1,-2,-3)
        assertEquals(new Vector(-1, -2, -3), v1.subtract(v2), "ERROR: subtract() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for a subtraction of a vector from itself
        // the result should be an exception
        assertThrows( IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: subtract() for vector from itself does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        // TC01: Test for a correct scaling of a vector
        // the result should be the vector (2,4,6)
        assertEquals(new Vector(2, 4, 6), v.scale(2), "ERROR: scale() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for a scaling of a vector by zero
        // the result should be an exception
        assertThrows( IllegalArgumentException.class, () -> v.scale(0), "ERROR: scale() for zero does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1, -1, 1);
        // TC01: Test for a correct dot product of two vectors
        // the result should be 28
        assertEquals(28, v1.dotProduct(v2), "ERROR: dotProduct() wrong value");


        // =============== Boundary Values Tests ==================
        // TC11: Test for orthogonal vectors
        // the result should be 0
        assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() for orthogonal vectors is not zero");

    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        Vector v3 = new Vector(-1, -1, 1);
        // TC01: Test for a correct cross product of two vectors
        // the result should be the vector (5,-4,1)
        assertEquals(new Vector(5, -4, 1), v1.crossProduct(v3), "ERROR: crossProduct() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test for parallel vectors
        // the result should be an exception
        assertThrows( IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for
     * {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        // TC01: Test for a correct squared length of a vector
        // the result should be 14
        assertEquals(14, v.lengthSquared(), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for
     * {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        // TC01: Test for a correct length of a vector
        // the result should be the square root of 14
        assertEquals(Math.sqrt(14), v.length(), "ERROR: length() wrong value");
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
        assertThrows( IllegalArgumentException.class, () -> new Vector(0, 0, 0).normalize(), "ERROR: normalize() for zero vector does not throw an exception");

    }
}