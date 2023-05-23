package lighting;

import primitives.Color;
import primitives.Point;

public class PointLight extends Light implements LightSource{

    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    private Point position;
    private double kc = 1d;
    private double kl = 0d;
    private double kq = 0d;


    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight setKc(double kc) {
        this.kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        this.kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        this.kq = kq;
        return this;

    }
}
