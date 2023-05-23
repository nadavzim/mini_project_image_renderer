package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;


public class LightSource {
    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    public Color getIntensity(Point p);
    /**
     * Returns the vector from the light source to the given point.
     *
     * @param p the point to get the vector to
     * @return the vector from the light source to the given point
     */
    public Vector getL(Point p);

}
