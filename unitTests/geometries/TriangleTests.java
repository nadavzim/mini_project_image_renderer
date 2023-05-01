package geometries;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 The TriangleTests class represents the unit tests for the Triangle class.
 */
class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     * Tests the equivalence partition of a regular triangle with a point on its plane.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point[] pts = {
                new Point(1, 1, 1),
                new Point(1, -1, 1),
                new Point(1, 0, -1)
        };
        Triangle t = new Triangle(pts[0], pts[1], pts[2]);
        Vector v = new Vector(1, 0, 0);
        Point p = new Point(0, 0.5, 0.1);
        assertEquals(v, t.getNormal(p).normalize(), "wrong normal for the triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#Triangle(Point,Point,Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try{
            new Triangle
                    (
                            new Point(1,0,0),
                            new Point(0,1,0),
                            new Point(0,0,1)
                    );
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct triangle");
        }
        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        assertThrows(IllegalArgumentException.class,()->new Triangle
                (
                        new Point(1, 0, 0),
                        new Point(1, 0, 0),
                        new Point(0, 0, 1)
                ),"Constructed a triangle while a point equal to b point");

        //TC03: Test when a point equal to c point.
        assertThrows(IllegalArgumentException.class,()->new Triangle
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(1, 0, 0)
                ),"Constructed a triangle while a point equal to c point");
        //TC04: Test when b point equal to c point.
        assertThrows(IllegalArgumentException.class,()->new Triangle
                (
                        new Point(0, 1, 0),
                        new Point(1, 0, 0),
                        new Point(1, 0, 0)
                ),"Constructed a triangle while c point equal to b point");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the triangle
        result = triangle.findIntersections(new Ray(new Point(-1, -1, -2), new Vector(1, 1, 2)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(0.25d, 0.25d, 0.5d), result.get(0), "Ray doesn't intersect the triangle");

        //TC02:Ray outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(-2, -2, -2), new Vector(1, 1, 2))), "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -2, -2), new Vector(1, 1, 2))), "Ray isn't outside against edge");

        //TC04:Ray inside the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0.5, 0.5, 0.2), new Vector(0.5, 0.5, 1.8d))), "Ray  isn't inside the triangle");

        // ============ Boundary Values Tests =============
        //TC11: Ray On edge
        assertNull(triangle.findIntersections(new Ray(new Point(0,0.5d,0.5d),new Vector(-2.9d,0.85d,-0.5d))),"Ray On edge");
        //TC12: Ray in vertex
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),new Vector(0.32d,-0.09d,0))),"Ray On vertex");
        //TC13: Ray On edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(0,-0.5d,1.5d),new Vector(-2.31d,-1d,-1.5d))),"Ray On edge's continuation");

    }
}