package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable. Intersection;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static primitives.Util.alignZero;


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

    public Color calcColor(Intersection intersection, Ray ray) {
        if(preprocessIntersection(intersection, ray.getDir())) {
            return scene.ambientLight.getIntensity()
                    .add(calcColorLocalEffects(intersection));
        }
        return Color.BLACK;
    }

    public boolean preprocessIntersection(Intersection intersection, Vector intersectionRay){
        intersection.v = intersectionRay;
        intersection.normal = intersection.geometry.getNormal(intersection.point);
        intersection.nv = alignZero(intersection.v
                .dotProduct(intersection.normal));
        return intersection.nv != 0;
    }
    public boolean setLightSource(Intersection intersection, LightSource lightSource){
        intersection.light = lightSource;
        intersection.l = lightSource.getL(intersection.point);
        intersection.ln = alignZero(intersection.l
                .dotProduct(intersection.normal));
        return intersection.ln * intersection.nv >= 0;
    }
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
    Double3 calcSpecular(Intersection intersection){
        // Calculate the specular color based on the material properties and the light source
        Vector r = intersection.v
                .subtract(intersection.normal.scale(intersection.v.dotProduct(intersection.normal)).scale(2));
        return intersection.geometry.getMaterial().kS
                .scale(
                        Math.pow(
                                max(
                                        0,
                                        -intersection.v.dotProduct(r)),
                                intersection.material.nSh));
    }
    Double3 calcDiffusive(Intersection intersection){
        // Calculate the diffusive color based on the material properties and the light source
        return intersection.geometry.getMaterial().kD.scale(abs(intersection.ln));
    }


}
