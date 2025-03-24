package geometries;

import primitives.Point;
import primitives.Vector;

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

    /**
     * calculate the normal to the sphere in the point on it
     * @param point the point on the sphere
     * @return the normal to the sphere in the point on it
     */
    public Vector getNormal(Point point) {
        return null;
    }
}
