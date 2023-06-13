package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import geometries.Sphere;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kc = 1d;
    private double kl = 0d;
    private double kq = 0d;
    private Double radius=100d;

    public PointLight(Color intensity, Point position, Double radius) {
        super(intensity);
        this.position = position;
        this.radius = radius;
    }

    public PointLight setRadius(Double radius) {
        this.radius = radius;
        return this;
    }


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
     * @param point the point to get the distance to
     * @return the distance between the light source and the given point
     */
    @Override
    public double getDistance(Point point) {
        return this.position.distance(point);
    }
    @Override
    public List<Vector> getListL(Point p) {
        Random r = new Random();
        List<Vector> vectors = new LinkedList();
        for (double i = - radius; i < radius; i += radius / 10) {
            for (double j = - radius; j < radius; j += radius / 10) {
                if (i != 0 && j != 0) {
                    Point point = position.add(new Vector(i, j,0.1d));
                    if (point.equals(position)){
                        vectors.add(p.subtract(point).normalize());
                    }
                    else{
                        try{
                            if (point.subtract(position).dotProduct(point.subtract(position)) <= radius * radius){
                                vectors.add(p.subtract(point).normalize());
                            }
                        }
                        catch (Exception e){
                            vectors.add(p.subtract(point).normalize());
                        }

                    }
                }

            }
        }
        vectors.add(getL(p));
        return vectors;
    }
}
