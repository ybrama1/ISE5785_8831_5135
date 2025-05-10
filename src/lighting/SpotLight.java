package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    /*** The direction of the light */
    private final Vector direction;

    /**
     * Constructor for SpotLight
     * @param position the position of the light
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    @Override
    public Color getIntensity(Point p) {
        // Calculate the angle between the light direction and the vector from the light to the point
        double angle = direction.dotProduct(getL(p));
        if (angle <= 0) {
            return Color.BLACK;
        }
        // Scale the intensity by the angle
        return super.getIntensity(p).scale(angle);
    }
    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight)super.setKc(kC);
    }
    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight)super.setKl(kL);

    }
    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight)super.setKq(kQ);
    }
/**
     * Set the narrow beam of the light
     * @param a the angle of the narrow beam
     * @return this SpotLight object
     */
    public SpotLight setNarrowBeam(int a){
        // Set the narrow beam of the light
        // This is a placeholder implementation
        // In a real implementation, you would use the angle to calculate the intensity
        return this;
    }
}
