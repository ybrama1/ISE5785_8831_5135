package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
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
        Vector u = center.subtract(p0);
        double tm = dir.dotProduct(u);

        double d = Math.sqrt(u.dotProduct(u) - tm * tm);

        if (d >= radius) {
            // The ray does not intersect the sphere
            return null;
        }

        double th = Math.sqrt(radius * radius - d * d);
        double t1 = Util.alignZero( tm - th);
        double t2 = Util.alignZero(tm + th);

        List<Point> intersections = new ArrayList<>();
        if (t1 > 0) {
            intersections.add(p0.add(dir.scale(t1)));
        }
        if (t2 > 0) {
            intersections.add(p0.add(dir.scale(t2)));
        }
        return intersections.isEmpty() ? null : intersections;
    }

}
