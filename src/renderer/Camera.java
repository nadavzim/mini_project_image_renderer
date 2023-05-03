package renderer;

import primitives.Point;
import primitives.Ray;
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
     * @param vTo The to vector of the camera.
     * @param vUp The up vector of the camera.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero( vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        else {
            this.p0 = p0;
            this.vUp = vUp.normalize();
            this.vTo = vTo.normalize();
            this.vRight = vTo.crossProduct(vUp).normalize();
        }
    }
    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return  this;
    }
    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pcenter = p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx = width / nX;
        double Ry = height / nY;

        //Pij point[i,j] in view-plane coordinates
        Point Pij = Pcenter;

        //delta values for moving on the view=plane
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi)) Pij = Pij.add(vUp.scale(Yi));

        // vector from camera's eye in the direction of point(i,j) in the view plane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }
}
