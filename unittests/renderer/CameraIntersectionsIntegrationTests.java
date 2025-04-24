package renderer;


import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** * CameraIntersectionsIntegrationTests is a class that contains integration tests for the Camera class.
 * It is part of the renderer package and is used to test the functionality of the Camera class.
 * test the camera's ability to find intersections with geometries in the scene.
 * @author Jeshurun and Binyamin
 */
public class CameraIntersectionsIntegrationTests {
    // in this class there will be 3 methods: for sphere, plane and triangle
    // dont need to separate the tests for EP and BVT
    // every check will make view plane in size 3x3, 3x3 pixels, calculate the intersections with the geometries and sum the intersections
    @Test
    void twstSphere() {
        // TC01: Test for a sphere with a camera
        // the result should be 2 intersections
        Sphere sphere = new Sphere(1, new Point(1, 0, 0));

    }

}
