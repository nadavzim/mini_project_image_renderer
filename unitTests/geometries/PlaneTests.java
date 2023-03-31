package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaneTests {

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