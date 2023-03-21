package geometries;

/**
 * This abstract class represents a radial geometry object in 3D space, which is defined by a radius value.
 * It implements the Geometry interface.
 */
public abstract class RadialGeometry implements Geometry{
    protected double radius;

    /**
     * Constructs a new RadialGeometry object with a given radius value.
     *
     * @param radius The radius value of the RadialGeometry object.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
