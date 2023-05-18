package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;


public class RayTracerBasic extends RayTracerBase{
    /**
     * constructor
     * @param scene       the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * abstract function that trace the ray
     *
     * @param ray the ray
     * @return the color of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersectionsHelper(ray);
        if (intersections == null)
            return scene.background;
        else {
            GeoPoint closest = ray.findClosestGeoPoint(intersections);
            return calcColor(closest);
        }
    }

    private Color calcColor(GeoPoint geoPoint){
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission());

    }
}
