package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;


/**
 * The Intersect interface defines an objects that can be intersected by a ray in 3D space.
 */
public abstract class Intersectable {
    //    public final List<GeoPoint> findGeoIntersections(Ray ray) {
//        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
//    }
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY, false);
    }

    /**
     * Helper method for finding geometric intersections between a ray and the object.
     * This method is implemented by subclasses of Geometry.
     *
     * @param ray         The Ray representing the ray to intersect with the object.
     * @param maxDistance The maximum distance to search for intersections.
     * @return A List of GeoPoint objects representing the geometric intersections found.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance, boolean bb);


    /**
     Finds geometric intersections between a ray and the object using the helper method.
     @param ray The Ray representing the ray to intersect with the object.
     @param maxDistance The maximum distance to search for intersections.
     @return A List of GeoPoint objects representing the geometric intersections found.
     */

    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance, true);
    }

    /**
     * Finds intersections between a ray and the object, returning a list of points.
     *
     * @param ray The Ray representing the ray to intersect with the object.
     * @return A List of Point objects representing the intersections found.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * @param ray         - ray that cross the geometry
     * @param maxDistance - the upper bound of distance, any point which
     *                    its distance is greater than this bound will not be returned
     * @param bb          boolean for bounding box
     * @return list of intersection points that were found and has valid distance value
     */

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean bb) {
        return findGeoIntersectionsHelper(ray, maxDistance, true); // change the bb to true for the bvh improvement
    }
}
