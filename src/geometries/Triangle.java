package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {

        List<Intersection> intersections = plane.calculateIntersectionsHelper(ray);
        if (intersections == null) {
            return null;
        }
        Point p = intersections.getFirst().point;
        // check if the point is one of the vertices
        if (p.equals(vertices.get(0)) || p.equals(vertices.get(1)) || p.equals(vertices.get(2))) {
            return List.of(new Intersection(this, p));
        }
        // check if the point is on the edge of the triangle

        // Check if the point is inside the triangle using the cross product
        Vector v1 = vertices.get(0).subtract(p).normalize();
        Vector v2 = vertices.get(1).subtract(p).normalize();
        Vector v3 = vertices.get(2).subtract(p).normalize();
        //check if they are the same
        if (v1.equals(v2) || v1.equals(v3) || v2.equals(v3)) {
            return null;
        }
        // check if the vectors are in the opposite direction
        if (v1.equals(v2.scale(-1)) || v1.equals(v3.scale(-1)) || v2.equals(v3.scale(-1))) {
            return List.of(new Intersection(this, p));
        }
        double s1 = v1.crossProduct(v2).dotProduct(plane.getNormal());
        double s2 = v2.crossProduct(v3).dotProduct(plane.getNormal());
        double s3 = v3.crossProduct(v1).dotProduct(plane.getNormal());
        if (s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0) {
            return List.of(new Intersection(this, p));
        } else {
            return null;
        }

    }
}