package geometries;

import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ObjReader;
import renderer.RayTracerType;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class BVHNodeTest {

    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);


    /**
     * Test for getBoundingBox method
     * @link geometries.BVHNode#getBoundingBox()
     */
    @Test
    public void getBoundingBoxTest() {
        Geometries geometries = new Geometries();
        geometries.add(new Sphere(1, new Point(0, 0, 0)));
        geometries.add(new Sphere(2, new Point(3, 4, 5)));
        geometries.add(new Triangle(new Point(-1, -1, -1), new Point(1, 0, 1), new Point(2, 2, 2)));

        BVHNode bvhNode = new BVHNode(geometries);
        AABB boundingBox = bvhNode.getBoundingBox();

        // Check if the bounding box is correct
        assert boundingBox.getMin().equals(new Point(-1, -1, -1));
        assert boundingBox.getMax().equals(new Point(5, 6, 7));
    }


    @Test
    public void lowPolyConvertTest1() {
        List<Geometry> triangles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double x = i;
            double y = i;
            double z = -i;
            Geometry t1 = new Triangle(
                    new Point(x, y, z),
                    new Point(x + 1, y + 1, z + 1),
                    new Point(x + 2, y, z + 2)
            ).setEmission(new Color(100, 100, 100));
            triangles.add(t1);
        }
        scene.geometries = new Geometries(triangles);
        scene.geometries.BVH();
        // lights
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)));
        Point SLL = new Point(30, -300, 60);
        scene.lights.add(new PointLight(
                        new Color(WHITE),
                        SLL
                ).setKc(0.9)
                        .setKl(1E-4)
        );
        // camera
        Point CL = new Point(0, 10 , 0);
        cameraBuilder
                .setLocation(CL)
                .setDirection(new Point(0, 0, 0), new Vector(0, 0, -1))
                .setVpDistance(5).setVpSize(500, 500)
                .setResolution(800, 800)
                .build()
                .renderImage()
                .writeToImage("objImage-lowPoly1-BVH");

    }

    @Test
    void BVHcolorfulShapesWithLights() {
        scene.geometries.add(
                new Sphere(40d, new Point(0, 0, -100))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.3).setKS(0.5).setShininess(80).setKT(0.5)),

                new Sphere(30d, new Point(-70, 40, -120))
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKD(0.4).setKS(0.7).setShininess(100)),

                new Sphere(25d, new Point(60, -50, -90))
                        .setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKD(0.7).setKS(0.1).setShininess(10)),

                new Triangle(new Point(-60, -60, -140), new Point(-20, 0, -140), new Point(-80, 20, -140))
                        .setEmission(new Color(255, 215, 0))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),

                new Triangle(new Point(20, -40, -130), new Point(80, -30, -130), new Point(50, 30, -130))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKD(0.3).setKS(0.8).setShininess(120)),

                new Triangle(new Point(-30, 60, -110), new Point(10, 90, -110), new Point(-10, 120, -110))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKD(0.2).setKS(0.3).setShininess(40).setKT(0.3))
        );

        // BVH optimization
        scene.geometries.BVH();

        scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20)));

        scene.lights.add(
                new SpotLight(new Color(500, 300, 300), new Point(50, 50, 50), new Vector(-1, -1, -2))
                        .setKl(1E-5).setKq(1E-7)
        );

        cameraBuilder
                .setLocation(new Point(0, 0, 1000))
                .setDirection(Point.ZERO, Vector.AXIS_Y)
                .setVpDistance(1000).setVpSize(200, 200)
                .setResolution(600, 600)
                .build()
                .renderImage()
                .writeToImage("colorfulShapesWithLights-BVH");
    }

    @Test
    public void lowPolyConvertTest2(){
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (6)\\united.obj";
        Geometries obj = objReader.getGeometryFromOBJ(filePath);
        scene.geometries.add(obj.geometries);
        scene.geometries.BVH();
        // lights
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)));
        Point SLL = new Point(30, -300, 60);
        scene.lights.add(new PointLight(
                        new Color(WHITE),
                        SLL
                ).setKc(0.9)
                        .setKl(1E-4)
        );
        // camera
        Point CL = new Point(40, -260,  40);
        cameraBuilder
                .setLocation(CL)
                .setDirection(new Point(10,-36,20), new Vector(0,0,1))
                .setVpDistance(1000).setVpSize(500, 500)
                .setResolution(800, 800)
                .setAntiAliasing(true)
                .setMultithreading(-1)
                .build()
                .renderImage()
                .writeToImage("objImage-lowPoly2-BVH");
    }
}
