package geometries;

import primitives.AABB;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Tube is the class representing a Tube(infinity cylinder) in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Tube extends RadialGeometry {

    /**The axis of the Tube*/
    protected final Ray axis;

    /**
     * constructor with parameters
     * @param radius the radius of the tube
     * @param axis the axis of the tube
     */
    public Tube(double radius, Ray axis){
        super(radius);
        this.axis = axis;
    }

    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // The tube is infinite, so we need to find the intersection of the ray with the axis
        // and then check if the point is within the radius of the tube
        return null;
    }

    // if anyone ever want it, have fun with implementing everything
    @Override
    public AABB getBoundingBox() {
        return null;
    }

    @Override
    public Vector getNormal(Point point) {
        // The normal of a tube is the vector from the axis to the point on the tube
        // The projection of the point on the axis
        Vector u = point.subtract(axis.getP0());
        double t = axis.getDir().dotProduct(u);
        if (t == 0) {
            // The point is on the axis
            return u.normalize();
        }
        // The projection of the point on the axis
        Point projection = axis.getP0().add(axis.getDir().scale(t));
        // The vector from the projection to the point
        Vector normal = point.subtract(projection);
        // The normal is the vector from the projection to the point
        return normal.normalize();
    }
}
