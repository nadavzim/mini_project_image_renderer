package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

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
        return null;
    }
}
