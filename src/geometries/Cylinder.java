package geometries;

import primitives.Ray;

/**
 * Class Cylinder is the class representing a cylinder in a Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Cylinder extends Tube {

    /**The height of the Cylinder*/
    private final double height;

    /**
     * constructor with parameters
     * @param radius the radius of the Cylinder
     * @param axis the axis of the Cylinder
     * @param height the height of the Cylinder
     */
    public Cylinder(double radius, Ray axis, double height){
        super(radius, axis);
        this.height = height;
    }
}
