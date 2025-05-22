package primitives;

import geometries.Intersectable.Intersection;

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

    private final double DELTA = 0.1;

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
     * constructor creates the ray
     * @param p0 the beginning of the ray
     * @param dir the direction of the ray
     * @param normal the normal vector to the surface
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        this.dir = dir.normalize();
        double nv = normal.dotProduct(dir);
        if (Util.isZero(nv)) {
            this.p0 = p0;
        }
        else if (nv > 0) {
            this.p0 = p0.add(normal.scale(DELTA));
        }
        else {
            this.p0 = p0.subtract(normal.scale(DELTA));
        }
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
    public Point findClosestPoint(List<Point> points) {
        return points == null
                ? null
                : findClosestIntersection(
                        points.stream().map(
                                p -> new Intersection(null, p)).toList()).point;
    }

    public Intersection findClosestIntersection(List<Intersection> intersections) {
        if (intersections == null || intersections.isEmpty()) {
            return new Intersection(null, null);
        }
        Intersection closestIntersection = intersections.getFirst();
        double minDistance = p0.distance(closestIntersection.point);
        for (Intersection intersection : intersections) {
            double distance = p0.distance(intersection.point);
            if (distance < minDistance) {
                closestIntersection = intersection;
                minDistance = distance;
            }
        }
        return closestIntersection;
    }

}
