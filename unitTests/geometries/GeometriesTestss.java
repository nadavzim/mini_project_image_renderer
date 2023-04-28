package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries(
                new Sphere(1, new Point(1,1,1)),
                new Plane(new Point(-4, 4, 1), new Vector(-2, -2, -1)),
                new Triangle(new Point(-0.5, -1.2, 0), new Point(-1, 1, 0.5), new Point(-1, 0.5, -1)));
    }
}