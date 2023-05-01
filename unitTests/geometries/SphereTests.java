package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**

 This class represents tests for the Sphere class.
 It contains tests for the getNormal method, testing the normal vector calculation for the sphere.
 The tests include Equivalence Partitions Tests.
 */

class SphereTests {
    /*
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     * Test for Equivalence Partition: single test for a simple case where the normal is on the X axis.
     */
    /**
     * Test methode for {@link geometries.Sphere#Sphere(Point, double)}
     */
    @Test
    void testConstorSphere(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(new Point(1, 2, 3), 5);
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct sphere");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when the radius is 0.
        assertThrows(IllegalArgumentException.class, () -> new Sphere(new Point(1, 2, 3), 0), "Constructed a sphere while the radius is 0");
        // TC03: Test when the radius is negative,-1.
        assertThrows(IllegalArgumentException.class, () -> new Sphere(new Point(1, 2, 3), -1), "Constructed a sphere while the radius is negative");


    }
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere s = new Sphere(new Point(0, 0, 0), 2);
        Vector v = new Vector(1, 0, 0);
        assertEquals(v, s.getNormal(new Point(2, 0, 0)), "bad normal for the sphere");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(-2, 0, 0),Math.sqrt(21));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(5,-6,0), new Vector(3, 6, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(-4.894427190999921,0,3.5527864045000395);
        Point p2 = new Point(-3.1055728090000763,0,4.447213595499962);
        result = sphere.findIntersections(new Ray(new Point(-12,0,0), new Vector(12,0,6)));

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), List.of(result.get(0),result.get(1)), "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(-0.9d, 1.45d, 4.2d), new Vector(3.4d, 4.05d, -4.2d)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(0.3069937380117084, 2.8877425408668875, 2.7090077353973014), result.get(0), "Ray starts inside the sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-2, -6, 0), new Vector(2, -3, 0))), "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line goes through the center
        // TC11: Ray starts before the sphere (2 points)
        result=sphere.findIntersections(new Ray(new Point(0,-8,0),new Vector(-3.35d,13.51d,0)));

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(new Point(-3.0875670840832323,4.451651136108795,0.0), new Point (-0.8817509698708297,-4.4440431035955505,0.0)), List.of(result.get(0),result.get(1)), "Ray starts before the sphere. The ray's line goes through the center");

        // TC12: Ray starts at sphere and goes inside (a points)
        result = sphere.findIntersections(new Ray(new Point(-3.17d,4.42d,0), new Vector(3.18d,-12.43d,0)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-0.9010568316576144,-4.448856472482971,0.0), result.get(0),"Ray starts at sphere and goes inside. The ray's line goes through the center");

        // TC13: Ray starts inside (a points)
        result= sphere.findIntersections(new Ray(new Point(-4,0,0) ,new Vector(0,1,0)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-4.0,4.123105625617661,0.0),result.get(0),"Ray starts inside sphere and it's line goes through the center");

        // TC14: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-8,0,0) ,new Vector(0,1,0))),"Ray starts after sphere and goes outside. The ray's line goes through the center");

        // TC15: Ray starts at sphere and goes outside (0 points)
        Sphere s = new Sphere(new Point(1,0,0),1);
        assertNull(s.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 1, 0))),"Ray starts at sphere and goes outside. The ray's line goes through the center");

        // TC16: Ray starts at the center (a points)
        result=sphere.findIntersections(new Ray(new Point(-2,0,0),new Vector(-5,-5,0)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-5.24037034920393,-3.2403703492039297,0.0), result.get(0),"Ray starts at the sphere's center");

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC17: Ray starts at sphere and goes inside (a points)
        result=sphere.findIntersections(new Ray(new Point(2.34d,0.88d,1.17d),new Vector(-7.34d,-9.88d,-1.17d)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(-1.6875816427897758,-4.541322429259263,0.5280012912719294), result.get(0),"Ray starts at sphere and goes inside");

        // TC18: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-6.1d,0,2.05d),new Vector(-3.9d,0,-2.05))),"Ray starts at sphere and goes outside");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-3.15d,-6.31d,0.26d),new Vector(0.52d,-8.31d,-7.35d))),"Ray starts before the sphere's tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,4.58d),new Vector(2,0,0))),"Ray starts at the sphere's tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-3.46d,-1.35d,4.65d),new Vector(-0.21d,3.35d,2.96d))),"Ray starts after the sphere's tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-2,0,6.28),new Vector(2,0,-0.01))),"Ray's line is outside the sphere. The ray is orthogonal to the ray that goes through the center line");
    }
}