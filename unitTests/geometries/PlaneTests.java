package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    void testFindIntsersections() {
        Ray ray = new Ray(new Point(0,0,2), new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point(0,0,4), new Vector(0,0,1));
        Point p = new Point(3,3,4);
        // TC01: a simple case of one intersection
        assertEquals(p, plane.findIntsersections(new Ray(new Point(1,1,1),new Vector(2,2,3))),
                "the intersection supposed to be only in the point (3,3,4) ");
        // TC02: a simple case of zero intersection's
        assertNull(plane.findIntsersections(new Ray(new Point(1, 1, 1), new Vector(1, 0, 0))),
                "Ray's line out doesn't supposed to intersect the plane");
    }
}