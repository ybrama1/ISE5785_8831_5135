package lighting;

import primitives.Color;

/**
 * Class AmbientLight is the class representing the ambient light in a scene.
 * It is a type of light that is present in the scene and does not have a specific source.
 * @author Jeshurun and Binyamin
 */
public class AmbientLight extends Light {
    /**The beginning of labor*/
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK);
    /**
     * constructor with parameters
     * @param La the intensity of the ambient light
     */
    public AmbientLight(Color La) {
        super(La);
    }

}
