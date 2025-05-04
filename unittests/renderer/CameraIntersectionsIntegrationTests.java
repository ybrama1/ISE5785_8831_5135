package renderer;


import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
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
    /** Camera builder for the tests */
    // camera constants
    private final double CAMERA_DISTANCE = 2;
    private final Point CAMERA_LOCATION = Point.ZERO;
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(CAMERA_LOCATION)
            .setVpDistance(CAMERA_DISTANCE);
    /** Assert failure message for a bad ray */
    private final String BAD_RAY = "Bad ray";
    // set the camera direction
    private final Vector to = new Vector(0, 1, 0);
    private final Vector up = new Vector(0, 0, 1);
    // set the camera size
    private final double width = 6;
    private final double height = 6;
    // set the resolution
    private final int nX = 3;
    private final int nY = 3;
    // set the camera
    private final Camera camera = cameraBuilder
            .setDirection(to, up)
            .setVpSize(width, height)
            .setResolution(nX, nY)
            .build();
//    // the location of the pixels on the view plane
//    private final Point[][] pixels = {
//            {new Point(-2, 2, 2), new Point(0, 2, 2), new Point(2, 2, 2)},
//            {new Point(-2, 2, 0), new Point(0, 2, 0), new Point(2, 2, 0)},
//            {new Point(-2, 2, -2), new Point(0, 2, -2), new Point(2, 2, -2)}
//    };

    /**
     * Test method for constructing rays and checking intersections with a sphere.
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testIntersectionsSphere() {
        //test for sphere
        //there should be 4 intersections
        Sphere sphere = new Sphere(3, new Point(1, 4, 1));
        // construct a ray through each pixel and check intersections
        int intersectionCount = 0;
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                // construct a ray through the pixel
                Ray ray = camera.constructRay(nX, nY, i, j);
                // check if the ray intersects with the sphere
                if (sphere.findIntersections(ray).size() > 0) {
                    intersectionCount++;
                }
            }
        }
        // check if the number of intersections is equal to 4
        assertEquals(4, intersectionCount, "The number of intersections is not equal to 4");
    }
    /**
     * Test method for constructing rays and checking intersections with a plane.
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testIntersectionsPlane() {
        //test for plane
        //there should be 9 intersections
        Plane plane = new Plane(new Point(0, 6, 0), new Point(1,6,0), new Point(0,7,-1));
        // construct a ray through each pixel and check intersections
        //There should be 6 intersections
        int intersectionCount = 0;
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                // construct a ray through the pixel
                Ray ray = camera.constructRay(nX, nY, i, j);
                // check if the ray intersects with the plane
                if (plane.findIntersections(ray).size() > 0) {
                    intersectionCount++;
                }
            }
        }
        // check if the number of intersections is equal to 6
        assertEquals(6, intersectionCount, "The number of intersections is not equal to 9");
    }

    /**
     * Test method for constructing rays and checking intersections with a triangle.
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testIntersectionsTriangle() {
        //test for triangle
        //there should be 9 intersections
        Triangle triangle = new Triangle(new Point(4, 3, 4), new Point(-4, 6, 4), new Point(0, 3, -5));
        // construct a ray through each pixel and check intersections
        //There should be 4 intersections
        int intersectionCount = 0;
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                // construct a ray through the pixel
                Ray ray = camera.constructRay(nX, nY, i, j);
                // check if the ray intersects with the triangle
                if (triangle.findIntersections(ray).size() > 0) {
                    intersectionCount++;
                }
            }
        }
        // check if the number of intersections is equal to 4
        assertEquals(4, intersectionCount, "The number of intersections is not equal to 9");
    }


}
