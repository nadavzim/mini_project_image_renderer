package geometries;

import primitives.Ray;

import java.util.List;

/**
 * class to contain the proper methods for setting a bounding box for both geometry and geometries
 */
public abstract class Container extends Intersectable {

    /**
     * every Intersectable composite have his bounding volume, which represented by a bounding box
     */
    public BoundingBox boundingBox; // = null as default

    /**
     * method of checking if bounding region exists and if the ray intersections it,
     * only ray value input.
     *
     * @param ray the ray which we about to check for intersection with it and some geometries which in her way
     * @return list of intersection points with the ray and the geometries,
     * calls origin function of for calculating the points
     */
    public List<GeoPoint> findIntersectBoundingRegion(Ray ray, double max) {
        if (boundingBox == null || boundingBox.intersectBV(ray)) {
            return findGeoIntersections(ray, Double.POSITIVE_INFINITY, true);
        }
        return null;
    }

    /**
     * method sets the values of the bounding volume of the intersectable component
     * this implementation is for constructing new bounding box if necessary/needed
     */
    public void setBoundingBox() {
        if (boundingBox == null) {
            boundingBox = new BoundingBox();
        }
    }
}
