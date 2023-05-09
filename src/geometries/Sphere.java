/**
 * A class representing a sphere in a three-dimensional space.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        Vector distanceVec;
        // distanceVec = 𝑂 − 𝑃0 distance from the center and the p0
        try {
            distanceVec = center.subtract(p0);
        } catch (IllegalArgumentException e) {
            return List.of(ray.getPoint(radius));
        }
        //distance=dir*distanceVec the distance between p0 and the point with makes 90 degrees with the center
        double distance = alignZero(dir.dotProduct(distanceVec));
        //d=distanceVec^2+distance^2 distance between the center and the point that  makes 90 degrees with the center
        double cap;
        if (distance == 0)
            cap = distanceVec.lengthSquared();
        else {
            cap = distanceVec.lengthSquared() - distance * distance;
        }
        double thsquared = alignZero(radius * radius - cap);

        if (thsquared <= 0) return null;
        //th=radius^2-distance^2 between p1 and center
        double th = alignZero(Math.sqrt(thsquared));
        if (th == 0) return null;

        double t1 = alignZero(distance - th);
        double t2 = alignZero(distance + th);
        if (t1 <= 0 && t2 <= 0) return null;

        if (t1 > 0 && t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2)); //P1 , P2
        if (t1 > 0)
            return List.of(ray.getPoint(t1)); //just one point
        if (t2 > 0)
            return List.of(ray.getPoint(t2));
        return null;
    }
}
