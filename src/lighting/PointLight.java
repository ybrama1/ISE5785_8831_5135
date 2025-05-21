package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    /*** The position of the light source*/
    private final Point position;
    /*** The constant attenuation factor*/
    private double kC = 1;
    /*** The linear attenuation factor*/
    private double kL = 0;
    /*** The quadratic attenuation factor*/
    private double kQ = 0;

    /**
     * Constructor for PointLight
     * @param intensity the intensity of the light source
     * @param position the position of the light source
     */
    public PointLight(Color intensity, Point position ) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        double d = point.distance(position);
        return getIntensity().scale(1 / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
