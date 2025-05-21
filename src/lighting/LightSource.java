package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
    /**
     * Get the intensity of the light at a given point
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    Color getIntensity(Point p);
    /**
     * Get the distance from the light source to a given point
     *
     * @param p the point to get the distance to
     * @return the distance from the light source to the given point
     */
    Vector getL(Point p);

    /**
     * Get the distance from the light source to a given point
     *
     * @param point the point to get the distance to
     * @return the distance from the light source to the given point
     */
    double getDistance(Point point);

}
