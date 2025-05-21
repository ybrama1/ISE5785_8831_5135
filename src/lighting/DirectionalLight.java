package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    /**
     * The direction of the light
     */
    private final Vector direction;

    /**
     * Constructor for DirectionalLight
     *
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight( Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point point) {
        return direction;
    }
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
