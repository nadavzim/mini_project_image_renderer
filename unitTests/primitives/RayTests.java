package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(1,2,3), new Vector(1,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: closest point is in the middle of the list
        List<Point> points1 = List.of(new Point(4,2,3), new Point(10,2,3), new Point(2,2,3), new Point(7,2,3));
        assertEquals(new Point(2,2,3), ray.findClosestPoint(points1), "wrong closest point - it should be 2,2,3");

        // =============== Boundary Values Tests ==================
        // TC11 empty list of points
        assertNull(ray.findClosestPoint(List.of()), "wrong closest point - it should be null");
        // TC12 first point is the closest from the list of points
        List<Point> points2 = List.of(new Point(2,2,3), new Point(4,2,3), new Point(10,2,3), new Point(7,2,3));
        assertEquals(new Point(2,2,3), ray.findClosestPoint(points2), "wrong closest point - it should be 2,2,3");
        // TC13 last point is the closest from the list of points
        List<Point> points3 = List.of(new Point(4,2,3), new Point(10,2,3), new Point(7,2,3), new Point(2,2,3));
        assertEquals(new Point(2,2,3), ray.findClosestPoint(points3), "wrong closest point - it should be 2,2,3");

    }
}