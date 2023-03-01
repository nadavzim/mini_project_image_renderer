//package primitives;
//
//import java.util.Objects;
//
//public class Point {
//    final Double3 xyz;
//    public Point(double x, double y, double z) {
//        xyz = new Double3(x,y,z);
//    }
//
//    Point(Double3 double3){
//        this(double3.d1, double3.d2, double3.d3);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Point point)) return false;
//        return xyz.equals(point.xyz);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(xyz);
//    }
//
//    @Override
//    public String toString() {
//        return "Point :" + xyz;
//    }
//    public double distance(Point other) {
//        return Math.sqrt(distanceSquared(other));
//    }
//
//    public double distanceSquared(Point other) {
//        double dx = other.xyz.d1 - xyz.d1;
//        double dy = other.xyz.d2 - xyz.d2;
//        double dz = other.xyz.d3 - xyz.d3;
//
//        return dx * dx + dy * dy + dz * dz;
//    }
//}


package primitives;

import java.util.Objects;

/**
 * The Point class represents a point in 3D space using three double values for the x, y, and z coordinates.
 * It uses a Double3 object to store the coordinates.
 */
public class Point {

    /**
     * The coordinates of this Point object.
     */
    final Double3 xyz;

    /**
     * Constructs a new Point object with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the new Point object
     * @param y the y-coordinate of the new Point object
     * @param z the z-coordinate of the new Point object
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point object from a Double3 object.
     *
     * @param double3 the Double3 object to construct the Point from
     */
    Point(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Determines if this Point object is equal to another object.
     * Two Point objects are considered equal if their coordinates are equal.
     *
     * @param o the object to compare to this Point object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    /**
     * Computes a hash code for this Point object.
     * The hash code is based on the coordinates of this Point object.
     *
     * @return the hash code of this Point object
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * Returns a string representation of this Point object.
     * The string representation consists of the word "Point" followed by the coordinates of this Point object.
     *
     * @return a string representation of this Point object
     */
    @Override
    public String toString() {
        return "Point :" + xyz;
    }

    /**
     * Computes the Euclidean distance between this Point object and another Point object.
     *
     * @param other the Point object to compute the distance to
     * @return the Euclidean distance between this Point object and the other Point object
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Computes the square of the Euclidean distance between this Point object and another Point object.
     *
     * @param other the Point object to compute the distance to
     * @return the square of the Euclidean distance between this Point object and the other Point object
     */
    public double distanceSquared(Point other) {
        double dx = other.xyz.d1 - xyz.d1;
        double dy = other.xyz.d2 - xyz.d2;
        double dz = other.xyz.d3 - xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    public Vector subtract(Point point) {
        return new Vector(point.xyz.subtract(xyz));
    }
}
