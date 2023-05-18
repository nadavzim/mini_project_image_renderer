//package geometries;
//
//import primitives.Point;
//
//public class Triangle extends Polygon {
//    public Triangle(Point a, Point b, Point c) {
//    super(a, b, c);
//    }
//}

/**
 A class representing a triangle in a three-dimensional space.
 */
package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;


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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result =plane.findGeoIntersectionsHelper(ray);
        if(result == null)
            return null;

        Point P0=ray.getP0();
        Vector v=ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(P0);//(P0->p1)
        Vector v2 = p2.subtract(P0);//(P0->p2)
        Vector v3 = p3.subtract(P0);//(P0->p3)

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double s1 = v.dotProduct(n1);
        double s2 = v.dotProduct(n2);
        double s3 = v.dotProduct(n3);

        if((s1>0 && s2>0 && s3>0 )|| (s1<0 && s2<0 && s3<0))
        {
            return result;
        }
        return super.findGeoIntersectionsHelper(ray);
    }
    
}