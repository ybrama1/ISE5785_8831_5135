package geometries;
import primitives.Point;
import primitives.Vector;

import java.util.List;
import  primitives.Ray;
/**
 * Abstract class Geometry is the basic class representing a geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public abstract class Geometry implements Intersectable {
    /**
     * Abstract function to calculate the normal to the geometry
     * @param point the point on the geometry
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point point);

    @Override
    public abstract List<Point> findIntersections(Ray ray);
}
