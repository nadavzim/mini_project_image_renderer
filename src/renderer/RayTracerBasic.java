package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    /**
     * Defines the maximum recursion level of the `calcColor` function.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * Defines the minimum value of the k coefficient.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Defines the initial value of the k coefficient.
     */
    private static final Double3 INITIAL_K = Double3.ONE;


    /**
     * Constructor for RayTracerBasic
     *
     * @param scene The scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Given a ray, trace it through the scene and return the color of the pixel that it hits.
     *
     * @param ray The ray to trace.
     * @return A color object.
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return scene.getBackground();
        }
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.getBackground() : calcColor(closestPoint, ray);
    }

    /**
     * It calculates the color of a point on a surface, by calculating the color of the point, and adding the ambient light
     * to it
     *
     * @param closestPoint The closest point to the ray's head.
     * @param ray the ray that was sent from the camera to the scene
     * @return The color of the closest point.
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }

    /**
     * It calculates the color of a point on the scene by calculating the local effects (diffuse, specular, ambient) and
     * adding them to the global effects (reflection and refraction)
     *
     * @param intersection The point of intersection between the ray and the object.
     * @param ray the ray that intersects the object
     * @param level the recursion level.
     * @param k how much to take the calculated color
     * @return The color of the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

    /**
     * It calculates the color of the point by calculating the color of the reflected ray and the color of the refracted
     * ray
     *
     * @param gp The closest intersection point.
     * @param v the ray's direction
     * @param level the recursion level.
     * @param k how much to take the calculated color
     * @return The color of the point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = material.getKr().product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.getKr(), kkr);
        Double3 kkt = material.getKt().product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.getKt(), kkt));
        return color;
    }

    /**
     * If there's no intersection, return the background color, otherwise return the color of the intersection point,
     * scaled by the kx(kt/kr) coefficient.
     *
     * The function is recursive, and the recursion stops when the level reaches 0
     *
     * @param ray the ray that we're currently tracing
     * @param level the recursion level.
     * @param kx how much to take the calculated color
     * @param kkx the attenuation factor of the light source
     * @return The color of the closest intersection point.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level-1, kkx)
        ).scale(kx);
    }

    /**
     * It finds the closest intersection point of a ray with the scene's geometries
     *
     * @param ray The ray that we want to find the closest intersection to.
     * @return The closest intersection point.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if(intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections); //returns closest point
    }

    /**
     * Construct and return a reflected ray
     *
     * @param point The point of intersection between the ray and the object
     * @param v the vector from the point to the light source
     * @param n the normal vector of the point of intersection
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        double vn = v.dotProduct(n);

        if (vn == 0){
            return null;
        }

        // r = v - 2 *(v*n)*n
        Vector r = v.subtract(n.scale(2*vn));
        return new Ray(point, n, r);
    }

    /**
     * Construct and return a refracted ray
     *
     * @param point The point of intersection between the ray and the object
     * @param v the vector from the point to the light source
     * @param n the normal vector of the point of intersection
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }


    /**
     * Calculates local effects of light sources on a certain point
     *
     * @param gp  The intersection point
     * @param ray the ray that hit the geometry
     * @return The color of the point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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

                Double3 ktr = transparency(gp,lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K) ) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
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

    /**
     * The function checks returns if the point is unshaded or shaded.
     *
     * @param gp          The point on the geometry that we're shading
     * @param lightSource The light source that we're checking if it's shaded or not.
     * @param l           The vector from the point to the light source
     * @param n           the normal vector of the point
     * @return true if the point is unshaded, and false if it is shaded.
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Point point = gp.point;
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(point, n, lightDirection);

        // Calculates the maximum distance from the ray to the surface
        double maxDistance = lightSource.getDistance(point);

        // Get the intersections
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        if (intersections == null)
            return true;

        for (var intersection: intersections) {
            if(intersection.geometry.getMaterial().getKt().lowerThan(MIN_CALC_COLOR_K))
                return false;
        }
        return true;
    }

    /**
     * The function calculates the transparency of the point.
     *
     * @param gp          The point on the surface of the geometry
     * @param lightSource The light source
     * @param l           The vector from the point to the light source
     * @param n           The normal vector of the point
     * @return The transparent level of the point, between 0 and 1.
     */
    private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Point point = gp.point;

        // This is a ray that is sent from the point to the light source.
        Ray lightRay = new Ray(point, n, lightDirection);

        // Calculates the maximum distance from the ray to the surface
        double maxDistance = lightSource.getDistance(point);

        // Get the intersections
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        // loop over intersections and for each intersection which is closer to the
        // point than the light source multiply ktr by 𝒌𝑻 of its geometry.
        // Performance: if you get close to 0 – it’s time to get out (return 0)
        for (var geo : intersections) {
            ktr = ktr.product(geo.geometry.getMaterial().getKt());
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }
    /**
     * a func that get list of rays and sum the average color of all of them
     * @param rays the given list of rays to trace
     * @return The average color of the rays
     */
    public Color calcAverageColor(LinkedList<Ray> rays) {
        Color totalColor = Color.BLACK;
        for (Ray ray : rays) {
            totalColor = totalColor.add(traceRay(ray));
        }
        return totalColor.scale((1 / (Double.valueOf(rays.size())))); // Calculates the average color
    }

}