package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    List<Intersectable> geometries;

    public Geometries(Intersectable... geometries){

    }
    public Geometries(){
        geometries = new LinkedList<Intersectable>();
    }

    public void add(Intersectable... geometries){

    }
    /**
     * @param ray the Ray to test for intersection with the Intersectable object.
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }

}
