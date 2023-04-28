package geometries;
import java.util.Arrays;
import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
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
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;

        for (Intersectable geo : geometries) {
            List<Point> geoPoints = geo.findIntersections(ray);

            if (geoPoints != null) {

                if (result == null) {
                    result = new LinkedList<>();
                }

                result.addAll(geoPoints);
            }
        }

        return result;

    }

}
