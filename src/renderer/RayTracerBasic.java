package renderer;

import primitives.Color;
import primitives.Point;
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
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        if (intersections == null)
            return scene.background;
        else {
            Point closest = ray.findClosestPoint(intersections);
            return calcColor(closest);
        }
    }

    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
