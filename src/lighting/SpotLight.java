package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private final Vector direction;
    private double narrowBeam = 0d;


    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        double l = direction.dotProduct(getL(p));
        if (l > 0)
            return super.getIntensity(p).scale(l);
        return Color.BLACK;
    }
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Setter for the narrowBeam field.
     *
     * @param narrowBeam The angle of the narrow beam in degrees.
     * @return The object itself.
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

}
