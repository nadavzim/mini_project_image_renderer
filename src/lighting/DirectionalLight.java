package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

public class DirectionalLight extends Light implements LightSource {

    private final Vector direction;

    /**
     * create a directional light source with a color
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    public List<Vector> getListL(Point p) {
        return List.of(getL(p));
    }

    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    /**
     * Returns the vector from the light source to the given point.
     *
     * @param p the point to get the vector to
     * @return the vector from the light source to the given point
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    /**
     * @param point
     * @return
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
