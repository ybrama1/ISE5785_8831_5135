package geometries;

import primitives.Point;
import primitives.Ray;

// The class implements an Axis-Aligned Bounding Box (AABB) in 3D space.
// An AABB is defined by two points: the minimum point (min) and the maximum point (max).
public class AABB{
    public Point min;
    public Point max;


    public AABB(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Checks if a ray intersects with the AABB.
     * @param ray the ray to check for intersection
     * @return true if the ray intersects with the AABB, false otherwise
     */
    public boolean intersects(Ray ray) {
        double tMin = Double.NEGATIVE_INFINITY;
        double tMax = Double.POSITIVE_INFINITY;

        for (int i = 0; i < 3; i++) {
            // For each axis, calculate the intersection with the slab defined by min and max
            double origin = ray.getP0().getCoordinate(i);
            double direction = ray.getDir().getCoordinate(i);

            if (direction == 0) {
                // Ray is parallel to the slab. If origin not within slab, no intersection.
                if (origin < min.getCoordinate(i) || origin > max.getCoordinate(i)) {
                    return false;
                }
                continue;
            }

            double invD = 1.0 / direction;
            double t0 = (min.getCoordinate(i) - origin) * invD;
            double t1 = (max.getCoordinate(i) - origin) * invD;
            if (invD < 0) {
                double temp = t0;
                t0 = t1;
                t1 = temp;
            }

            tMin = Math.max(tMin, t0);
            tMax = Math.min(tMax, t1);

            if (tMax < tMin)
                return false;
        }
        return true;
    }

    /**
     * Calculates the surrounding AABB that contains both input boxes.
     * @param box1 the first AABB
     * @param box2 the second AABB
     * @return a new AABB that surrounds both input boxes
     */
    public static AABB surroundingBox(AABB box1, AABB box2) {
        Point small = new Point(
                Math.min(box1.min.getCoordinate(0), box2.min.getCoordinate(0)),
                Math.min(box1.min.getCoordinate(1), box2.min.getCoordinate(1)),
                Math.min(box1.min.getCoordinate(2), box2.min.getCoordinate(2))
        );
        Point big = new Point(
                Math.max(box1.max.getCoordinate(0), box2.max.getCoordinate(0)),
                Math.max(box1.max.getCoordinate(1), box2.max.getCoordinate(1)),
                Math.max(box1.max.getCoordinate(2), box2.max.getCoordinate(2))
        );
        return new AABB(small, big);
    }
}
