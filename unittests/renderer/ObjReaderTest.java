package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class ObjReaderTest {

    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);

    @Test
    public void testReadVerticesFromOBJ() {
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (2)\\tinker.obj"; // Replace with your actual file path
        List<Point> vertices = objReader.readVerticesFromOBJ(filePath);

        // Print the vertices to verify
        for (Point vertex : vertices) {
            System.out.println(vertex);
        }

        // You can add assertions here to check if the vertices are read correctly
    }

    @Test
    public void testReadPolygonsFromOBJ() {
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (2)\\tinker.obj"; // Replace with your actual file path
        List<Geometry> polygons = objReader.readPolygonsFromOBJ(filePath);

        // Print the polygons to verify
        for (Geometry polygon : polygons) {
            System.out.println(polygon);
        }

        // You can add assertions here to check if the polygons are read correctly
    }


    @Test
    public void testReadMaterialsFromMtl() {
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (2)\\obj.mtl"; // Replace with your actual file path
        var materials = objReader.getMaterialsFromMtl(filePath);

        // Print the materials to verify
        for (var entry : materials.entrySet()) {
            System.out.println("Material Name: " + entry.getKey() + ", Material: " + entry.getValue());
        }

        // You can add assertions here to check if the materials are read correctly
    }

    //create an image of the obj file
    @Test
    public void testCreateImageFromOBJ() {
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (2)\\tinker.obj"; // Replace with your actual file path
        List<Geometry> polygons = objReader.readPolygonsFromOBJ(filePath);

        scene.geometries.add(polygons);
        // lights
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)));
        Point SLL = new Point(0, 100, 100);
        scene.lights.add(new SpotLight(
                new Color(WHITE),
                SLL,
                Point.ZERO.subtract(SLL)
                )
        );

        // camera
        Point CL = new Point(50, -100, 200);
        cameraBuilder
                .setLocation(CL)
                .setDirection(Point.ZERO, Vector.AXIS_Y)
                .setVpDistance(1000).setVpSize(500, 500)
                .setResolution(800, 800)
                .build()
                .renderImage()
                .writeToImage("objImage");
    }

    @Test
    public void OBJReader(){
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (3)\\tinker.obj"; // Replace with your actual file path
        Geometries obj = objReader.getGeometryFromOBJ(filePath);
        System.out.println("finished extracting");
        scene.geometries.add(obj);
        // lights
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)));
        Point SLL = new Point(0, 100, 100);
        scene.lights.add(new SpotLight(
                new Color(WHITE),
                SLL,
                Point.ZERO.subtract(SLL)
                )
        );

        // camera
        Point CL = new Point(50, -100, 200);
        cameraBuilder
                .setLocation(CL)
                .setDirection(Point.ZERO, Vector.AXIS_Y)
                .setVpDistance(1000).setVpSize(500, 500)
                .setResolution(800, 800)
                .build()
                .renderImage()
                .writeToImage("objImage-elephant");
    }

    @Test
    public void lowPolyConvertTest(){
        ObjReader objReader = new ObjReader();
        String filePath1 = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (5)\\color_1206582.obj"; // Replace with your actual file path
        String filePath2 = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (5)\\color_2829873.obj"; // Replace with your actual file path
        String filePath3 = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (5)\\color_4634441.obj"; // Replace with your actual file path
        Geometries obj1 = objReader.getGeometryFromOBJ(filePath1);
        Geometries obj2 = objReader.getGeometryFromOBJ(filePath2);
        Geometries obj3 = objReader.getGeometryFromOBJ(filePath3);
        System.out.println("finished extracting");
        scene.geometries.add(obj1);
        scene.geometries.add(obj2);
        scene.geometries.add(obj3);
        // lights
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)));
        Point SLL = new Point(0, 100, 100);
        scene.lights.add(new SpotLight(
                new Color(WHITE),
                SLL,
                Point.ZERO.subtract(SLL)
                )
        );
        // camera
        Point CL = new Point(50, -100, 200);
        cameraBuilder
                .setLocation(CL)
                .setDirection(new Point(80,-40,20), Vector.AXIS_Y)
                .setVpDistance(1000).setVpSize(500, 500)
                .setResolution(800, 800)
                .build()
                .renderImage()
                .writeToImage("objImage-lowPoly");
    }

    @Test
    public void lowPolyConvertTest2(){
        ObjReader objReader = new ObjReader();
        String filePath = "C:\\Users\\binyamin\\Downloads\\Exquisite Amur (6)\\united.obj";
        Geometries obj = objReader.getGeometryFromOBJ(filePath);
        scene.geometries.add(obj);
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
                .build()
                .renderImage()
                .writeToImage("objImage-lowPoly2");
    }

    @Test
    public void DiscWorld(){
        ObjReader objReader = new ObjReader();
        String filePath = "OBJfiles\\united.obj";
        Geometries obj = objReader.getGeometryFromOBJ(filePath);
        obj.BVH();
        scene.geometries.add(obj);
        //add spheres in distance like stars
        Geometries spheres = new Geometries();
        for (int i = 0; i < 300; i++) {
            int offset = 20000;
            double x = Math.random() * offset * 2 - (double) offset /2 * 2;
            double y = Math.random() * offset - (double) offset /2;
            double z = Math.random() * offset - (double) offset /2;
            spheres.add(new Sphere(
                    50,
                    new Point(x, y, z).add(new Vector(-110,214,-110).scale(170))
            ).setEmission(new Color(255,255,255)));
        }
        spheres.BVH();
        scene.geometries.add(spheres);
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
        Point SLL2 = new Point(0, 100, 150);
        scene.lights.add(new SpotLight(
                new Color(WHITE),
                SLL2,
                Point.ZERO.subtract(SLL2)
        ));
        Point SLL3 = new Point(0, -100, 200);
        scene.lights.add(new SpotLight(
                new Color(YELLOW),
                SLL3,
                Point.ZERO.subtract(SLL3)
        ));
        // camera
        Point CL = new Point(120, -250,  150);
        cameraBuilder
                .setLocation(CL)
                .setDirection(new Point(10,-36,40), new Vector(0,0,1))
                .setVpDistance(1000).setVpSize(900, 500)
                .setResolution(1980, 1080)
                .setMultithreading(-1)
                .setAntiAliasing(true)
                .build()
                .renderImage()
                .writeToImage("DiscWorld");
    }
}
