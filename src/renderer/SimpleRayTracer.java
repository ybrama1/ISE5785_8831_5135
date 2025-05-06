package renderer;

import lighting.AmbientLight;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

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
        if (scene.geometries.findIntersections(ray) == null || scene.geometries.findIntersections(ray).isEmpty()) {
            return scene.background;
        } else {
            // Find the closest intersection point
            Point closestPoint = scene.geometries.findIntersections(ray).getFirst();
            // Calculate the color at the closest intersection point
            return calcColor(closestPoint);
        }
    }

    public Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }


}
