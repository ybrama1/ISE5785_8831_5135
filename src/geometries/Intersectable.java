package geometries;
import lighting.LightSource;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        return intersections == null
                ? null
                : intersections.stream()
                    .map(intersection -> intersection.point).toList();
    }

    public static class Intersection {
        /*** The geometry that was intersected*/
        public final Geometry geometry;
        /*** The point of intersection*/
        public final Point point;
        /*** The material of the geometry*/
        public final Material material;
        /*** The ray that created the intersection*/
        public Vector v;
        /*** The normal to the geometry at the intersection point*/
        public Vector normal;
        /*** a dot product between the normal and the ray direction*/
        public double nv;
        /*** The light source that created the intersection*/
        public LightSource light;
        /*** direction vector from the light source to the intersection point*/
        public Vector l;
        /*** dot product between the light source direction and the normal*/
        public double ln;


        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
            if (geometry != null) {
                this.material = geometry.getMaterial();
            }
            else {
                this.material = null;
            }
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
