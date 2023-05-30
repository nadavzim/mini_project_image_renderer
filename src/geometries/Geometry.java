package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric object in three-dimensional space.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Calculates the normal vector to the geometry object at the given point.
     *
     * @param point the point on the geometry object
     * @return the normal vector to the geometry object at the given point
     */
    public abstract Vector  getNormal(Point point);

    /**
     Retrieves the emission color of the object.
     @return The Color representing the emission color of the object.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     Sets the emission color of the object.
     @param emission The Color representing the new emission color of the object.
     @return The updated Geometry object with the new emission color.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**
     * Returns the material of the geometry object.
     *
     * @return the material of the geometry object
     */
    public Material getMaterial() {
        return material;
    }
    /**
     * Sets the material of the geometry object.
     *
     * @param material the material of the geometry object
     * @return the geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}