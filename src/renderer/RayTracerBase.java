package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class for ray tracing
 * @author Jeshurun and Binyamin
 */
public abstract class RayTracerBase {
    /**The scene to be rendered*/
    protected final Scene scene;
    /**
     * constructor with the scene
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color of the pixel
     * @param ray the ray to be traced
     * @return the color of the pixel
     */
    public abstract Color traceRay(Ray ray);
}
