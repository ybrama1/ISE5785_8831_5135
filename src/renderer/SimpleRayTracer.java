package renderer;


import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable. Intersection;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static primitives.Util.alignZero;

/**
 * SimpleRayTracer class that extends RayTracerBase
 * This class is responsible for rendering a scene using ray tracing
 * It calculates the color of each pixel based on the light sources and the geometry in the scene

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
        var intersections = scene.geometries.calculateIntersections(ray);
        return intersections == null
                ? scene.background
                : calcColor(ray.findClosestIntersection(intersections), ray);
    }
    /**
     * Calculate the color of the intersection point
     * @param intersection the intersection point
     * @param ray the ray that created the intersection
     * @return the color of the intersection point
     */
    public Color calcColor(Intersection intersection, Ray ray) {
        if(preprocessIntersection(intersection, ray.getDir())) {
            return scene.ambientLight.getIntensity()
                    .scale(intersection.geometry.getMaterial().kA)
                    .add(calcColorLocalEffects(intersection));
        }
        return Color.BLACK;
    }
    /**
     * Preprocess the intersection point
     * @param intersection the intersection point
     * @param intersectionRay the ray that created the intersection
     * @return true if the intersection is valid, false otherwise
     */
    public boolean preprocessIntersection(Intersection intersection, Vector intersectionRay){
        intersection.v = intersectionRay;
        intersection.normal = intersection.geometry.getNormal(intersection.point);
        intersection.nv = alignZero(intersection.v
                .dotProduct(intersection.normal));
        return intersection.nv != 0;
    }
    /**
     * Set the light source for the intersection
     * @param intersection the intersection point
     * @param lightSource the light source
     * @return true if the light source is set successfully, false otherwise
     */
    public boolean setLightSource(Intersection intersection, LightSource lightSource){
        intersection.light = lightSource;
        intersection.l = lightSource.getL(intersection.point);
        intersection.ln = alignZero(intersection.l
                .dotProduct(intersection.normal));
        return intersection.ln * intersection.nv >= 0;
    }
    /**
     * Calculate the color of the local effects based on the material properties and the light source
     * @param intersection the intersection point
     * @return the color of the local effects
     */
    Color calcColorLocalEffects(Intersection intersection){
        Color color = intersection.geometry.getEmission();

        for (LightSource lightSource : scene.lights) {
            if (setLightSource(intersection, lightSource)) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(intersection.point);
                color = color.add(
                        iL.scale(calcDiffusive(intersection)
                                .add(calcSpecular(intersection))));
            }
        }
        return color;
    }
    /**
     * Calculate the specular color based on the material properties and the light source
     * @param intersection the intersection point
     * @return the specular color
     */
    Double3 calcSpecular(Intersection intersection){
        Vector r = intersection.l
                .subtract(intersection.normal.scale(2 * intersection.ln));
        return intersection.geometry.getMaterial().kS
                .scale(
                        Math.pow(
                                max(
                                        0,
                                        -intersection.v.dotProduct(r)),
                                intersection.material.nSh));
    }
    /**
     * Calculate the diffusive color based on the material properties and the light source
     * @param intersection the intersection point
     * @return the diffusive color
     */
    Double3 calcDiffusive(Intersection intersection){
        return intersection.geometry.getMaterial().kD.scale(abs(intersection.ln));
    }
}