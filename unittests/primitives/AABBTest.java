package primitives;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AABBTest {
    /**
     * Tests the intersection of a ray with an AABB.
     * @link primitive.AABB#intersects(Ray)
     */
    @Test
    public void testIntersects() {
        // ============ Equivalence Partitions Tests ==============
        // Create an AABB with min and max points
        AABB box = new AABB(new Point(0, 0, 0), new Point(1, 1, 1));

        //TC01: Test for a ray that intersects the AABB
        // Create a ray that intersects the AABB
        Ray ray1 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        assertTrue(box.intersects(ray1), "Ray should intersect the AABB");

        //TC02: Test for a ray that does not intersect the AABB
        // Create a ray that does not intersect the AABB
        Ray ray2 = new Ray(new Point(2, 2, -1), new Vector(0, 0, 1));
        assertFalse(box.intersects(ray2), "Ray should not intersect the AABB");

        // =============== Boundary Values Tests ==================
        //TC11: Test for a ray that is parallel to the AABB and outside
        // Create a ray that is parallel to the AABB and outside
        Ray ray3 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 1, 0));
        assertFalse(box.intersects(ray3), "Parallel ray should not intersect the AABB");
        //TC12: Test for an AABB that is 2D
        // Create a 2D AABB (min and max points are the same in one dimension)
        AABB box2D = new AABB(new Point(0, 0, 0), new Point(1, 1, 0));
        // Create a ray that intersects the 2D AABB
        Ray ray4 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        assertTrue(box2D.intersects(ray4), "Ray should intersect the 2D AABB");
    }
    /**
     * Tests the surrounding AABB calculation.
     * @link primitive.AABB#surroundingBox(AABB, AABB)
     */
    @Test
    public void testSurroundingBox() {
        // Create two AABBs
        AABB box1 = new AABB(new Point(0, 0, 0), new Point(1, 1, 1));
        AABB box2 = new AABB(new Point(0.5, 0.5, 0.5), new Point(1.5, 1.5, 1.5));

        // Calculate the surrounding box
        AABB surroundingBox = AABB.surroundingBox(box1, box2);

        // Check the min and max points of the surrounding box
        assertEquals(new Point(0, 0, 0), surroundingBox.getMin(), "Min point of surrounding box is incorrect");
        assertEquals(new Point(1.5, 1.5, 1.5), surroundingBox.getMax(), "Max point of surrounding box is incorrect");
    }
}
