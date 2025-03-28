package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Class Triangle is the class representing a 2-Dimensional triangle in a Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Triangle extends Polygon{
    /**
     * constructor creates the triangle
     * @param p1 the first point on the triangle
     * @param p2 the second point on the triangle
     * @param p3 the third point on the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }


}