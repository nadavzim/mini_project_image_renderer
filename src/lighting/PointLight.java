package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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

    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(position);
        return getIntensity().scale(1/(kc + kl * d + kq * d * d));
    }

    /**
     * Returns the vector from the light source to the given point.
     *
     * @param p the point to get the vector to
     * @return the vector from the light source to the given point
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * @param point
     * @return
     */
    @Override
    public double getDistance(Point point) {
        return this.position.distanceSquared(point);
    }
}
