package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

import java.util.Random;

import static java.awt.Color.BLACK;
import static java.lang.Math.abs;

public class Try {
    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);

    @Test
    void colorfulArtExplosion() {
        Random rand = new Random();

        for (int i = 0; i < 70; i++) {
            // יצירת כדורים אקראיים
            double radius = 10 + rand.nextDouble() * 20;
            Point center = new Point(
                    rand.nextDouble() * 400 - 200,
                    rand.nextDouble() * 400 - 200,
                    -80 - rand.nextDouble() * 100
            );
            Color color = new Color(
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256)
            );
            scene.geometries.add(
                    new Sphere(radius, center)
                            .setEmission(color)
                            .setMaterial(new Material()
                                    .setKD(rand.nextDouble())
                                    .setKS(rand.nextDouble())
                                    .setShininess(20 + rand.nextInt(100))
                                    .setKT(rand.nextDouble() < 0.3 ? rand.nextDouble() : 0))
            );
        }

        for (int i = 0; i < 50; i++) {
            // יצירת קואורדינטות אקראיות לנקודת התחלה
            double x = rand.nextDouble() * 400 - 200;
            double y = rand.nextDouble() * 400 - 200;
            double z = -80 - rand.nextDouble() * 100;

            Point p1 = new Point(x, y, z);
            Point p2 = new Point(x + rand.nextDouble() * 60 - 30,
                    y + rand.nextDouble() * 60 - 30,
                    z + rand.nextDouble() * 30 - 15);
            Point p3 = new Point(x + rand.nextDouble() * 60 - 30,
                    y + rand.nextDouble() * 60 - 30,
                    z + rand.nextDouble() * 30 - 15);

            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            scene.geometries.add(
                    new Triangle(p1, p2, p3)
                            .setEmission(color)
                            .setMaterial(new Material()
                                    .setKD(rand.nextDouble())
                                    .setKS(rand.nextDouble())
                                    .setShininess(20 + rand.nextInt(100))
                                    .setKT(rand.nextDouble() < 0.2 ? rand.nextDouble() : 0))

            );
        }



        // תאורה
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)));
        scene.lights.add(new SpotLight(
                new Color(RED),
                new Point(100, 100, 100),
                new Vector(-1, -1, -2)
        ).setKl(1E-5).setKq(1E-7));

        // מצלמה
        cameraBuilder
                .setLocation(new Point(0, 0, 1000))
                .setDirection(Point.ZERO, Vector.AXIS_Y)
                .setVpDistance(1000).setVpSize(500, 500)
                .setResolution(800, 800)
                .build()
                .renderImage()
                .writeToImage("colorfulArtExplosion");
    }

}
