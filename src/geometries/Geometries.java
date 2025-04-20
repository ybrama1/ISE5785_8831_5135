package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;

public class Geometries {
    /**
     * List of geometries
     */
    private final List<Intersectable> geometries = new java.util.LinkedList<>();

    /**
     * Default constructor
     */
    public Geometries() {
        // No initialization needed
        }

    /**
     * Constructor
     * @param geometries the list of geometries
     */
    public Geometries (Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Method to add a geometry to the list
     * @param geometry the geometry to add
     */
    public void add(Intersectable... geometry) {
        for (Intersectable g : geometry) {
            this.geometries.add(g);
        }
    }

    /**
     * Method to find intersections with a ray
     * @param ray the ray to check for intersections
     * @return a list of intersection points, or an empty list if there are no intersections
     */
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new ArrayList<>();

        for (Intersectable geometry : geometries) {
            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }
}