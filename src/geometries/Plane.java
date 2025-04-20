package geometries;
import primitives.*;

import java.util.List;

/**
 * Class Plane is the basic class representing a plane in a Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Plane extends Geometry {
    /**The point on the plane*/
    private final Point q0;
    /**The normal to the plane*/
    private final Vector normal;
    /**
     * constructor creates the plane
     * @param q0 the point on the plane
     * @param normal the normal to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }
    /**
     * constructor creates the plane
     * @param p1 the first point on the plane
     * @param p2 the second point on the plane
     * @param p3 the third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        // Calculate the normal using the cross product of two vectors on the plane
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Points must be distinct");
        }
        // the points should not be on the same line
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        if(v1.dotProduct(v2) == v1.length()*v2.length()){
            throw new IllegalArgumentException("Points must not be on the same line");
        }
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * normal getter
     * @return the normal to the geometry
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Check if the ray is parallel to the plane
        double denominator = normal.dotProduct(ray.getDir());
        if (denominator == 0) {
            return null; // The ray is parallel to the plane
        }

        if(ray.getP0().equals(q0)) {
            return null; // The ray starts on the plane
        }
        // Calculate the t value for the intersection point
        double t = normal.dotProduct(q0.subtract(ray.getP0())) / denominator;
        if (t < 0) {
            return null; // The intersection point is behind the ray's origin
        }
        // Calculate the intersection point
        Point intersectionPoint = ray.getP0().add(ray.getDir().scale(t));
        return List.of(intersectionPoint);
    }
}
