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
//        super(double3);
        this(double3.d1, double3.d2, double3.d3);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;

        return x*x + y*y +z*z;
    }

    public Vector normalize() {
        double len = length();

        double x = xyz.d1/len;
        double y = xyz.d2/len;
        double z = xyz.d3/len;

        return new Vector(x, y, z);

    }
}
