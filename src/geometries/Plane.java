/**
 A class representing a plane in a three-dimensional space.
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // ray begins at q0 of the plane
        if (q0.equals(P0)) {
            return null;
        }

        // ray is laying in the plane axis
        double nv = n.dotProduct(v);

        //ray direction cannot be parallel to plane orientation
        if (isZero(nv)) {
            return null;
        }

        Vector P0_Q0 = q0.subtract(P0);

        // numerator
        double nQMinusP0 = alignZero(n.dotProduct(P0_Q0));

        // t should be > 0
        if (isZero(nQMinusP0)) {
            return null;
        }

        double t = alignZero(nQMinusP0 / nv);

        // t should be > 0
        if (t < 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }

        // return immutable List
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}


