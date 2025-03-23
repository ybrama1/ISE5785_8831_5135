package geometries;
import primitives.Point;
/**
 * Abstract class Geometry is the basic class representing a geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Geometry {
    /**
     * Abstract function to calculate the normal to the geometry
     * @param point the point on the geometry
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point point);
}
