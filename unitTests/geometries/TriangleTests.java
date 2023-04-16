package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}