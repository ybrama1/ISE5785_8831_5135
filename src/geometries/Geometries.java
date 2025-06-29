package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries extends Intersectable {
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
        this.add(Arrays.asList(geometry));
    }

    /**
     * Method to add a list of geometries to the list
     * @param geometries the list of geometries to add
     */
    public void add(List<? extends Intersectable> geometries) { this.geometries.addAll(geometries);}

    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        List<Intersection> intersections = new ArrayList<>();

        for (Intersectable geometry : geometries) {
            List<Intersection> geometryIntersections = geometry.calculateIntersectionsHelper(ray);
            if (geometryIntersections != null) {
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }
}