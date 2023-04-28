package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;


/**
 * The Intersectable interface defines a contract for objects that can be intersected by a ray in 3D space.
 */
public interface Intersectable {
    /**
     * Finds the intersection points between the Intersectable object and a given Ray.
     *
     * @param ray the Ray to test for intersection with the Intersectable object.
     * @return a List of Point objects representing the intersection points, or an empty list if no intersections  found.
     */
    List<Point> findIntsersections(Ray ray);
}