package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author Dan Zilberstein
 */
class ReflectionRefractionTests {
   /** Default constructor to satisfy JavaDoc generator */
   ReflectionRefractionTests() { /* to satisfy JavaDoc generator */ }

   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
      .setRayTracer(scene, RayTracerType.SIMPLE);

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   void twoSpheres() {
      scene.geometries.add( //
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)), //
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))); //
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      cameraBuilder
         .setLocation(new Point(0, 0, 1000)) //
         .setDirection(Point.ZERO, Vector.AXIS_Y) //
         .setVpDistance(1000).setVpSize(150, 150) //
         .setResolution(500, 500) //
         .build() //
         .renderImage() //
         .writeToImage("refractionTwoSpheres");
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   void twoSpheresOnMirrors() {
      scene.geometries.add( //
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20) //
                                 .setKT(new Double3(0.5, 0, 0))), //
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)), //
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKR(1)), //
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(26, 26, 26)));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      cameraBuilder
         .setLocation(new Point(0, 0, 10000)) //
         .setDirection(Point.ZERO, Vector.AXIS_Y) //
         .setVpDistance(10000).setVpSize(2500, 2500) //
         .setResolution(500, 500) //
         .build() //
         .renderImage() //
         .writeToImage("reflectionTwoSpheresMirrored");
   }

   /**
    * Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow
    */
   @Test
   void trianglesTransparentSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(38, 38, 38)));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setKl(4E-5).setKq(2E-7));

      cameraBuilder
         .setLocation(new Point(0, 0, 1000)) //
         .setDirection(Point.ZERO, Vector.AXIS_Y) //
         .setVpDistance(1000).setVpSize(200, 200) //
         .setResolution(600, 600) //
         .build() //
         .renderImage() //
         .writeToImage("refractionShadow");
   }
   @Test
   void colorfulShapesWithLights() {
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
              .writeToImage("colorfulShapesWithLights");
   }

   @Test
   void geometricElephantWithTransparencyAndReflection() {
      scene.geometries.add(
              // had
              new Sphere(50d, new Point(0, 0, -150))
                      .setEmission(new Color(100, 200, 200))
                      .setMaterial(new Material().setKD(0.4).setKS(0.5).setShininess(100).setKT(0.5)),

              // left ear
              new Triangle(new Point(-90, 20, -160), new Point(-60, 60, -160), new Point(-40, 10, -160))
                      .setEmission(new Color(250, 250, 0))
                      .setMaterial(new Material().setKR(0.6).setKD(0.2).setKS(0.4).setShininess(80)),

              // right ear
              new Triangle(new Point(90, 20, -160), new Point(60, 60, -160), new Point(40, 10, -160))
                      .setEmission(new Color(250, 250, 0))
                      .setMaterial(new Material().setKD(0.3).setKS(0.3).setShininess(50)),

              // nose
              new Sphere(12, new Point(0, -40, -100))
                      .setEmission(new Color(250, 120, 160))
                      .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(40)),
              new Sphere(10, new Point(0, -60, -150))
                      .setEmission(new Color(250, 120, 160))
                      .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(40)),
              new Sphere(8, new Point(0, -75, -150))
                      .setEmission(new Color(250, 120, 160))
                      .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(40)),

              // legs
              new Sphere(10, new Point(-30, -80, -150))
                      .setEmission(new Color(200, 70, 90))
                      .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(20)),
              new Sphere(10, new Point(30, -80, -150))
                      .setEmission(new Color(200, 70, 90))
                      .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(20)),
              new Sphere(10, new Point(-30, -95, -150))
                      .setEmission(new Color(200, 70, 90))
                      .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(20)),
              new Sphere(10, new Point(30, -95, -150))
                      .setEmission(new Color(200, 70, 90))
                      .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(20)),

              // left eye
              new Sphere(5, new Point(-15, 20, -100))
                      .setEmission(new Color(BLACK))
                      .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(150)),
              // right eye
              new Sphere(5, new Point(15, 20, -100))
                      .setEmission(new Color(BLACK))
                      .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(150))
      );

      scene.setAmbientLight(new AmbientLight(new Color(0, 0, 30)));

      scene.lights.add(
              new SpotLight(new Color(RED), new Point(70, 100, 50), new Vector(-1, -1, -2))
                      .setKl(1E-5).setKq(1E-7));
        scene.lights.add(
              new PointLight(new Color(GREEN), new Point(0, 200, 150))
                      .setKl(1E-5).setKq(1E-7));
        scene.lights.add(
              new DirectionalLight(new Color(WHITE), new Vector(-1, -1, -1)));


      cameraBuilder
              .setLocation(new Point(0, 0, 1000))
              .setDirection(Point.ZERO, Vector.AXIS_Y)
              .setVpDistance(1000).setVpSize(200, 200)
              .setResolution(600, 600)
              .build()
              .renderImage()
              .writeToImage("geometricElephant");
   }


}
