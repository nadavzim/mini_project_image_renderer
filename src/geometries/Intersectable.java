package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;


/**
 * The Intersect interface defines an objects that can be intersected by a ray in 3D space.
 */
public abstract class Intersectable {
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

//    public final List<GeoPoint> findGeoIntersections(Ray ray) {
//        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
//    }
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     Helper method for finding geometric intersections between a ray and the object.
     This method is implemented by subclasses of Geometry.
     @param ray The Ray representing the ray to intersect with the object.
     @param maxDistance The maximum distance to search for intersections.
     @return A List of GeoPoint objects representing the geometric intersections found.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     Finds geometric intersections between a ray and the object using the helper method.
     @param ray The Ray representing the ray to intersect with the object.
     @param maxDistance The maximum distance to search for intersections.
     @return A List of GeoPoint objects representing the geometric intersections found.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     Finds intersections between a ray and the object, returning a list of points.
     @param ray The Ray representing the ray to intersect with the object.
     @return A List of Point objects representing the intersections found.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
//    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}