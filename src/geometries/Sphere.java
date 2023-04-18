/**
 A class representing a sphere in a three-dimensional space.
 */
package geometries;
import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    /** The center point of the sphere. */
    Point center;
    /**
     * Returns the center point of the sphere.
     * @return the center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Constructs a sphere object with the given radius and center point.
     * @param radius the radius of the sphere.
     * @param center the center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector of the sphere at the given point.
     * @param point the point to get the normal vector at.
     * @return the normal vector of the sphere at the given point.
     */
    public Vector getNormal(Point point) {
        return  (point.subtract(this.center).normalize());
    }
}
