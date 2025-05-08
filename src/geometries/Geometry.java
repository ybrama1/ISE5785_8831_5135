package geometries;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;
import  primitives.Ray;
/**
 * Abstract class Geometry is the basic class representing a geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public abstract class Geometry extends Intersectable {
    /***The color of the geometry*/
    protected Color emission = Color.BLACK;
    /**
     * Abstract function to calculate the normal to the geometry
     * @param point the point on the geometry
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point point);

    public Color getEmission() {
        return emission;
    }
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    @Override
    public abstract List<Intersection> calculateIntersectionsHelper(Ray ray);
}
