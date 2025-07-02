package renderer;


import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable. Intersection;

import java.util.List;

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
    /*** The maximum number of color levels used for shading*/
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /*** The minimum color level used for shading*/
    private static final double MIN_CALC_COLOR_K = 0.001;
    /*** The initial color value used for shading*/
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructor for SimpleRayTracer
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Intersection intersection = findClosestIntersection(ray);
        return intersection == null
                ? scene.background
                : calcColor(intersection, ray);
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
                    .add(calcColor(intersection, MAX_CALC_COLOR_LEVEL, INITIAL_K));
        }
        return Color.BLACK;
    }
    private Color calcColor(Intersection intersection, int level, Double3 k){
        Color color = calcColorLocalEffects(intersection, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, level, k));
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
        intersection.nv = alignZero(
                intersection.v
                .dotProduct(intersection.normal)
        );
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
    Color calcColorLocalEffects(Intersection intersection, Double3 k){
        Color color = intersection.geometry.getEmission();

        for (LightSource lightSource : scene.lights) {
            if (setLightSource(intersection, lightSource)){ // sign(nl) == sign(nv)
                Double3 ktr = transparency(intersection); // check if the intersection is unshaded
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(intersection)
                                    .add(calcSpecular(intersection))));
                }
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

    /**
     * Check if the intersection point is unshaded
     * @param intersection the intersection point
     * @return true if the intersection point is unshaded, false otherwise
     */
    private boolean unshaded(Intersection intersection){
        Vector pointToLight = intersection.l.scale(-1); // from point to light source
        Ray shadowRay = new Ray(intersection.point, pointToLight, intersection.normal);

        var intersections = scene.geometries.calculateIntersections(shadowRay);
        if (intersections == null) {
            return true;
        }
        for (Intersection i : intersections) {
            if (i.point.distance(shadowRay.getP0()) < intersection.light.getDistance(shadowRay.getP0())) {
                return !i.material.kT.lowerThan(MIN_CALC_COLOR_K); // if the intersection is transparent, we can ignore it
            }
        }
        return true;
    }
    /**
     * Calculate the reflected ray based on the intersection point
     * @param intersection the intersection point
     * @return the reflected ray
     */
    private Ray calcReflectedRay(Intersection intersection){
        Vector v = intersection.v;
        Vector n = intersection.normal;
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));

        return new Ray(intersection.point, r, intersection.normal);
    }
    /**
     * Calculate the refracted ray based on the intersection point
     * @param intersection the intersection point
     * @return the refracted ray
     */
    private Ray calcTransparentRay(Intersection intersection){
        return new Ray(intersection.point, intersection.v, intersection.normal);
    }

    /**
     * Calculate the global effect of the ray based on the intersection point
     * @param ray the ray to be traced
     * @param level the level of recursion
     * @param k the color of the ray
     * @param kx the color of the intersection point
     * @return the color of the global effect
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        Intersection intersection = findClosestIntersection(ray);
        if (intersection == null) return scene.background.scale(kx);
        return preprocessIntersection(intersection, ray.getDir())
                ? calcColor(intersection, level - 1, kkx).scale(kx)
                : Color.BLACK;
    }
    /**
     * Calculate the global effects of the ray based on the intersection point
     * @param intersection the intersection point
     * @param level the level of recursion
     * @param k the color of the ray
     * @return the color of the global effects
     */
    private Color calcGlobalEffects(Intersection intersection, int level, Double3 k) {
        return calcGlobalEffect(
                calcTransparentRay(intersection),
                level,
                k,
                intersection.material.kT
        )
                .add(
                    calcGlobalEffect(
                            calcReflectedRay(intersection),
                            level,
                            k,
                            intersection.material.kR
                    ));
    }
    /**
     * Find the closest intersection point of the ray with the scene
     * @param ray the ray to be traced
     * @return the closest intersection point
     */
    private Intersection findClosestIntersection(Ray ray){
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            return null;
        }
        return ray.findClosestIntersection(intersections);
    }
    private Double3 transparency(Intersection intersection){
        Vector pointToLight = intersection.l.scale(-1); // from point to light source
        Ray shadowRay = new Ray(intersection.point, pointToLight, intersection.normal);

        Double3 ktr = Double3.ONE;
        var intersections = scene.geometries.calculateIntersections(shadowRay);
        if (intersections != null) {
            for (Intersection i : intersections) {
                if (i.point.distance(shadowRay.getP0()) < intersection.light.getDistance(shadowRay.getP0())) {
                    ktr = ktr.product(i.material.kT); // if the intersection is transparent, we can ignore it
                }
            }
        }
        return ktr;
    }

}