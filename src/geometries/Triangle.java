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
}
