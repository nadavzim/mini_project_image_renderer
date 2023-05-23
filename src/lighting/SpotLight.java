package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private final Vector direction;
    private double narrowBeam = 0d;


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
