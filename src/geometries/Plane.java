/**
 A class representing a plane in a three-dimensional space.
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Plane implements Geometry {
    /**
     * The base point of the plane.
     */
    Point q0;

    /**
     * The normal vector of the plane.
     */
    Vector normal;

    /**
     * Constructs a plane object with the given base point and normal vector.
     *
     * @param q0     the base point of the plane.
     * @param normal the normal vector of the plane.
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a plane object with the given three points.
     *
     * @param a the first point.
     * @param b the second point.
     * @param c the third point.
     */
    public Plane(Point a, Point b, Point c) {
        this.q0 = a;
        Vector v1 = a.subtract(b);
        Vector v2 = a.subtract(c);
        try {
            normal = v1.crossProduct(v2).normalize();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("This three points in the same line!");
        }
    }

    /**
     * Returns the normal vector of the plane at the given point.
     *
     * @param p the point to get the normal vector at.
     * @return the normal vector of the plane at the given point.
     */
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}


