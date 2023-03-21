package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * This class represents a cylinder shape in 3D space, which is defined by its base axis tube and height.
 * It extends Tube class and adds a height field.
 */
public class Cylinder extends Tube{
    double height;

    /**
     * Constructs a new Cylinder object with a given radius, axis Ray and height.
     *
     * @param radius  The radius of the cylinder.
     * @param axisRay The axis Ray of the cylinder.
     * @param height  The height of the cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
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
        return null;
    }
}
