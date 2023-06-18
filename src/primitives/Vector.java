package primitives;

public class Vector extends Point {

    /**
     * Constructs a new Vector object with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the new Vector object
     * @param y the y-coordinate of the new Vector object
     * @param z the z-coordinate of the new Vector object
     * @throw IllegalArgumentException if the vector is a zero vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector can't be vector(0,0,0)");
        }
    }

    /**
     * Constructs a new Vector object from a Double3 object.
     *
     * @param double3 the Double3 object to construct the Vector from
     */
    Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector can't be vector(0,0,0)");
        }
    }

    /**
     * Returns the length of the vector.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the squared length of the vector.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        double x = this.xyz.d1;
        double y = this.xyz.d2;
        double z = this.xyz.d3;

        return x * x + y * y + z * z;
    }

    /**
     * Returns the sum of this vector and the specified vector.
     *
     * @param vector the vector to add to this vector
     * @return the sum of this vector and the specified vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Returns a new vector that is the normalized version of this vector.
     *
     * @return a new vector that is the normalized version of this vector
     */
    public Vector normalize() {
        double len = length();

        double x = this.xyz.d1 / len;
        double y = this.xyz.d2 / len;
        double z = this.xyz.d3 / len;

        return new Vector(x, y, z);

    }

    /**
     * Returns a new vector that is the result of scaling this vector by the specified number.
     *
     * @param number the number to scale this vector by
     * @return a new vector that is the result of scaling this vector by the specified number
     */
    public Vector scale(double number) {
        double x = this.xyz.d1 * number;
        double y = this.xyz.d2 * number;
        double z = this.xyz.d3 * number;

        return new Vector(x, y, z);

    }

    /**
     * Returns the dot product of this vector and the specified vector.
     *
     * @param v3 the vector to compute the dot product with
     * @return the dot product of this vector and the specified vector
     */
    public double dotProduct(Vector v3) {
        double x = xyz.d1 * v3.xyz.d1;
        double y = xyz.d2 * v3.xyz.d2;
        double z = xyz.d3 * v3.xyz.d3;
        return x + y + z;
    }

    /**
     * Computes the cross product of this vector and the given vector and returns a new Vector object that represents the result.
     *
     * @param other the vector to compute the cross product with
     * @return a new Vector object representing the cross product of this vector and the given vector
     */
    public Vector crossProduct(Vector other) {
        double x = this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2;
        double y = this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3;
        double z = this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1;
        return new Vector(x, y, z);
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
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    /**
     * Returns a string representation of this Vector object.
     *
     * @return a string representation of this Vector object
     */
    @Override
    public String toString() {
        return "Vector: " + "xyz=" + xyz;
    }

    /***
     * Rotate the vector by angle (in degrees) and axis of rotation
     * @param axis Axis of rotation
     * @param theta Angle of rotation (degrees)
     */
    public Vector rotateVector(Vector axis, double theta) {

        //Use of Rodrigues' rotation formula
        //https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
        //v: vector to rotate
        //k: axis of rotation
        //θ: angle of rotation
        //Vrot = v * Cos θ + (k * v) * Sin θ + k(k,v) * (1 - Cos θ)

        //Variables used in computing
        double x, y, z;
        double u, v, w;
        x = this.xyz.d1;
        y = this.xyz.d2;
        z = this.xyz.d3;
        u = axis.xyz.d1;
        v = axis.xyz.d2;
        w = axis.xyz.d3;
        double v1 = u * x + v * y + w * z;

        //Convert degrees to Rad
        double thetaRad = Math.toRadians(theta);

        //Calculate X's new coordinates
        double xPrime = u * v1 * (1d - Math.cos(thetaRad))
                + x * Math.cos(thetaRad)
                + (-w * y + v * z) * Math.sin(thetaRad);

        //Calculate Y's new coordinates
        double yPrime = v * v1 * (1d - Math.cos(thetaRad))
                + y * Math.cos(thetaRad)
                + (w * x - u * z) * Math.sin(thetaRad);

        //Calculate Z's new coordinates
        double zPrime = w * v1 * (1d - Math.cos(thetaRad))
                + z * Math.cos(thetaRad)
                + (-v * x + u * y) * Math.sin(thetaRad);

        return new Vector(xPrime, yPrime, zPrime);
    }

    /**
     * get the start of ray
     *
     * @return ray's head
     */
    public Point getHead() {
        Point p = new Point(this.xyz);
        return p;
    }
}
