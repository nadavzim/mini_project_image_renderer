/**
 * A class representing a sphere in a three-dimensional space.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    Point center;

    /**
     * Constructs a sphere object with the given radius and center point.
     *
     * @param radius the radius of the sphere.
     * @param center the center point of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the normal vector of the sphere at the given point.
     *
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance, boolean bb) {
        if (ray.getP0().equals(center)) {
            List<GeoPoint> points = new ArrayList<>(1);
            Point p = center.add(ray.getDir().scale(radius));
            points.add(new GeoPoint(this, p));
            return points;
        }

        Vector u = center.subtract(ray.getP0());
        double Tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - Tm * Tm);
        if (d >= radius)
            return null;
        double Th = Math.sqrt(radius * radius - d * d);
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);
        if (t1 <= 0 && t2 <= 0)
            return null;
        int size = 0;
        if (t1 > 0)
            size += 1;
        if (t2 > 0)
            size += 1;
        List<GeoPoint> points = new ArrayList<>(size);
        if (t1 > 0) {
            Point p = ray.getPoint(t1);
            if (alignZero(p.distance(ray.getP0()) - maxDistance) <= 0)
                points.add(new GeoPoint(this, p));
        }
        if (t2 > 0) {
            Point p = ray.getPoint(t2);
            if (alignZero(p.distance(ray.getP0()) - maxDistance) <= 0)
                points.add(new GeoPoint(this, p));
        }
        return points;
    }

    /**
     * method sets the values of the bounding volume for the intersectable sphere
     */
    @Override
    public void setBoundingBox() {

        super.setBoundingBox();

        // get minimal & maximal x value for the containing box
        double minX = center.getX() - radius;
        double maxX = center.getX() + radius;

        // get minimal & maximal y value for the containing box
        double minY = center.getY() - radius;
        double maxY = center.getY() + radius;

        // get minimal & maximal z value for the containing box
        double minZ = center.getZ() - radius;
        double maxZ = center.getZ() + radius;

        // set the minimum and maximum values in 3 axes for this bounding region of the component
        boundingBox.setBoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
    }

}
