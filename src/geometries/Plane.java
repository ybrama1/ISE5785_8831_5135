package geometries;
import primitives.*;
import geometries.Geometry;

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
        this.normal = null;
    }
    /**
     * function to calculate the normal to the plane
     * @param point the point on the plane
     * @return the normal to the plane
     */
    public Vector getNormal(Point point) {
        return normal;
    }
}
