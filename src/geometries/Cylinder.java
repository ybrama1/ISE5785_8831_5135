package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    @Override
    public Vector getNormal(Point point) {
        // check if the point is on the top or bottom base of the cylinder or on the side
        if (point.equals(axis.getP0())){
            // The point is on the bottom base of the cylinder
            return axis.getDir().scale(-1);
        } else if (point.equals(axis.getP0().add(axis.getDir().scale(height)))){
            // The point is on the top base of the cylinder
            return axis.getDir();
        }
        double t = axis.getDir().dotProduct(point.subtract(axis.getP0()));
        if (t == 0){
            return axis.getDir().scale(-1);
        } else if (t == height) {
            return axis.getDir();
        } else {
            // The point is on the side of the cylinder
            return super.getNormal(point);
        }
    }
}
