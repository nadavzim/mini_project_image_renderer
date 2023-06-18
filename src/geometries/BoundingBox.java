package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;


/**
 * class represents axis-aligned bounding box, it is used to check if ray is in the area of a geometry
 * by checking if the ray direction come with intersection in the bounding box of the geometry.
 * It means to us that the calculation of all the intersections of the same ray should be taken into account
 */
public class BoundingBox {

    /**
     * xMin - the minimum value of X coordinate of this bounding box
     */
    private double xMin = Double.POSITIVE_INFINITY;
    /**
     * yMin - the minimum value of Y coordinate of this bounding box
     */
    private double yMin = Double.POSITIVE_INFINITY;
    /**
     * zMin - the minimum value of Z coordinate of this bounding box
     */
    private double zMin = Double.POSITIVE_INFINITY;
    /**
     * xMax - the maximum value of X coordinate of this bounding box
     */
    private double xMax = Double.NEGATIVE_INFINITY;
    /**
     * yMax - the maximum value of Y coordinate of this bounding box
     */
    private double yMax = Double.NEGATIVE_INFINITY;
    /**
     * zMax - the maximum value of Z coordinate of this bounding box
     */
    private double zMax = Double.NEGATIVE_INFINITY;


    /**
     * Setter for new values for class BoundingBox, with 6 inputs, minimum value and maximum value inputs per axis
     *
     * @param x1 minimum/maximum value in x axis
     * @param x2 minimum/maximum value in x axis
     * @param y1 minimum/maximum value in y axis
     * @param y2 minimum/maximum value in y axis
     * @param z1 minimum/maximum value in z axis
     * @param z2 minimum/maximum value in z axis
     */
    public void setBoundingBox(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.xMin = Math.min(x1, x2);
        this.xMax = Math.max(x1, x2);
        this.yMin = Math.min(y1, y2);
        this.yMax = Math.max(y1, y2);
        this.zMin = Math.min(z1, z2);
        this.zMax = Math.max(z1, z2);
    }


    /**
     * get min X value
     *
     * @return min x
     */
    public double getMinX() {
        return xMin;
    }

    /**
     * get max X value
     *
     * @return max x
     */
    public double getMaxX() {
        return xMax;
    }

    /**
     * get min y value
     *
     * @return min y
     */
    public double getMinY() {
        return yMin;
    }

    /**
     * get max y value
     *
     * @return max y
     */
    public double getMaxY() {
        return yMax;
    }

    /**
     * get min z value
     *
     * @return min z
     */
    public double getMinZ() {
        return zMin;
    }

    /**
     * get max z value
     *
     * @return max z
     */
    public double getMaxZ() {
        return zMax;
    }

    /**
     * Function which checks if a ray intersects the bounding region
     *
     * @param ray the ray to check for intersection
     * @return boolean result, true if intersects, false otherwise
     * @author Naor Barkochva
     */
    public boolean intersectBV(Ray ray) {
        Point p0 = ray.getP0();
        Point dirHead = ray.getDir().getHead();

        // the coordinates of the ray direction
        double dirHeadX = dirHead.getX();
        double dirHeadY = dirHead.getY();
        double dirHeadZ = dirHead.getZ();

        // the coordinates of the ray starting point
        double rayStartPointX = p0.getX();
        double rayStartPointY = p0.getY();
        double rayStartPointZ = p0.getZ();

        // define default variables for calculations
        double txMin;
        double txMax;
        double tyMin;
        double tyMax;
        double tzMin;
        double tzMax;

        // for all 3 axes:
        //
        // calculate the intersection distance t0 and t1
        // (t_Min represent the min and t_Max represent the max)
        //
        //  1. when the values for t are negative, the box is behind the ray (no need to find intersections)
        //  2. if the ray is parallel to an axis it will not intersect with the bounding volume plane for this axis.
        //  3. we first find where the ray intersects the planes defined by each face of the bounding cube,
        //     after that, we find the ray's first and second intersections with the planes.

        if (dirHeadX > 0) {
            txMax = (xMax - rayStartPointX) / dirHeadX;
            if (txMax <= 0) {
                return false; // if value for t_Max is negative, the box is behind the ray.
            }
            txMin = (xMin - rayStartPointX) / dirHeadX;
        } else if (dirHeadX < 0) {
            txMax = (xMin - rayStartPointX) / dirHeadX;
            if (txMax <= 0) {
                return false; // if value for t_Max is negative, the box is behind the ray.
            }
            txMin = (xMax - rayStartPointX) / dirHeadX;
        } else { // preventing parallel to the x-axis
            txMax = Double.POSITIVE_INFINITY;
            txMin = Double.NEGATIVE_INFINITY;
        }

        if (dirHeadY > 0) {
            tyMax = (yMax - rayStartPointY) / dirHeadY;
            if (tyMax <= 0) {
                return false; // if value for tyMax is negative, the box is behind the ray.
            }
            tyMin = (yMin - rayStartPointY) / dirHeadY;
        } else if (dirHeadY < 0) {
            tyMax = (yMin - rayStartPointY) / dirHeadY;
            if (tyMax <= 0) {
                return false; // if value for tyMax is negative, the box is behind the ray.
            }
            tyMin = (yMax - rayStartPointY) / dirHeadY;
        } else { // preventing parallel to the y-axis
            tyMax = Double.POSITIVE_INFINITY;
            tyMin = Double.NEGATIVE_INFINITY;
        }
        // cases where the ray misses the cube
        // the ray misses the box when t0x is greater than t1y and when t0y is greater than  t1x
        if ((txMin > tyMax) || (tyMin > txMax)) {
            return false;
        }

        // we find which one of these two points lie on the cube by comparing their values:
        // we simply need to choose the point which value for t is the greatest.
        if (tyMin > txMin)
            txMin = tyMin;
        // we find the second point where the ray intersects the box
        // we simply need to choose the point which value for t is the smallest
        if (tyMax < txMax)
            txMax = tyMax;

        if (dirHeadZ > 0) {
            tzMax = (zMax - rayStartPointZ) / dirHeadZ;
            if (tzMax <= 0) {
                return false; // if value for tzMax is negative, the box is behind the ray.
            }
            tzMin = (zMin - rayStartPointZ) / dirHeadZ;
        } else if (dirHeadZ < 0) {
            tzMax = (zMin - rayStartPointZ) / dirHeadZ;
            if (tzMax <= 0) {
                return false; // if value for tzMax is negative, the box is behind the ray.
            }
            tzMin = (zMax - rayStartPointZ) / dirHeadZ;
        } else { // preventing parallel to the z axis
            tzMax = Double.POSITIVE_INFINITY;
            tzMin = Double.NEGATIVE_INFINITY;
        }

        // cases where the ray misses the cube
        // the ray misses the box when t0 is greater than t1z and when t0z is greater than  t1
        return (!(txMin > tzMax)) && (!(tzMin > txMax));
    }

    /**
     * calculate volume of BoundingBox
     *
     * @return the volume of bounding box
     */
    public double size() {
        return (xMax - xMin) * (yMax - yMin) * (zMax - zMin);
    }


    /**
     * function to get the center of the bounding box
     *
     * @return the Point in the middle of the bounding box
     */
    public Point getBoundingBoxCenter() {
        return new Point(
                (getMaxX() + getMinX()) / 2,
                (getMaxY() + getMinY()) / 2,
                (getMaxZ() + getMinZ()) / 2
        );
    }

    /**
     * function to get the distance between the centers of two bounding boxes
     *
     * @param boundingBox - the other bounding box
     * @return the distance between the center of the boxes
     */
    public double BoundingBoxDistance(BoundingBox boundingBox) {
        return this.getBoundingBoxCenter().distance(boundingBox.getBoundingBoxCenter());
    }

    /**
     * creates string which identify the values of the bounding region
     *
     * @return a string of minimum Vector and maximum Vector
     */
    @Override
    public String toString() {
        return "minimum : (" + xMin + " " + yMin + " " + zMin + ") \n" +
                "maximum : (" + xMax + " " + yMax + " " + zMax + ")";
    }


    /**
     * Bounding Box method equals implementation
     *
     * @param obj - another object to compare with
     * @return boolean result (same values of the bounding boxes)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BoundingBox other)) return false;
        // Purpose: Make sure that all vertices of bounding boxes are equal:
        return Util.isZero(xMin - other.xMin) &&
                Util.isZero(yMin - other.yMin) &&
                Util.isZero(zMin - other.zMin) &&
                Util.isZero(xMax - other.xMax) &&
                Util.isZero(yMax - other.yMax) &&
                Util.isZero(zMax - other.zMax);
    }
}


