package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Ray {
    private static final double DELTA = 0.1;
    Point p0;
    Vector dir;


    /**
     * Constructs a Ray object with the given starting point and direction.
     * The direction vector is normalized.
     *
     * @param p0  the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Constructor for ray deflected by epsilon
     *
     * @param p0  origin
     * @param n   normal vector
     * @param dir direction
     */
    public Ray(Point p0, Vector n, Vector dir) {
        this.p0 = p0.add(n.scale(n.dotProduct(dir) > 0 ? DELTA : -DELTA));
        this.dir = dir.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Checks if this Ray object is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Ray other)
            return this.dir.equals(other.dir) && this.p0.equals(other.p0);
        return false;
    }

    /**
     * Returns a string representation of the Ray object.
     *
     * @return a string representation of the Ray object
     */
    @Override
    public String toString() {
        return "Ray :" + "p0=" + p0 + ", dir=" + dir;
    }

    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * returns the closest point from the ray p0 point .
     *
     * @param points the list of points
     * @return the closest point from the ray base
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public Intersectable.GeoPoint findClosestGeoPoint(List<Intersectable.GeoPoint> points) {
        if (points.size() == 0)
            return null;
        double close = p0.distanceSquared(points.get(0).point);
        int index = 0;
        for (int i = 1; i < points.size(); i++) {
            if (p0.distanceSquared(points.get(i).point) < close) {
                close = p0.distanceSquared(points.get(i).point);
                index = i;
            }
        }
        return points.get(index);
    }

    /**
     * auxiliary function to randomly scatter points within a rectangular surface.
     * returns a list of rays which relates to the surface.
     *
     * @param numRays     - number of rays we create in the rectangular surface.
     * @param vUp         - upper vector of rectangular surface.
     * @param vRight      - right vector of rectangular surface.
     * @param dist        - of the camera from the view plane
     * @param pixelWidth  - the width of a single pixel in view plane
     * @param pixelHeight - the height of a single pixel in view plane
     * @return list of rays from the area of the pixel to the scene
     */
    public List<Ray> randomRaysInGrid(Vector vUp, Vector vRight, int numRays, double dist, double pixelWidth, double pixelHeight) {
        List<Ray> rays = new LinkedList<>();

        // the starting point of the original vector
        Point p0 = getP0();

        // the center of the pixel
        Point pixelCenter = p0.add(dir.scale(dist));

        // calculate the maximal number of rays which we can compress in one dimension
        // if we don't make this calculation, we might add too many rays, which will decrease performance
        // if the sqrt is not an integer, it will be the floor value
        // (mostly, unless the number is x.99...99 with 15 9s or more, which will probably never happen)
        int raysInDimension = (int) Math.sqrt(numRays);

        // the size of one step from one ray to another in each dimension
        double xMove = pixelWidth / raysInDimension;
        double yMove = pixelHeight / raysInDimension;

        // the starting point is the right bottom corner of the pixel
        Point cornerOfPixel = pixelCenter
                .add(vRight.scale(pixelWidth / 2d))
                .add(vUp.scale(pixelHeight / 2d));

        Point newRayStartPoint;

        int sign = 1;
        Random rand = new Random();

        for (int i = 0; i < raysInDimension; ++i) {
            for (int j = 0; j < raysInDimension; ++j, sign *= -1) {

                newRayStartPoint = cornerOfPixel;

                // determine by how much we move the point in each iteration
                double randomMoveX = xMove / 2d + xMove * rand.nextDouble();
                double randomMoveY = yMove / 2d + yMove * rand.nextDouble();

                // move in the x dimension
                if (!Util.isZero(i * randomMoveX)) {
                    newRayStartPoint = newRayStartPoint.add(vRight.scale((i) * randomMoveX));
                }

                // move in the y dimension
                if (!Util.isZero(j * randomMoveY)) {
                    newRayStartPoint = newRayStartPoint.add(vUp.scale((j) * randomMoveY));
                }

//                // if we have reach the center point of the pixel
//                if (i == raysInDimension / 2 && j == i) {
//                    newRayStartPoint = pixelCenter;
//                }

                // make sure we do not add the canter ray more than once
                if (!newRayStartPoint.equals(pixelCenter)) {
                    rays.add(new Ray(p0, (newRayStartPoint.subtract(p0)))); // normalized inside Ray constructor
                }

            }
        }
        return rays;
    }

}



