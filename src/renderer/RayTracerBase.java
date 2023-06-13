package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor
     * @param myScene       the scene
     */
    public RayTracerBase(Scene myScene) {
        scene = myScene;
    }

    /**
     * abstract function that trace the ray
     * @param ray the ray
     * @return the color of the ray
     */
    public abstract Color traceRay(Ray ray);
    public abstract Color calcAverageColor(List<Ray> rays);

    }
