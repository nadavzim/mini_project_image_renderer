package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric object in three-dimensional space.
 */
public interface Geometry {
    /**
     * Calculates the normal vector to the geometry object at the given point.
     *
     * @param point the point on the geometry object
     * @return the normal vector to the geometry object at the given point
     */
    public Vector getNormal(Point point);
}