package primitives;

public class Vector extends Point{

    /**
     * Constructs a new Vector object with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the new Point object
     * @param y the y-coordinate of the new Point object
     * @param z the z-coordinate of the new Point object
     * @throw throw IllegalArgumentException in case of Vector Zero
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector can't be vector(0,0,0)");
        }
    }

    /**
     * Constructs a new Vector object from a Double3 object.
     *
     * @param double3 the Double3 object to construct the Point from
     */
    Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector can't be vector(0,0,0)");
        }
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        double x = this.xyz.d1;
        double y = this.xyz.d2;
        double z = this.xyz.d3;

        return x*x + y*y +z*z;
    }
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    public Vector normalize() {
        double len = length();

        double x = this.xyz.d1/len;
        double y = this.xyz.d2/len;
        double z = this.xyz.d3/len;

        return new Vector(x, y, z);

    }
    public Vector scale(double number) {
        double x = this.xyz.d1 * number;
        double y = this.xyz.d2 * number;
        double z = this.xyz.d3 * number;

        return new Vector(x, y, z);

    }

    public double dotProduct(Vector v3) {
        double x = xyz.d1*v3.xyz.d1;
        double y = xyz.d2*v3.xyz.d2;
        double z = xyz.d3*v3.xyz.d3;
        return x + y + z;
    }

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
        if(this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    @Override
    public String toString() {
        return "Vector: " + "xyz=" + xyz ;
    }
}
