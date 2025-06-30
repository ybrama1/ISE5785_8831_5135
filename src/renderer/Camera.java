package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;
import scene.Scene;

import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.stream.*;

import static primitives.Util.isZero;

/**
 * Class Camera is the basic class representing a camera in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Camera implements Cloneable {
    /*** The camera's position in 3D space.*/
    private Point location;
    /*** The camera's orientation in 3D space.*/
    private Vector vup;
    /*** The camera's view direction in 3D space.*/
    private Vector vto;
    /*** The camera's right direction in 3D space.*/
    private Vector vright;
    /*** The distance from the camera to the view plane.*/
    private double distance = 0;
    /*** The width of the view plane.*/
    private double width = 0;
    /*** The height of the view plane.*/
    private double height = 0;
    /*** The image writer used to write the image to a file.*/
    ImageWriter imageWriter;
    /*** The ray tracer used to trace rays in the scene.*/
    RayTracerBase rayTracer;
    /*** The x resolution of the image.*/
    private int nX = 1;
    /*** The y resolution of the image.*/
    private int nY = 1;
    /*** define if uses anti aliasing or not */
    private boolean antiAliasing = false;
    /*** define how many rays to cast per pixel for super sampling */
    private int raysPerPixel = 25;
    /** Amount of threads to use fore rendering image by the camera */
    private int threadsCount = -1;
    /**
     * Amount of threads to spare for Java VM threads:<br>
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 2;
    /**
     * Debug print interval in seconds (for progress percentage)<br>
     * if it is zero - there is no progress output
     */
    private double printInterval = 1;
    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * </ul>
     */
    private PixelManager pixelManager;

    /**
     * default constructor
     */
    private Camera(){}


    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Render image using multi-threading by parallel streaming
     * @return the camera object itself
     */
    private Camera renderImageStream() {
        IntStream.range(0, nY).parallel()
                .forEach(i -> IntStream.range(0, nX).parallel()
                        .forEach(j -> colorPixel(j, i)));
        return this;
    }
    /**
     * Render image without multi-threading
     * @return the camera object itself
     */
    private Camera renderImageNoThreads() {
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                colorPixel(j, i);
        return this;
    }
    /**
     * Render image using multi-threading by creating and running raw threads
     * @return the camera object itself
     */
    private Camera renderImageRawThreads() {
        var threads = new LinkedList<Thread>();
        while (threadsCount-- > 0)
            threads.add(new Thread(() -> {
                PixelManager.Pixel pixel;
                while ((pixel = pixelManager.nextPixel()) != null)
                    colorPixel(pixel.col(), pixel.row());
            }));
        for (var thread : threads) thread.start();
        try {
            for (var thread : threads) thread.join();
        } catch (InterruptedException ignored) {}
        return this;
    }

    /**
     * Constructs a ray from the camera through a pixel on the view plane.
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the pixel's column index
     * @param i  the pixel's row index
     * @param shiftX the horizontal shift from the pixel center
     * @param shiftY the vertical shift from the pixel center
     * @return a ray from the camera through the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i, double shiftX, double shiftY){
        // check if the pixel coordinates are valid
        if(nX <= 0 || nY <= 0)
            throw new IllegalArgumentException("Invalid pixel coordinates");

        Point pIJ = location;

        // calculate the pixel size
        double yI = -(i - (nY - 1) / 2d) * height / nY;
        double xJ = (j - (nX - 1) / 2d) * width / nX;

        // calculate the point on the view plane the pixel and the shift
        if (!isZero(xJ+shiftX)) pIJ = pIJ.add(vright.scale(xJ+shiftX));
        if (!isZero(yI+shiftY)) pIJ = pIJ.add(vup.scale(yI+shiftY));

        // we need to move the point in the direction of vTo by distance
        pIJ = pIJ.add(vto.scale(distance));

        Vector vIJ = pIJ.subtract(location);
        return new Ray(location, vIJ);
    }

    /** This function renders image's pixel color map from the scene
     * included in the ray tracer object
     * @return the camera object itself
     */
    public Camera renderImage() {
        pixelManager = new PixelManager(nY, nX, printInterval);
        return switch (threadsCount) {
            case 0 -> renderImageNoThreads();
            case -1 -> renderImageStream();
            default -> renderImageRawThreads();
        };
    }
    
    /**
     * Prints a grid on the image.
     * @param interval the interval between grid lines
     * @param color the color of the grid lines
     * @return the camera object
     */
    public Camera printGrid(int interval,Color color){
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;
    }


    /**
     * Colors a specific pixel in the image, optionally, uses anti-aliasing.
     * @param i the row index of the pixel
     * @param j the column index of the pixel
     */
    public void colorPixel(int i, int j){
        // get the ray from the camera
        Ray ray = constructRay(this.nX, this.nY, i, j, 0.5, 0.5);
        // get the color of the pixel
        Color color = rayTracer.traceRay(ray);

        // if anti-aliasing is not enabled, write the pixel color directly
        if(!this.antiAliasing) {
            imageWriter.writePixel(i, j, color);
            pixelManager.pixelDone();
            return;
        }

        //anti-aliasing: super sampling
        for(int k = 1; k < raysPerPixel; k++) {
            // get the ray from the camera with random shift
            double shiftX = Math.random() - 0.5;
            double shiftY = Math.random() - 0.5;
            Ray antiAliasingRay = constructRay(this.nX, this.nY, i, j, shiftX, shiftY);
            // get the color of the pixel
            Color antiAliasingColor = rayTracer.traceRay(antiAliasingRay);
            // add the color to the pixel
            color = color.add(antiAliasingColor);
        }

        // average the color
        color = color.scale((double) 1 / raysPerPixel);

        // set the color of the pixel
        imageWriter.writePixel(i, j, color);
        pixelManager.pixelDone();
    }

    /**
     * Writes the image to a file.
     * @param filePath the path to the file
     */
    public void writeToImage(String filePath) {
        imageWriter.writeToImage(filePath);
    }
    /**
     * Builder class for constructing a Camera object.
     * This class provides methods to set the camera's location, view direction,
     * view plane size, and distance from the view plane.
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Sets the camera's location.
         *
         * @param location the camera's location
         * @return the builder instance
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * Sets the camera's view direction.
         *
         * @param vto the camera's view direction
         * @param vup the camera's up direction
         * @return the builder instance
         */
        public Builder setDirection(Vector vto, Vector vup) {
            if (vto.dotProduct(vup) != 0) {
                throw new IllegalArgumentException(
                        "The view direction and up direction must be orthogonal");
            }
            camera.vto = vto.normalize();
            camera.vup = vup.normalize();
            camera.vright = vto.crossProduct(vup).normalize();
            return this;
        }

        /**
         * Sets the camera's view direction.
         *
         * @param target the target point
         * @param up     the up direction
         * @return the builder instance
         */
        public Builder setDirection(Point target, Vector up) {
            camera.vto = target.subtract(camera.location).normalize();
            camera.vright = camera.vto.crossProduct(up).normalize();
            camera.vup = camera.vright.crossProduct(camera.vto).normalize();
            return this;
        }

        /**
         * Sets the camera's view direction. the up direction is the default up direction (0,1,0)
         *
         * @param target the target point
         * @return the builder instance
         */
        public Builder setDirection(Point target) {
            camera.vto = target.subtract(camera.location).normalize();
            camera.vup = Vector.AXIS_Y;
            camera.vright = camera.vto.crossProduct(camera.vup).normalize();
            camera.vup = camera.vright.crossProduct(camera.vto).normalize();
            return this;
        }

        /**
         * Sets the camera's view plane size.
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the builder instance
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the camera's distance from the view plane.
         *
         * @param distance the distance from the camera to the view plane
         * @return the builder instance
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the camera's resolution.
         *
         * @param nX the number of pixels in the x direction
         * @param nY the number of pixels in the y direction
         * @return copy of the builder instance
         */
        public Builder setResolution(int nX, int nY) {
            camera.nX = nX;
            camera.nY = nY;
            return this;
        }

        public Builder setRayTracer(Scene scene, RayTracerType rayTracerType) {
            // meanwhile, we will do it just for simple type
            if (rayTracerType == RayTracerType.SIMPLE) {
                camera.rayTracer = new SimpleRayTracer(scene);
            } else {
                camera.rayTracer = null;
            }
            return this;
        }

        public Builder setAntiAliasing(boolean antiAliasing) {
            camera.antiAliasing = antiAliasing;
            return this;
        }

        /**
         * Set multi-threading <br>
         * Parameter value meaning:
         * <ul>
         * <li>-2 - number of threads is number of logical processors less 2</li>
         * <li>-1 - stream processing parallelization (implicit multi-threading) is used</li>
         * <li>0 - multi-threading is not activated</li>
         * <li>1 and more - literally number of threads</li>
         * </ul>
         * @param threads number of threads
         * @return builder object itself
         */
        public Builder setMultithreading(int threads) {
            if (threads < -3)
                throw new IllegalArgumentException("Multithreading parameter must be -2 or higher");
            if (threads == -2) {
                int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            } else
                camera.threadsCount = threads;
            return this;
        }
        /**
         * Set debug printing interval. If it's zero - there won't be printing at all
         * @param interval printing interval in %
         * @return builder object itself
         */
        public Builder setDebugPrint(double interval) {
            if (interval < 0) throw new IllegalArgumentException("interval parameter must be non-negative");
            camera.printInterval = interval;
            return this;
        }

        public Builder setRaysPerPixel(int raysPerPixel) {
            if (raysPerPixel <= 0) {
                throw new IllegalArgumentException("Rays per pixel must be positive");
            }
            camera.raysPerPixel = raysPerPixel;
            return this;
        }

        public Camera build() {
            String errorMessage = "Renderer parameters are missing";
            if (camera.location == null) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "location");
            }
            if (camera.vto == null) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "vto");
            }
            if (camera.vup == null) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "vup");
            }
            if (isZero(camera.vup.lengthSquared())) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "width");
            }
            if (isZero(camera.height)) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "height");
            }
            if (isZero(camera.distance)) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "distance");
            }
            if (isZero(camera.width)) {
                throw new MissingResourceException(
                        errorMessage,
                        "Camera",
                        "width");
            }
            if (camera.height <= 0) {
                throw new IllegalArgumentException("Height must be positive");
            }
            if (camera.width <= 0) {
                throw new IllegalArgumentException("Width must be positive");
            }
            if (camera.distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }

            if (camera.nX <= 0) {
                throw new IllegalArgumentException("nX must be positive");
            }
            if (camera.nY <= 0) {
                throw new IllegalArgumentException("nY must be positive");
            }
            if (camera.rayTracer == null) {
                camera.rayTracer = new SimpleRayTracer(null);
            }
            camera.vright = camera.vto.crossProduct(camera.vup).normalize();
            camera.imageWriter = new ImageWriter(camera.nX, camera.nY);
            try {
                return (Camera)camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}