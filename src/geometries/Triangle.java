//package geometries;
//
//import primitives.Point;
//
//public class Triangle extends Polygon {
//    public Triangle(Point a, Point b, Point c) {
//    super(a, b, c);
//    }
//}

package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * A class representing a triangle in a three-dimensional space.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a triangle object with the given three vertices.
     *
     * @param a the first vertex of the triangle.
     * @param b the second vertex of the triangle.
     * @param c the third vertex of the triangle.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    /**
     * calc the normal of the point on the triangle
     *
     * @param point the point on the geometry object
     * @return the normal of the triangle
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }


    /**
     * Finds the intersection points between the Triangle object and a given Ray.
     *
     * @param ray the Ray to test for intersection with the Triangle object.
     * @return a List of Point objects representing the intersection points, or an empty list if no intersections  found.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance, boolean bb) {
        // Gets all intersections with the plane
        var result = plane.findGeoIntersections(ray, maxDistance);

        // if there is no intersections with the whole plane,
        // then is no intersections with the triangle
        if (result == null) {
            return null;
        }

        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = this.vertices.get(0).subtract(P0),
                v2 = this.vertices.get(1).subtract(P0),
                v3 = this.vertices.get(2).subtract(P0);

        Vector n1 = v1.crossProduct(v2).normalize(),
                n2 = v2.crossProduct(v3).normalize(),
                n3 = v3.crossProduct(v1).normalize();

        double a = alignZero(v.dotProduct(n1)),
                b = alignZero(v.dotProduct(n2)),
                c = alignZero(v.dotProduct(n3));

        // if all the points have the same sign(+/-),
        // all the points are inside the triangle
        if (a < 0 && b < 0 && c < 0 || a > 0 && b > 0 && c > 0)
            return List.of(new GeoPoint(this, result.get(0).point));

        return null;
    }
}
