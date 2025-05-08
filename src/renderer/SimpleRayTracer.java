package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * SimpleRayTracer is a simple ray tracer that extends the RayTracerBase class.
 * It implements the traceRay method to trace a ray and return the color of the pixel.
 * @author Jeshurun and Binyamin
 */
public class SimpleRayTracer extends RayTracerBase{
    /**
     * Constructor for SimpleRayTracer
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        // Check if the ray intersects with any geometry in the scene
        // If it does, find the closest intersection point and return the color at that point
        // Otherwise, return the background color of the scene
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            return scene.background;
        } else {
            // Find the closest intersection point
            Point closestPoint = intersections.getFirst();
            // Calculate the color at the closest intersection point
            return calcColor(closestPoint);
        }
    }

    /**
     * Calculate the color at a given point
     * @param point the point to calculate the color at
     * @return the color at the point
     */
    public Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }


}
