package geometries;

import primitives.Ray;

import java.util.List;

/**
 * this class represents all kinds of flat shapes - plane, polygon, triangle
 */
public abstract class FlatGeometry extends Geometry {
    /**
     * Helper method for finding geometric intersections between a ray and the object.
     * This method is implemented by subclasses of Geometry.
     *
     * @param ray         The Ray representing the ray to intersect with the object.
     * @param maxDistance The maximum distance to search for intersections.
     * @param bb
     * @return A List of GeoPoint objects representing the geometric intersections found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance, boolean bb) {
        return null;
    }
}