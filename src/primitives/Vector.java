package primitives;
import primitives.Point;

/**
 * Class Vector is the basic class representing a vector in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Vector extends Point {

    /**
     * The beginning of labor
     *
     * @param x first coordinate
     * @param y second coordinate
     * @param z third coordinate
     */
    public Vector(double x, double y, double z) {
        if (new Double3(x, y, z).equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be (0,0,0)");
        }
        super(x, y, z);
    }

    /**
     * constractor create the vector xyz
     *
     * @param xyz 3 coordinates
     */
    public Vector(Double3 xyz) {
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be (0,0,0)");
        }
        super(xyz);
    }

    /**
     * add vector to the vector
     *
     * @param addend the vector
     * @return new vector
     */
    public Vector add(Vector addend) {
        return new Vector(this.xyz.add(addend.xyz));
    }

    /**
     * scalar multiplication
     * @param scalar scalar we multiply the vector by
     * @return new vector
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

}
