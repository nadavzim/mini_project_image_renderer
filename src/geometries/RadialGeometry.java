package geometries;

/**
 * This abstract class represents a radial geometry object in 3D space, which is defined by a radius value.
 * It implements the Geometry interface.
 */
public abstract class RadialGeometry extends Geometry{
    protected double radius;

    /**
     * Constructs a new RadialGeometry object with a given radius value.
     *
     * @param radius The radius value of the RadialGeometry object.
     */
    public RadialGeometry(double radius) {
        if(radius<=0)
            throw new IllegalArgumentException("This radius is not legal!");

        this.radius = radius;
    }

}
