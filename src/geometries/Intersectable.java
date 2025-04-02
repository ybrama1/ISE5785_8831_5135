package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface for geometries that can be intersected by rays.
 * It defines a method to find intersections between a ray and the geometry.
 * @author Jeshurun and Binyamin
 */
public interface Intersectable {
    /**
     * Finds the intersections between a ray and the geometry.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or an empty list if there are no intersections
     */
    List<Point> findIntersections(Ray ray);
}
