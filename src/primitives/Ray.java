package primitives;
/**
 * Class Ray is the basic class representing a ray in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Ray {
    /**The beginning of labor*/
    private final Point p0;
    /**The direction of the ray*/
    private final Vector dir;

    /**
     * constructor creates the ray
     * @param p0 the beginning of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * returns the beginning of the ray
     * @return the beginning of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * returns the direction of the ray
     * @return the direction of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * returns the point on the ray at distance t from the beginning of the ray
     * @param t the distance from the beginning of the ray
     * @return the point on the ray at distance t from the beginning of the ray
     */
    public Point getPoint(double t) {
        if (t < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        if (Util.isZero(t)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.p0.equals(other.p0)
                && this.dir.equals(other.dir);
    }

}
