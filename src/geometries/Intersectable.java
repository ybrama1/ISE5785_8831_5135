package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface for geometries that can be intersected by rays.
 * It defines a method to find intersections between a ray and the geometry.
 * @author Jeshurun and Binyamin
 */
public abstract class Intersectable {
    /**
     * Finds the intersections between a ray and the geometry.
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or an empty list if there are no intersections
     */
    public final List<Point> findIntersections(Ray ray) {
        var intersections = calculateIntersections(ray);
        return intersections == null ? null
                : intersections.stream()
                .map(intersection -> intersection.point).toList();
    }

    public static class Intersection {
        public final Geometry geometry;
        public final Point point;

        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Intersection other)) return false;
            return this.geometry == other.geometry && this.point.equals(other.point);
        }
        @Override
        public String toString() {
            return "Intersection{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
    /**
     * Help to find the intersections between a ray and the geometry, returning a list of Intersection objects.
     * @param ray the ray to check for intersections
     * @return a list of Intersection objects, or an empty list if there are no intersections
     */
    protected abstract List<Intersection> calculateIntersectionsHelper (Ray ray);
    /**
     * Finds the intersections between a ray and the geometry, returning a list of Intersection objects.
     * @param ray the ray to check for intersections
     * @return a list of Intersection objects, or an empty list if there are no intersections
     */
    public final List<Intersection> calculateIntersections(Ray ray) {
         return calculateIntersectionsHelper(ray);
    }
}
