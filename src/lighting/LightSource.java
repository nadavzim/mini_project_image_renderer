package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

public interface LightSource {
    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    Color getIntensity(Point p);

    /**
     * Returns the vector from the light source to the given point.
     *
     * @param p the point to get the vector to
     * @return the vector from the light source to the given point
     */
    Vector getL(Point p);

    double getDistance(Point point);

    List<Vector> getListL(Point p);


}
