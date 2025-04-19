package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Class Sphere is the class representing a sphere in a Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Sphere extends RadialGeometry {

    /**The point in the center of the sphere*/
    private final Point center;

    /**
     * constructor with parameters
     * @param radius the radius of the sphere
     * @param center the center of the sphere
     */
    public Sphere(double radius, Point center){
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        // The normal of a sphere is the vector from the center to the point on the sphere
        Vector normal = point.subtract(center);
        return normal.normalize();
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();

        if (p0.equals(center)) {
            // The ray starts at the center of the sphere
            return List.of(p0.add(dir.scale(radius)));
        }
        Vector L = center.subtract(p0);
        double lengthL = L.length();

        double b = dir.dotProduct(L)*dir.dotProduct(L);
        double c = lengthL * lengthL - radius * radius;
        double discriminant = b * b - 4 * c;

        if (discriminant < 0) {
            // No intersection
            return null;
        } else if (Util.isZero(discriminant)) {
            // One intersection
            double t = -b / (2 * c);
            if (t < 0) {
                // The intersection is behind the ray
                return null;
            }
            return List.of(p0.add(dir.scale(t)));
        } else {
            // Two intersections
            double t1 = (-b + Math.sqrt(discriminant)) / (2);
            double t2 = (-b - Math.sqrt(discriminant)) / (2);
            if (t1 < 0 && t2 < 0) {
                // Both intersections are behind the ray
                return null;
            }
            else if (t1 < 0) {
                // Only t2 is valid
                return List.of(p0.add(dir.scale(t2)));
            }
            else if (t2 < 0) {
                // Only t1 is valid
                return List.of(p0.add(dir.scale(t1)));
            }
            return List.of(p0.add(dir.scale(t1)), p0.add(dir.scale(t2)));
        }
    }

}
