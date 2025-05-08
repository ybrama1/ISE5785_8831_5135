package primitives;

import java.util.List;

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

    /**
     * Find the closest point to the ray from a list of points
     * @param points the list of points
     * @return the closest point to the ray
     */
    Point findClosestPoint(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }
        Point closestPoint = points.getFirst();
        double minDistance = p0.distanceSquared(closestPoint);
        for (Point point : points) {
            double distance = p0.distanceSquared(point);
            if (distance < minDistance) {
                closestPoint = point;
                minDistance = distance;
            }
        }
        return closestPoint;
    }

}
