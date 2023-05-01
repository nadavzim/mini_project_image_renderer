package renderer;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }


    /**
     * Constructs a new Camera object with a given p0, vUp and vTo.
     * @param p0 The location of the camera.
     * @param vUp The up vector of the camera.
     * @param vTo The to vector of the camera.
     */
    public Camera(Point p0, Vector vUp, Vector vTo) {
        if (isZero( vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        else {
            this.p0 = p0;
            this.vUp = vUp.normalize();
            this.vTo = vTo.normalize();
            this.vRight = vTo.crossProduct(vUp).normalize();
        }
    }

}
