package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    /**The name of the scene*/
    public String name;
    /**The background color of the scene*/
    public Color background = Color.BLACK;
    /**The ambient light of the scene*/
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**The geometries in the scene*/
    public Geometries geometries = new Geometries();

    /**
     * constructor with the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    public scene.Scene setName(String name) {
        this.name = name;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}
