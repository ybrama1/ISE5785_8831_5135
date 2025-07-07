package geometries;

import primitives.AABB;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BVHNode extends Intersectable {
    private final Intersectable left;
    private final Intersectable right;
    private final AABB box;

    /**
     * Constructor that builds a BVHNode from a list of objects.
     * The objects are sorted and divided into left and right nodes recursively.
     *
     * @param objects the list of intersectable objects to build the BVH from
     */
    public BVHNode(List<Intersectable> objects) {
        this(objects, 0, objects.size());
        //run on the tree and count the number of objects(not nodes)
        //System.out.println("Number of objects in BVH: " + countObjects());

    }

    public BVHNode(Geometries geometries){
        this(geometries.geometries);
    }

    public int countObjects() {
        int count = 0;
        //if they are not null or BVHNode count them
        if (left != null) {
            if (left instanceof BVHNode) {
                count += ((BVHNode) left).countObjects();
            } else if(left instanceof Polygon){
                AABB b = ((Polygon) left).getBoundingBox();
                count += 1; // Count the polygon as an object

            } else {
                count++;
            }
        }
        if (right != null) {
            if (right instanceof BVHNode) {
                count += ((BVHNode) right).countObjects();
            } else if(right instanceof Polygon){
                AABB b = ((Polygon) right).getBoundingBox();
                count += 1; // Count the polygon as an object
            } else {
                count++;
            }
        }
        return count;
    }

    /**
     * Constructor that builds a BVHNode from a sublist of objects.
     * The objects are sorted and divided into left and right nodes recursively.
     *
     * @param objects the list of intersectable objects to build the BVH from
     * @param start   the starting index of the sublist
     * @param end     the ending index of the sublist
     */
    public BVHNode(List<Intersectable> objects, int start, int end) {
        int axis = (int)(3 * Math.random());
        Comparator<Intersectable> comparator = getBoxComparator(axis);

        int size = end - start;

        if(size <= 0) {
            left = null;
            right = null;
            box = new AABB(Point.ZERO,Point.ZERO); // Empty bounding box
            return;
        }
        if (size == 1) {
            right = objects.get(start);
            left = null;
            box = right.getBoundingBox();
            return;
        } else if (size == 2) {
            if (comparator.compare(objects.get(start), objects.get(start + 1)) < 0) {
                left = objects.get(start);
                right = objects.get(start + 1);
            } else {
                left = objects.get(start + 1);
                right = objects.get(start);
            }
        } else {
            objects.subList(start, end).sort(comparator);
            int mid = start + size / 2;
            left = new BVHNode(objects, start, mid);
            right = new BVHNode(objects, mid, end);
        }

        box = AABB.surroundingBox(
                left.getBoundingBox(),
                right.getBoundingBox()
        );
    }


    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // Check if the ray intersects the bounding box of this node
        if (!box.intersects(ray)) {
            return null;
        }

        List<Intersection> intersections = new ArrayList<>();

        // Recursively calculate intersections in the left and right nodes
        List<Intersection> leftHits = new ArrayList<>();
        List<Intersection> rightHits = new ArrayList<>();
        if(left != null) leftHits = left.calculateIntersections(ray);
        if(right != null) rightHits = right.calculateIntersections(ray);

        if (leftHits != null) {
            intersections.addAll(leftHits);
        }
        if (rightHits != null) {
            intersections.addAll(rightHits);
        }
        return intersections.isEmpty() ? null : intersections;
    }

    public AABB getBoundingBox() {
        return box;
    }

    /**
     * Returns a comparator that sorts intersectable objects based on their bounding box center along the specified axis.
     *
     * @param axis the axis to sort by (0 for x, 1 for y, 2 for z)
     * @return a comparator that compares two intersectable objects based on their bounding box centers
     */
    private Comparator<Intersectable> getBoxComparator(int axis) {
        return (a, b) -> {
            AABB boxA = a.getBoundingBox();
            AABB boxB = b.getBoundingBox();

            double centerA = (boxA.getMin().getCoordinate(axis) + boxA.getMax().getCoordinate(axis)) / 2;
            double centerB = (boxB.getMin().getCoordinate(axis) + boxB.getMax().getCoordinate(axis)) / 2;

            return Double.compare(centerA, centerB);
        };
    }

}
