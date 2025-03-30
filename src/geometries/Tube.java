package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Vector getNormal(Point point) {
        // The normal of a tube is the vector from the axis to the point on the tube
        return null;
    }
}
