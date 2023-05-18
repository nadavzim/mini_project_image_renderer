/**
 * A class representing a sphere in a three-dimensional space.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends RadialGeometry {
    /** The center point of the sphere. */
    Point center;

    /**
     * Constructs a sphere object with the given radius and center point.
     * @param radius the radius of the sphere.
     * @param center the center point of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     * @return the center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the normal vector of the sphere at the given point.
     * @param point the point to get the normal vector at.
     * @return the normal vector of the sphere at the given point.
     */
    public Vector getNormal(Point point) {
        return (point.subtract(this.center).normalize());
    }

    /**
     * @param ray
     * @return the intersection points of the ray with the sphere.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (ray.getP0().equals(center)) {
            List<GeoPoint> points = new ArrayList<>(1);
            Point p = center.add(ray.getDir().scale(radius));
            points.add(new GeoPoint(this, p));
            return points;
        }

        Vector u = center.subtract(ray.getP0());
        double Tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-Tm*Tm);
        if (d >= radius)
            return null;
        double Th = Math.sqrt(radius*radius-d*d);
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);
        if (t1 <= 0 && t2 <= 0)
            return null;
        int size = 0;
        if(t1 > 0)
            size += 1;
        if(t2 > 0)
            size += 1;
        List<GeoPoint> points = new ArrayList<>(size);
        if (t1 > 0) {
            Point p = ray.getPoint(t1);
            points.add(new GeoPoint(this, p));
        }
        if (t2 > 0) {
            Point p = ray.getPoint(t2);
            points.add(new GeoPoint(this, p));
        }
        return points;
    }

}
