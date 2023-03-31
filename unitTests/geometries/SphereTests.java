package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SphereTests {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere s = new Sphere(2, new Point(0, 0, 0));
        Vector v = new Vector(1, 0, 0);
        assertEquals(v, s.getNormal(new Point(2, 0, 0)), "bad normal for the sphere");
    }
}