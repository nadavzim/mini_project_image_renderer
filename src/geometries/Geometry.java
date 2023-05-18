package geometries;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric object in three-dimensional space.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * Calculates the normal vector to the geometry object at the given point.
     *
     * @param point the point on the geometry object
     * @return the normal vector to the geometry object at the given point
     */
    public abstract Vector  getNormal(Point point);

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}