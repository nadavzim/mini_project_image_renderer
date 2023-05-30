package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Ray;
import scene.Scene;
import lighting.LightSource;


import java.util.List;

import static primitives.Util.alignZero;


public class RayTracerBasic extends RayTracerBase{

    /**
     * The delta for the shadow rays
     */
    private static final double DELTA = 0.1;

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
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        else {
            GeoPoint closest = ray.findClosestGeoPoint(intersections);
            return calcColor(closest, ray);
        }
    }
    /**
     * Calculates the color of a given point on the scene
     *
     * @param gp The point on the geometry that we're calculating the color for.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.getAmbientLight().getIntensity()
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * Calculates local effects of light sources on a certain point
     *
     * @param gp The intersection point
     * @param ray the ray that hit the geometry
     * @return The color of the point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);

        double nv = alignZero(n.dotProduct(v));

        // This is a check to see if the ray is hitting the geometry from the inside.
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();

        // Calculates the color of a point on a surface,
        // by adding the emission of the surface to the sum of
        // the diffuse and specular colors of the surface
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }

        return color;
    }

    /**
     * Calculate the specular component of the light reflected from the surface of the object.
     *
     * @param material the material of the object
     * @param n        normal vector
     * @param l        direction from light to point
     * @param nl       dot-product of the normal vector and the light vector
     * @param v        view vector
     * @return The specular component factor.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.add(n.scale(-2 * nl)); // nl must be not zero!
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0)
            return Double3.ZERO; // view from direction opposite to r vector
        return material.getKs().scale(Math.pow(minusVR, material.getShininess()));
    }

    /**
     * Calculates Diffusive component of light reflection
     *
     * @param material The material of the object that the ray hit.
     * @param nl       the dot-product of the normal and the light direction
     * @return The diffuse component factor.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        nl = Math.abs(nl);
        return material.getKd().scale(nl);
    }
//    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light){
//
//
//    }

}
