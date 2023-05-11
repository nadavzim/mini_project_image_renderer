package primitives;

import java.util.List;

public class Ray {
     Point p0;
     Vector dir;

    /**
     * Constructs a Ray object with the given starting point and direction.
     * The direction vector is normalized.
     * @param p0  the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
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
    public Point findClosestPoint(List<Point> points){
        if (points.size() == 0)
            return null;
        double closest = p0.distance(points.get(0));
        int index = 0;
        for (int i = 1; i < points.size(); i++) {
            if (p0.distance(points.get(i)) < closest) {
                closest = p0.distance(points.get(i));
                index = i;
            }
        }
        return points.get(index);
    }
}



