package geometries;
import primitives.*;

/**
 * Abstract class RadialGeometry is the basic class representing a radial geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public abstract class RadialGeometry extends Geometry {
    /**The radius of the geometry*/
    protected final double radius;
    /**
     * constructor
     * @param radius the radius of the geometry
     */
    public RadialGeometry(double radius){
        this.radius = radius;
    }
}
