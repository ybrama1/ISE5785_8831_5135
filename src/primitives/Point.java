package primitives;

/** Class Point is the basic class representing a point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Jeshurun and Binyamin
 */
public class Point{
    /**3 coordinates of point*/
    protected final Double3 xyz;

    /**The beginning of labor */
    public static final Point ZERO = new Point(0,0,0);

    /**
     * constructor creates the point (x,y,z)
     * @param x first coordinate
     * @param y second coordinate
     * @param z third coordinate
     */
    public Point(double x, double y, double z){
        xyz = new Double3(x,y,z);
    }

    /**
     * constructor create the point xyz
     * @param xyz 3 coordinates
     */
    public Point(Double3 xyz){
        this.xyz = xyz;
    }

    /**
     * Vector subtraction
     * @param subtrahend second point
     * @return vector from the second point to the point
     */
    public Vector subtract(Point subtrahend){

        return new Vector(this.xyz.subtract(subtrahend.xyz));
    }

    /**
     * add vector to the point
     * @param addend the vector
     * @return new point
     */
    public Point add(Point addend){
        return new Point(this.xyz.add(addend.xyz));
    }

    /**
     * function to calculate the squared distance
     * @param target a point
     * @return squared distance
     */
    public double distanceSquared(Point target){
            if (this.equals(target)) return 0;
            Double3 distance = this.subtract(target).xyz;
            distance = distance.product(distance);
            return distance.d1() + distance.d2() + distance.d3();
    }

    /**
     * function to calculate the distance
     * @param target a point
     * @return distance
     */
    public double distance(Point target){
        return Math.sqrt(distanceSquared(target));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }


    @Override
    public String toString(){
        return xyz.toString();
    }

}
