package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a tube shape in 3D space, which is defined by its radius and axis Ray.
 * It extends RadialGeometry class and adds an axis Ray field.
 */
public class Tube extends RadialGeometry {
    Ray axisRay;

    /**
     * Constructs a new Tube object with a given radius and axis Ray.
     *
     * @param radius  The radius of the tube.
     * @param axisRay The axis Ray of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axis Ray of the tube.
     *
     * @return The axis Ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns null since the normal of the tube at any point is not yet implemented.
     *
     * @param point The point to get the normal to the tube at.
     * @return The normal vector at the specified point on the tube surface.
     */
    @Override
    public Vector getNormal(Point point) {
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();

        Vector vec = point.subtract(p0);
        double t = v.dotProduct(vec);
        //t = alignZero(t);

        //if t=0, then t*v is the zero vector and o=p0.
        Point o = p0;

        if (!isZero(t)) {
            o = p0.add(v.scale(t));
        } else
            return vec.normalize();
        if (point.equals(o))
            throw new IllegalArgumentException("point cannot be on the tube axis");
        Vector res = point.subtract(o).normalize();
        return res;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance, boolean bb) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
            if (isZero(dirV))
                return List.of(new GeoPoint(this, ray.getPoint(radius)));

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(new GeoPoint(this, ray.getPoint(Math.sqrt(radius * radius
                    / dir.subtract(v.scale(dir.dotProduct(v)))
                    .lengthSquared()))));
        }

        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (isZero(a)) {
            if (isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(-c / b))); // if it's linear, there's a solution.
        }

        double discriminant = alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            List<GeoPoint> _points = new ArrayList<>(2);
            _points.add(new GeoPoint(this, ray.getPoint(t1)));
            _points.add(new GeoPoint(this, ray.getPoint(t2)));
            return _points;
        } else if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            List<GeoPoint> _points = new ArrayList<>(1);
            _points.add(new GeoPoint(this, ray.getPoint(t1)));
            return _points;
        } else if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            List<GeoPoint> _points = new ArrayList<>(1);
            _points.add(new GeoPoint(this, ray.getPoint(t2)));
            return _points;
        }
        return null;
    }
}
