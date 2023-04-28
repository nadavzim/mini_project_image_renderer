package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 The PlaneTests class contains tests for the Plane class using JUnit 5.
 */
class PlaneTests {
    /**
     * Tests the getNormal method of the Plane class.
     * Equivalence partitions:
     * - TC01: A simple test with a plane defined by three points.
     *          The expected normal vector is the normalized vector (sqrt(1/3), sqrt(1/3), sqrt(1/3)).
     *          The tested point is (0, 0, 1).
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: a simple single test here
        Plane p = new Plane(new Point(1, 0, 0), new Point(0, 0, 1), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
//        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), p.getNormal(new Point(0, 0, 1)), "wrong normal to plane");
        assertTrue(new Vector(sqrt3, sqrt3, sqrt3).equals(p.getNormal(new Point(0, 0, 1))) ||
                new Vector(-sqrt3, -sqrt3, -sqrt3).equals(p.getNormal(new Point(0, 0, 1))), "wrong plane normal");

    }

    @Test
    void testFindIntersections1() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point(3,0,0), new Point(0,3,0), new Point(0,0,3));
        // TC01: a simple case of one intersection
        List<Point> res1 = plane.findIntersections(new Ray(new Point(1,2,3),new Vector(0,-1,-2)));
        assertEquals(1, res1.size(),"error: the intersection supposed to be only in the point (1,1,1) ");
        // TC02: Ray starts after and doesn't cross the Plane - no intersection's
        assertNull(plane.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(-1, -1,-1))),
                "Ray's line doesn't supposed to intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: Ray is not included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(4,4,4),new Vector(-1,0,1))),
                "ERROR: Ray is parallel to the plane");
        // TC12: Ray is included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,1,1),new Vector(-1,0,1))),
                "ERROR: Ray is included in the plane - no intersection points");
        // **** Group: Ray is orthogonal to the plane
        //TC13: Ray start before the plane
        List<Point> res2 = plane.findIntersections(new Ray(new Point(0.5,0.5,0.5),new Vector(1,1,1)));
        assertEquals(1, res1.size(),
                "error: the intersection supposed to be in the point (1,1,1), (Ray orthogonal to the plane)");
        //TC14: Ray start at the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,1,1),new Vector(1,1,1))),
                "ERROR: Ray head is in the plane - no intersections (Ray orthogonal to the plane");
        //TC15: Ray start after the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,2,2),new Vector(1,1,1))),
                "ERROR: Ray head is after the plane - no intersections (Ray orthogonal to the plane");
        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane (𝑃0 is in the plane, but not the ray)
        assertNull(plane.findIntersections(new Ray(new Point(1,1,1),new Vector(1,0,0))),
                "ERROR: Ray head is in the plane - no intersections");
        // TC17: Ray is neither orthogonal nor parallel to and begins at the plane (𝑃0 begin as the p0y)
        assertNull(plane.findIntersections(new Ray(new Point(3,0,0),new Vector(1,0,0))),
                "ERROR: Ray head is in the p0 of the plane - no intersections");
    }


    @Test
    void findIntersections2() {
        Plane p1 = new Plane(new Point(-4, 4, 1), new Vector(-2, -2, -1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        Ray r1 = new Ray(new Point(5, 3, 0), new Vector(-4, -2.5, 4));
        assertEquals(List.of(new Point(-5d/3d, -7d/6d, 20d/3d)), p1.findIntersections(r1),
                "findIntersections() wrong result");

        //TC02: Ray does not intersects the plane
        Ray r2 = new Ray(new Point(5, 3, 0), new Vector(4, 2.5, 4));
        assertNull(p1.findIntersections(r2),
                "findIntersections() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Ray is parallel to the plane and included in the plane
        Ray r3 = new Ray(new Point(-4, 4, 1), new Vector(7f/3f, -31f/6f, 17f/3f));
        assertNull(p1.findIntersections(r3),
                "findIntersections() for parallel and included Ray is wrong");

        //TC12: Ray is parallel to the plane and not included in the plane
        Ray r4 = new Ray(new Point(1, 2, 3), new Vector(4, 2.5, 4));
        assertNull(p1.findIntersections(r4),
                "findIntersections() for parallel and not included Ray is wrong");

        //TC13: Ray is orthogonal to the plane and p0 before the plane
        Ray r5 = new Ray(new Point(5, 3, 0), new Vector(-2, -2, -1));
        assertEquals(List.of(new Point(5d/3d, -1d/3d, -5d/3d)), p1.findIntersections(r5),
                "findIntersections() for orthogonal Ray before the plane is wrong");

        //TC14: Ray is orthogonal to the plane and p0 in the plane
        Ray r6 = new Ray(new Point(3, -3, 1), new Vector(-2, -2, -1));
        assertNull(p1.findIntersections(r6),
                "findIntersections() for orthogonal Ray in the plane is wrong");

        //TC15: Ray is orthogonal to the plane and p0 after the plane
        Ray r7 = new Ray(new Point(-4, -3, 0), new Vector(-4, -3, 0));
        assertNull(p1.findIntersections(r7),
                "findIntersections() for orthogonal Ray after the plane is wrong");

        //TC16: Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)
        Ray r8 = new Ray(new Point(3, 3, 1), new Vector(1, 2, 3));
        assertNull(p1.findIntersections(r8),
                "findIntersections() for neither orthogonal nor parallel Ray in the plane is wrong");

        //TC17: Ray is neither orthogonal nor parallel to the plane and begins in the same point (p0 equals q)
        Ray r9 = new Ray(new Point(-4, 4, 1), new Vector(1, 2, 3));
        assertNull(p1.findIntersections(r9),//problem
                "findIntersections() for neither orthogonal nor parallel Ray in the plane is wrong");
    }
}


