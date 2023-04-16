package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

/**
 * This class represents a tube shape in 3D space, which is defined by its radius and axis Ray.
 * It extends RadialGeometry class and adds an axis Ray field.
 */
public class Tube extends RadialGeometry{
    Ray axisRay;

    /**
     * Constructs a new Tube object with a given radius and axis Ray.
     *
     * @param radius   The radius of the tube.
     * @param axisRay  The axis Ray of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        if(radius<=0)
            throw new IllegalArgumentException("This radius is not legal!");
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

        Vector v= axisRay.getDir();
        Point p0 =axisRay.getP0();

        double t= v.dotProduct(point.subtract(p0));

        //if t=0, then t*v is the zero vector and o=p0.
        Point o=p0;

        if(!isZero(t))
        {
            o=p0.add(v.scale(t));
        }

        return point.subtract(o).normalize();
    }
}
