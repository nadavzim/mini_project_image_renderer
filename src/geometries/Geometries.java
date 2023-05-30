package geometries;
import java.util.Arrays;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable{
    List<Intersectable> geometries;

    public Geometries(Intersectable... geometries){
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }
    public Geometries(){
        geometries = new LinkedList<>();
    }
    public void add(Intersectable... geometries){
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * @param ray a ray to find intersections with.
     * @return a list of points that are intersections of the ray and the geometries that in "geometries" list.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> res = null;
        for (Intersectable geometry : this.geometries) {
            List<GeoPoint> resi = geometry.findGeoIntersections(ray, maxDistance);
            if (resi != null) {
                if (res == null) {
                    res = new LinkedList<GeoPoint>();
                }
                res.addAll(resi);
            }
        }
        return res;
    }
}