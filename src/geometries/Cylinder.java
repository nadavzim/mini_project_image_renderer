package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * This class represents a cylinder shape in 3D space, which is defined by its base axis tube and height.
 * It extends Tube class and adds a height field.
 */
public class Cylinder extends Tube {
    double height;

    /**
     * Constructs a new Cylinder object with a given radius, axis Ray and height.
     *
     * @param radius  The radius of the cylinder.
     * @param axisRay The axis Ray of the cylinder.
     * @param height  The height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (radius <= 0)
            throw new IllegalArgumentException("The radius low then zero!");
        if (height <= 0)
            throw new IllegalArgumentException("The height low equal to zero!");
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns null since the normal of the cylinder at any point is not yet implemented.
     *
     * @param point The point to get the normal to the cylinder at.
     * @return The normal vector at the specified point on the cylinder surface.
     */
    @Override
    public Vector getNormal(Point point) {
        // Finding the normal:
        // n = normalize(point - o)
        // t = v * (point - p0)
        // o = p0 + t * v

        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();

        //if p=p0, then (p-p0) is zero vector
        //returns the vector of the base as a normal
        if (point.equals(p0)) {
            return v.scale(-1);
        }

        double t = v.dotProduct(point.subtract(p0));
        //check if the point on the bottom
        if (isZero(t)) {
            return v.scale(-1);
        }
        //check if the point on the top
        if (isZero(t - height)) {
            return v;
        }

        Point o = p0.add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                super.toString() +
                "height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (/*o == null ||*/ !(o instanceof Cylinder cylinder)) return false;
        if (!super.equals(o)) return false;

        return this.height == cylinder.height;
    }
}
