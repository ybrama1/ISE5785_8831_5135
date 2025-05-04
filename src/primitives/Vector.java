package primitives;
import primitives.Point;

/**
 * Class Vector is the basic class representing a vector in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Vector extends Point {

    /**3 coordinates of vector*/
    public static final Vector AXIS_X = new Vector(1, 0, 0);
    public static final Vector AXIS_Y = new Vector(0, 1, 0);
    public static final Vector AXIS_Z = new Vector(0, 0, 1);
    /**
     * The beginning of labor
     *
     * @param x first coordinate
     * @param y second coordinate
     * @param z third coordinate
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        validation();

    }

    /**
     * constractor create the vector xyz
     *
     * @param xyz 3 coordinates
     */
    public Vector(Double3 xyz) {
        super(xyz);
        validation();
    }

    /**
     * add vector to the vector
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

    /**
     * dot product
     * @param other vector
     * @return dot product
     */
    public double dotProduct(Vector other) {
        return this.xyz.d1() * other.xyz.d1() + this.xyz.d2() * other.xyz.d2() + this.xyz.d3() * other.xyz.d3();
    }

    /**
     * cross product
     * @param other vector
     * @return cross product
     */
    public Vector crossProduct(Vector other) {
        return new Vector(
                this.xyz.d2() * other.xyz.d3() - this.xyz.d3() * other.xyz.d2(),
                this.xyz.d3() * other.xyz.d1() - this.xyz.d1() * other.xyz.d3(),
                this.xyz.d1() * other.xyz.d2() - this.xyz.d2() * other.xyz.d1());
    }

    /**
     * squared length
     * @return squared length
     */
    public double lengthSquared() {
        return this.xyz.d1() * this.xyz.d1() + this.xyz.d2() * this.xyz.d2() + this.xyz.d3() * this.xyz.d3();
    }

    /**
     * length
     * @return length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the vector
     * @return normalized vector
     */
    public Vector normalize() {
        return scale(1 / length());
    }

    /**
     * validation of the vector
     * throw exception if the vector is (0,0,0)
     */
    private void validation(){
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be (0,0,0)");
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && this.xyz.equals(other.xyz);
    }

}
