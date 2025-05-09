package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    /**The name of the scene*/
    public String name;
    /**The background color of the scene*/
    public Color background = Color.BLACK;
    /**The ambient light of the scene*/
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**The geometries in the scene*/
    public Geometries geometries = new Geometries();
    /**The lights in the scene*/
    public List<LightSource> lights = new LinkedList<>();

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
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

}
