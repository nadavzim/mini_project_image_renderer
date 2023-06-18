package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.*;
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

    private boolean softShadow = false;

    /**
     * Constructor for RayTracerBasic
     *
     * @param scene The scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    public boolean isSoftShadow() {
        return softShadow;
    }

    public RayTracerBasic setSoftShadow(boolean softShadow) {
        this.softShadow = softShadow;
        return this;
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
     * @param ray          the ray that was sent from the camera to the scene
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
     * @param ray          the ray that intersects the object
     * @param level        the recursion level.
     * @param k            how much to take the calculated color
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
     * @param gp    The closest intersection point.
     * @param v     the ray's direction
     * @param level the recursion level.
     * @param k     how much to take the calculated color
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
     * <p>
     * The function is recursive, and the recursion stops when the level reaches 0
     *
     * @param ray   the ray that we're currently tracing
     * @param level the recursion level.
     * @param kx    how much to take the calculated color
     * @param kkx   the attenuation factor of the light source
     * @return The color of the closest intersection point.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    /**
     * function to find geo point closest to starting point of the ray
     * from all of the intersections with an object
     *
     * @param ray - the tested ray
     * @return the point closest to the ray's starting point
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        List<GeoPoint> intersections;

        if (scene.isBb()) {
            intersections = scene.geometries.findGeoIntersections(ray);
        } else {
            intersections = scene.geometries.findIntersectBoundingRegion(ray, Double.POSITIVE_INFINITY);
        }

        if (intersections == null || intersections.size() == 0) {
            return null;
        } else {
            return ray.findClosestGeoPoint(intersections);
        }
    }

    /**
     * Construct and return a reflected ray
     *
     * @param point The point of intersection between the ray and the object
     * @param v     the vector from the point to the light source
     * @param n     the normal vector of the point of intersection
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        // r = v - 2 *(v*n)*n
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(point, n, r);
    }

    /**
     * Construct and return a refracted ray
     *
     * @param point The point of intersection between the ray and the object
     * @param v     the vector from the point to the light source
     * @param n     the normal vector of the point of intersection
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }


    /**
     * calculate Kd * |l.dorProduct(n)| * +Ks * max(0 ,(-v).dotProduct(r)) ** nShinines * Il
     * from phong model (specular and diffusive light) for each light in scene
     * adds the color from all light to returned result
     *
     * @param intersection {@link GeoPoint} to calculate color at
     * @param ray          {@link  Ray} from camera to the point
     * @param k            coefficient for transparency and reflectiveness
     * @return {@link Color} of the shape at the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        // (Kd * |l.dorProduct(n)| ) * Il + (Ks * max(0 ,(-v).dotProduct(r)) ** nShinines) * Il
        // l = vector from light source to the point
        // n = normal vector to shape at point
        // r = specular vector to vector from light to point
        // v = ray from camera to point

        Color color = intersection.geometry.getEmission();

        // v
        Vector v = ray.getDir();
        // n
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        //  nShininess
        int nShininess = intersection.geometry.getMaterial().getShininess();
        // Kd
        Double3 kD = intersection.geometry.getMaterial().getKd();
        // Ks
        Double3 kS = intersection.geometry.getMaterial().getKs();
        // loop through all light sources in scene

        var lights = scene.getLights();
        if (softShadow) {
            for (var lightSource : lights) {
                Color colorBeam = Color.BLACK;
                var vectors = lightSource.getListL(intersection.point);
                for (var l : vectors) {

                    // l.dorProduct(n)
                    double nl = alignZero(n.dotProduct(l));
                    // check that light direction is towards shape and not behind
                    if (nl * nv > 0) { // sign(nl) == sing(nv)

                        Double3 ktr = transparency(intersection, lightSource, l, n);
                        if (ktr.scale(k).greaterThan(MIN_CALC_COLOR_K)) {
                            Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                            // (Kd * |l.dorProduct(n)|) * Il
                            colorBeam = colorBeam.add(calcDiffusive(kD, nl, lightIntensity),
                                    // (Ks * max(0 ,(-v).dotProduct(r)) ** nShinines ) * Il
                                    calcSpecular(kS, nl, l, n, v, nShininess, lightIntensity));
                        }
                    }
                }
                color = color.add(colorBeam.reduce(vectors.size()));
            }
        } else {
            for (var lightSource : lights) {
                // l
                Vector l = lightSource.getL(intersection.point);
                // l.dorProduct(n)
                double nl = alignZero(n.dotProduct(l));
                // check that light direction is towards shape and not behind
                if (nl * nv > 0) { // sign(nl) == sing(nv)

                    Double3 ktr = transparency(intersection, lightSource, l, n);
                    if (ktr.scale(k).greaterThan(MIN_CALC_COLOR_K)) {
                        Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                        // (Kd * |l.dorProduct(n)|) * Il
                        color = color.add(calcDiffusive(kD, nl, lightIntensity),
                                // (Ks * max(0 ,(-v).dotProduct(r)) ** nShinines ) * Il
                                calcSpecular(kS, nl, l, n, v, nShininess, lightIntensity));
                    }
                }
            }
        }
        return color;
    }


    /**
     * calculate  (Ks * max(0 ,(-v).dotProduct(r)) ** nShinines )* Il
     * from phong model
     *
     * @param kS             attenuation factor of specular light
     * @param nDotL          l.dorProduct(n)
     * @param l              vector from light source to the point
     * @param n              normal vector to shape at point
     * @param v              ray from camera to point
     * @param nShininess     shininess factor of shape
     * @param lightIntensity Il
     * @return Il scaled by  factor
     */
    private Color calcSpecular(Double3 kS, double nDotL, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        // calculating (Ks * max(0 ,(-v).dotProduct(r)) ** nShinines )* Il

        // r = L - (2 *  l.dorProduct(n)) * n
        Vector r = l.subtract(n.scale(2 * nDotL)).normalize();
        // -v
        Vector minusV = v.scale(-1);
        // max(0 ,(-v).dotProduct(r))
        double specular = max(0, minusV.dotProduct(r));
        //** nShinines
        if (specular != 0)
            specular = pow(specular, nShininess);
        // Il scaled by factor
        return lightIntensity.scale(kS.scale(specular));
    }

    /**
     * calculate  (Kd * |l.dorProduct(n)|) * Il
     * from phong model
     *
     * @param kD             attenuation factor of diffusive light
     * @param nDotL          l.dorProduct(n)
     * @param lightIntensity Il
     * @return Il scaled by factor
     */
    private Color calcDiffusive(Double3 kD, double nDotL, Color lightIntensity) {
        // Kd * |l.dorProduct(n)|
        if (nDotL == 0)
            return lightIntensity.scale(0);
        else
            nDotL = abs(nDotL);
        // Il scaled by factor
        return lightIntensity.scale(kD.scale(nDotL));
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

        for (var intersection : intersections) {
            if (intersection.geometry.getMaterial().getKt().lowerThan(MIN_CALC_COLOR_K))
                return false;
        }
        return true;
    }


    /**
     * calculate transparency of a point (shade)
     *
     * @param gp    {@link GeoPoint} to calculate transparency for
     * @param light {@link LightSource} lighting towards the geometry
     * @param l     normal {@link Vector} to geometry at the point
     * @param n     light direction {@link Vector} (the original ray)
     * @return {@link Double3} value of transparency at point
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        // create a vector by scaling  light direction vector to opposite direction
        // now originating from point towards light
        Vector lightScaled = l.scale(-1);
        // construct a new ray using the scaled vector from the point towards ray
        // slightly removed from original point by epsilon (in Ray class)
        Ray shadowRay = new Ray(gp.point, n, lightScaled);
        // get distance from the light to the point
        double lightDistance = light.getDistance(shadowRay.getP0());
        // check if new ray intersect a geometry between point and the light source
        // further objects behind the light are avoided by distance parameter
        List<GeoPoint> intersections;
        if (!scene.isBb()) {
            intersections = scene.geometries.findGeoIntersections(shadowRay, lightDistance);
        } else {
            intersections = scene.geometries.findIntersectBoundingRegion(shadowRay, lightDistance);
        }
        // point is not shaded - return transparency level of 1
        if (intersections == null)
            return Double3.ONE;
        // point is shaded - iterate through intersection points and add the shade effect from geometry
        //to transparency level at point
        Double3 ktr = Double3.ONE;
        for (var geoPoint : intersections) {
            ktr = ktr.scale(geoPoint.geometry.getMaterial().getKt());
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        // return the transparency
        return ktr;
    }


    /**
     * a func that get list of rays and sum the average color of all of them
     *
     * @param rays the given list of rays to trace
     * @return The average color of the rays
     */
    public Color calcAverageColor(List<Ray> rays) {
        Color totalColor = Color.BLACK;
        for (Ray ray : rays) {
            totalColor = totalColor.add(traceRay(ray));
        }
        return totalColor.scale((1 / (Double.valueOf(rays.size())))); // Calculates the average color
    }

}