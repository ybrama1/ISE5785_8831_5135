package primitives;
import  primitives.Point;
import primitives.Vector;
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
     * return whether 2 objects are equals
     * @param obj the other object
     * @return whether they are equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.p0.equals(other.p0)
                && this.dir.equals(other.dir);
    }
}
