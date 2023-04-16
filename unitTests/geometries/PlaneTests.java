package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}