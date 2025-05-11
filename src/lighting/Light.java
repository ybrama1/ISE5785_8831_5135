package lighting;

import primitives.Color;

abstract class Light {
    /*** The intensity of the light*/
    protected final Color intensity;

    /**
     * Constructor for Light
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
